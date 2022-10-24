package project.everytime.client.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.client.TimeBaseEntity;
import project.everytime.client.UploadFile;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_image_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, updatable = false)
    private Item item;

    @Embedded
    private UploadFile uploadFile;

    public ItemImage(Item item, UploadFile uploadFile) {
        this.item = item;
        this.uploadFile = uploadFile;
    }
}
