package project.everytime.client.user.dto;

import lombok.Data;
import project.everytime.client.user.AccountStatus;
import project.everytime.client.user.AuthType;
import project.everytime.client.user.User;

@Data
public class LoginUser {

    private Long id;
    private Long schoolId;
    private String schoolName;
    private String loginId;
    private String username;
    private String birth;
    private String phone;
    private String nickname;
    private String enterYear;
    private String sex;
    private String email;
    private String storeFileName;
    private AuthType authType;
    private boolean agreementAd;
    private AccountStatus status;

    public LoginUser(User user) {
        this.id = user.getId();
        this.schoolId = user.getSchool().getId();
        this.schoolName = user.getSchool().getName().replaceAll("대학교", "대");
        this.loginId = user.getLoginId();
        this.username = user.getUsername();
        this.birth = user.getBirth();
        this.phone = user.getPhone();
        this.nickname = user.getNickname().getNickname();
        this.enterYear = user.getEnterYear();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.storeFileName = user.getUploadFile().getStoreFileName();
        this.authType = user.getAuthType();
        this.agreementAd = user.isAgreementAd();
        this.status = user.getStatus();
    }
}
