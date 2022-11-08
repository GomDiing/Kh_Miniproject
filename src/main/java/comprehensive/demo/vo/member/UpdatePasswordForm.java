package comprehensive.demo.vo.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 비밀번호 변경 VO
 */
@Getter
public class UpdatePasswordForm {

    @NotBlank(message = "아이디는 필수 입력값입니다")
    @JsonProperty("id")
    private String memberId;

    @NotNull(message = "비밀번호는 필수 입력값입니다")
    private String password;
}
