package project.everytime.client.ban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.ban.repository.BanRepository;
import project.everytime.client.ban.service.BanService;

@Service
@RequiredArgsConstructor
public class BanServiceImpl implements BanService {

    private final BanRepository banRepository;

    @Override
    public Long addBan(Long userId, Long targetId) {
        return null;
    }
}
