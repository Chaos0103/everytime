package project.everytime.client.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.school.School;
import project.everytime.client.TimeBaseEntity;
import project.everytime.client.UploadFile;
import project.everytime.client.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id", nullable = false, updatable = false)
    private Book book;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id", nullable = false, updatable = false)
    private School school;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lecture_id", updatable = false)
    private Lecture lecture;

    @Column(nullable = false)
    private Integer soldOut;
    @Column(nullable = false)
    private Integer price;
    @Column(updatable = false, nullable = false, length = 4)
    private String statusNote;
    @Column(updatable = false, nullable = false, length = 4)
    private String statusDamage;
    @Column(updatable = false, nullable = false)
    private Integer meansDelivery;
    @Column(updatable = false, nullable = false)
    private Integer meansDirect;
    @Column(length = 500)
    private String comment;
    @Column(updatable = false, length = 20)
    private String location;
    @Embedded
    private UploadFile coverImage;

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItemImage> images = new ArrayList<>();

    public Item(Book book, School school, User user, Lecture lecture, int price, String statusNote, String statusDamage, int meansDelivery, int meansDirect, String comment, String location, UploadFile coverImage) {
        this.book = book;
        this.school = school;
        this.user = user;
        this.lecture = lecture;
        this.price = price;
        this.statusNote = statusNote;
        this.statusDamage = statusDamage;
        this.meansDelivery = meansDelivery;
        this.meansDirect = meansDirect;
        this.comment = comment;
        this.location = location;
        this.coverImage = coverImage;
    }

    //== ?????? ????????? ==//
    public static Item createItem(Book book, School school, User user, Lecture lecture, int price, String statusNote, String statusDamage, int meansDelivery, int meansDirect, String comment, String location, UploadFile coverImage, List<UploadFile> images) {
        Item item = new Item(book, school, user, lecture, price, statusNote, statusDamage, meansDelivery, meansDirect, comment, location, coverImage);
        for (UploadFile image : images) {
            ItemImage itemImage = new ItemImage(item, image);
            item.addImage(itemImage);
        }
        item.soldOut = 0;
        return item;
    }

    //== ???????????? ?????? ????????? ==//
    public void addImage(ItemImage itemImage) {
        this.images.add(itemImage);
    }

    //== ???????????? ?????? ==//
    public void soldOut() {
        this.soldOut = 1;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeComment(String comment) {
        this.comment = comment;
    }
}
