package project.everytime.client.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.school.School;
import project.everytime.client.TimeBaseEntity;
import project.everytime.client.UploadFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id", unique = true, updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Column(unique = true, updatable = false, nullable = false, length = 10)
    private String loginId;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false, length = 50)
    private String username;
    @Column(updatable = false, nullable = false, length = 10)
    private String birth;
    @Column(unique = true, updatable = false, nullable = false, length = 13)
    private String phone;
    @Column(nullable = false, length = 10)
    private String nickname;
    private LocalDateTime nicknameModifiedDate;
    @Column(nullable = false, length = 4)
    private String yearOfAdmission;
    @Column(updatable = false, nullable = false, length = 1)
    private String sex;
    @Column(unique = true, nullable = false)
    private String email;
    @Embedded
    private UploadFile uploadFile;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    private boolean adInfoSendAgree;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    public User(School school, String loginId, String password, String username, String birth, String phone, String nickname, String yearOfAdmission, String sex, String email, boolean adInfoSendAgree) {
        this.school = school;
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.nickname = nickname;
        this.nicknameModifiedDate = LocalDateTime.now().minusDays(30);
        this.yearOfAdmission = yearOfAdmission;
        this.sex = sex;
        this.email = email;
        this.uploadFile = null;
        this.authType = AuthType.NONE;
        this.adInfoSendAgree = adInfoSendAgree;
        this.status = AccountStatus.ACTIVE;
        school.addCount();
    }

    //== 비즈니스 로직==//
    public void changeAuthType(AuthType authType) {
        this.authType = authType;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
        this.nicknameModifiedDate = LocalDateTime.now();
    }

    public void changeUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void changeStatus(AccountStatus status) {
        this.status = status;
    }

    public void changeAdInfoSendAgree(boolean adInfoSendAgree) {
        this.adInfoSendAgree = adInfoSendAgree;
    }
}
