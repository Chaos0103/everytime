package project.everytime.client.user.dto;

import lombok.Data;
import project.everytime.client.UploadFile;
import project.everytime.client.user.AccountStatus;
import project.everytime.client.user.AuthType;
import project.everytime.client.user.User;

@Data
public class UserResponse {

    private Long id;
    private Long schoolId;
    private String loginId;
    private String username;
    private String birth;
    private String phone;
    private String nickname;
    private String yearOfAdmission;
    private String sex;
    private String email;
    private UploadFile uploadFile;
    private AuthType authType;
    private boolean agreementAd;
    private AccountStatus status;

    public UserResponse(User user) {
        this.id = user.getId();
        this.schoolId = user.getSchool().getId();
        this.loginId = user.getLoginId();
        this.username = user.getUsername();
        this.birth = user.getBirth();
        this.phone = user.getPhone();
        this.nickname = user.getNickname().getNickname();
        this.yearOfAdmission = user.getEnterYear();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.uploadFile = user.getUploadFile();
        this.authType = user.getAuthType();
        this.agreementAd = user.isAgreementAd();
        this.status = user.getStatus();
    }
}
