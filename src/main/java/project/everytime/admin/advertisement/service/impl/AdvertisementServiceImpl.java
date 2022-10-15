package project.everytime.admin.advertisement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.FileStore;
import project.everytime.admin.advertisement.Advertisement;
import project.everytime.admin.advertisement.AdvertisementType;
import project.everytime.admin.advertisement.repository.AdvertisementRepository;
import project.everytime.admin.advertisement.service.AdvertisementService;
import project.everytime.client.UploadFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final FileStore fileStore;

    @Override
    public Long addAdvertisement(AdvertisementType type, String url, MultipartFile file, int days) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(file);
        LocalDateTime expiredDate = LocalDateTime.now().plusDays(days);
        Advertisement advertisement = new Advertisement(type, url, uploadFile, expiredDate);
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);
        return savedAdvertisement.getId();
    }
}
