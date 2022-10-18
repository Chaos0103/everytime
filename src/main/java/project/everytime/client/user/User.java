package project.everytime.client.user;

import lombok.AccessLevel;
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
    @Embedded
    private Nickname nickname;
    @Column(nullable = false, length = 4)
    private String enterYear;
    @Column(updatable = false, nullable = false, length = 1)
    private String sex;
    @Column(unique = true, nullable = false)
    private String email;
    @Embedded
    private UploadFile uploadFile;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    private boolean agreementAd;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private User(School school, String loginId, String password, String username, String birth, String phone, String enterYear, String sex, String email) {
        this.school = school;
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.enterYear = enterYear;
        this.sex = sex;
        this.email = email;
    }

    //== 생성 메서드 ==//
    public static User createUser(School school, String loginId, String password, String username, String birth, String phone, String nickname, String enterYear, String sexNumber, String email, int agreementAd) {
        String sex = sexNumber.equals("1") || sexNumber.equals("3") ? "M" : "F";
        User user = new User(school, loginId, password, username, birth, phone, enterYear, sex, email);
        user.changeNickname(nickname);
        user.changeUploadFile(new UploadFile("/file/initProfile.png", "/file/initProfile.png"));
        user.changeAuthType(AuthType.NONE);
        user.changeStatus(AccountStatus.ACTIVE);
        user.changeAdInfoSendAgree(agreementAd);
        school.addCount();
        return user;
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
        LocalDateTime period = LocalDateTime.now().plusDays(30);
        this.nickname = new Nickname(nickname, period);
    }

    public void changeUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void changeStatus(AccountStatus status) {
        this.status = status;
    }

    public void changeAdInfoSendAgree(int agreementAd) {
        this.agreementAd = (agreementAd == 1);
    }
}
