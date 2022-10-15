package project.everytime.admin.advertisement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.advertisement.Advertisement;
import project.everytime.admin.advertisement.AdvertisementType;
import project.everytime.admin.advertisement.dto.AdvertisementDto;
import project.everytime.admin.advertisement.repository.AdvertisementRepository;
import project.everytime.admin.advertisement.service.AdvertisementQueryService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdvertisementQueryServiceImpl implements AdvertisementQueryService {

    private final AdvertisementRepository advertisementRepository;


    @Override
    public Map<AdvertisementType, List<AdvertisementDto>> randomAdvertisement() {

        Map<AdvertisementType, List<AdvertisementDto>> result = new HashMap<>();

        List<Advertisement> advertisements = advertisementRepository.findAdvertisement(LocalDateTime.now());

        List<AdvertisementDto> mainAdvertisement = advertisements.stream()
                .filter(advertisement -> advertisement.getType().equals(AdvertisementType.MAIN))
                .map(AdvertisementDto::new)
                .toList();
        result.put(AdvertisementType.MAIN, mainAdvertisement);

        List<AdvertisementDto> sideAdvertisement = advertisements.stream()
                .filter(advertisement -> advertisement.getType().equals(AdvertisementType.SIDE))
                .map(AdvertisementDto::new)
                .toList();
        result.put(AdvertisementType.SIDE, sideAdvertisement);

        return result;
    }
}
