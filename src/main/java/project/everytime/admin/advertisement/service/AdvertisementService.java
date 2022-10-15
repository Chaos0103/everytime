package project.everytime.admin.advertisement.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.admin.advertisement.AdvertisementType;

import java.io.IOException;

@Transactional
public interface AdvertisementService {

    Long addAdvertisement(AdvertisementType type, String url, MultipartFile file, int days) throws IOException;
}
