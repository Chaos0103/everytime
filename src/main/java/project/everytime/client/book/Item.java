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
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    private int soldOut;
    private int price;
    @Column(nullable = false, length = 4)
    private String statusNote;
    @Column(nullable = false, length = 4)
    private String statusDamage;
    private int meansDelivery;
    private int meansDirect;
    private String comment;
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

    //== 생성 메서드 ==//
    public static Item createItem(Book book, School school, User user, Lecture lecture, int price, String statusNote, String statusDamage, int meansDelivery, int meansDirect, String comment, String location, UploadFile coverImage, List<UploadFile> images) {
        Item item = new Item(book, school, user, lecture, price, statusNote, statusDamage, meansDelivery, meansDirect, comment, location, coverImage);
        for (UploadFile image : images) {
            ItemImage itemImage = new ItemImage(item, image);
            item.addImage(itemImage);
        }
        item.changeSoldOut(0);
        return item;
    }

    //== 연관관계 편의 메서드 ==//
    public void addImage(ItemImage itemImage) {
        this.images.add(itemImage);
    }

    //== 비즈니스 로직 ==//
    public void changeSoldOut(int soldOut) {
        this.soldOut = soldOut;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeComment(String comment) {
        this.comment = comment;
    }
}
