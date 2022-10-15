package project.everytime.admin.advertisement.repository;

import project.everytime.admin.advertisement.Advertisement;

import java.time.LocalDateTime;
import java.util.List;

public interface AdvertisementRepositoryCustom {

    List<Advertisement> findAdvertisement(LocalDateTime currentTime);
}
