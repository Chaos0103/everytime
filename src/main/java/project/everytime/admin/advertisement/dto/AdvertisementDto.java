package project.everytime.admin.advertisement.dto;

import lombok.Data;
import project.everytime.admin.advertisement.Advertisement;
import project.everytime.admin.advertisement.AdvertisementType;

@Data
public class AdvertisementDto {
    private AdvertisementType type;
    private String url;
    private String storeFileName;

    public AdvertisementDto(Advertisement advertisement) {
        this.type = advertisement.getType();
        this.url = advertisement.getUrl();
        this.storeFileName = advertisement.getUploadFile().getStoreFileName();
    }
}
