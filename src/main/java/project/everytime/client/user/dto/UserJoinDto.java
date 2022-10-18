package project.everytime.client.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserJoinDto {
    private String loginId;
    private String password;
    private String email;
    private String nickname;
    private String enterYear;
    private int agreementAd;

    private String username;
    private String birth;
    private String sex;
    private String phone;
}
