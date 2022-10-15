package project.everytime.admin.advertisement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.TimeBaseEntity;
import project.everytime.client.UploadFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Advertisement extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "advertisement_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AdvertisementType type;
    private String url;
    @Embedded
    private UploadFile uploadFile;
    private LocalDateTime expiredDate;

    public Advertisement(AdvertisementType type, String url, UploadFile uploadFile, LocalDateTime expiredDate) {
        this.type = type;
        this.url = url;
        this.uploadFile = uploadFile;
        this.expiredDate = expiredDate;
    }
}
