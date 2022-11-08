package comprehensive.demo.vo.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * 회원 가입 VO
 */
@Getter
public class SignUpMemberForm {

    @NotNull(message = "아이디는 필수 입력값입니다")
    @JsonProperty("id")
    private String memberId;

    @NotNull(message = "비밀번호는 필수 입력값입니다")
    private String password;

    @NotNull(message = "이메일은 필수 입력값입니다")
    private String email;

    @NotNull(message = "이름은 필수 입력값입니다")
    private String name;
}
