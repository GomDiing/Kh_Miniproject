package comprehensive.demo.vo.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * 비밀번호 찾기 VO
 */
@Getter
public class SearchPasswordForm {

    @NotNull(message = "아이디는 필수 입력값입니다")
    @JsonProperty("id")
    private String memberId;

    @NotNull(message = "이메일은 필수 입력값입니다")
    private String email;
}
