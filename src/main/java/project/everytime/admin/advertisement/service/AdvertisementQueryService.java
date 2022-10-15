package project.everytime.admin.advertisement.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.advertisement.AdvertisementType;
import project.everytime.admin.advertisement.dto.AdvertisementDto;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface AdvertisementQueryService {

    Map<AdvertisementType, List<AdvertisementDto>> randomAdvertisement();
}
