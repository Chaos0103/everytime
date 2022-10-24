package project.everytime.client.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.client.book.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
}
