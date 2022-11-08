package comprehensive.demo.vo.member;

import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * 아이디 찾기 VO
 */
@Getter
public class SearchIdMemberForm {

    @NotNull(message = "이메일은 필수 입력값입니다")
    private String email;

    @NotNull(message = "이름은 필수 입력값입니다")
    private String name;
}
