package project.everytime.client.ban.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BanService {

    //차단등록
    Long addBan(Long userId, Long targetId);
}
