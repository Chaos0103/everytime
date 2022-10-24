package project.everytime.client.book.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.FileStore;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.lecture.repository.LectureRepository;
import project.everytime.client.UploadFile;
import project.everytime.client.book.Book;
import project.everytime.client.book.Item;
import project.everytime.client.book.dto.BookAddDto;
import project.everytime.client.book.dto.ItemAddDto;
import project.everytime.client.book.repository.BookRepository;
import project.everytime.client.book.repository.ItemRepository;
import project.everytime.client.book.service.ItemService;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.exception.NoSuchException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static project.everytime.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LectureRepository lectureRepository;
    private final FileStore fileStore;

    @Override
    public Long addItem(Long userId, Long lectureId, BookAddDto addBook, ItemAddDto addItem) throws IOException {

        User findUser = getUser(userId);
        Book findBook = getBook(addBook);
        Lecture findLecture = getLecture(lectureId);

        UploadFile coverImage = null;
        if (addItem.getCoverImage() != null) {
            coverImage = fileStore.storeFile(addItem.getCoverImage());
        }

        List<UploadFile> images = new ArrayList<>();
        for (MultipartFile image : addItem.getImages()) {
            UploadFile uploadFile = fileStore.storeFile(image);
            images.add(uploadFile);
        }

        Item item = Item.createItem(findBook, findUser.getSchool(), findUser, findLecture, addItem.getPrice(), addItem.getStatusNote(), addItem.getStatueDamage(), addItem.getMeansDelivery(), addItem.getMeansDirect(), addItem.getComment(), addItem.getLocation(), coverImage, images);
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    @Override
    public Long editItemPrice(Long itemId, Integer price) {
        Item findItem = getItem(itemId);
        findItem.changePrice(price);
        return findItem.getId();
    }

    @Override
    public Long editItemComment(Long itemId, String comment) {
        Item findItem = getItem(itemId);
        findItem.changeComment(comment);
        return findItem.getId();
    }

    @Override
    public Long itemSoldOut(Long itemId) {
        Item findItem = getItem(itemId);
        findItem.changeSoldOut(1);
        return findItem.getId();
    }

    private User getUser(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_USER);
        }
        return findUser.get();
    }

    private Book getBook(BookAddDto addBook) {
        Optional<Book> findBook = bookRepository.findByIsbn(addBook.getIsbn());

        Book book;
        if (findBook.isPresent()) {
            book = findBook.get();
            book.change(addBook.getTitle(), addBook.getAuthor(), addBook.getPublisher(), addBook.getPubDate(), addBook.getPrice());
        } else {
            Book newBook = new Book(addBook.getIsbn(), addBook.getTitle(), addBook.getAuthor(), addBook.getPublisher(), addBook.getPubDate(), addBook.getPrice());
            book = bookRepository.save(newBook);
        }
        return book;
    }

    private Lecture getLecture(Long lectureId) {
        if (lectureId == null) {
            return null;
        }
        Optional<Lecture> findLecture = lectureRepository.findById(lectureId);
        if (findLecture.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_LECTURE);
        }
        return findLecture.get();
    }

    private Item getItem(Long itemId) {
        Optional<Item> findItem = itemRepository.findById(itemId);
        if (findItem.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_ITEM);
        }
        return findItem.get();
    }
}
