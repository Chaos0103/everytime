package project.everytime.admin.advertisement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.admin.advertisement.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, AdvertisementRepositoryCustom {
}
