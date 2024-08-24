package comprehensive.demo.vo.reviewQna;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * 문의 전송 VO
 */
@Getter
public class SendQnaForm {

    @JsonProperty("id")
    @NotNull(message = "아이디는 필수 입력값입니다")
    private String memberId;

    @NotNull(message = "문의 제목은 필수 입력값입니다")
    private String qna_title;

    @NotNull(message = "리뷰 내용은 필수 입력값입니다")
    private String qna_content;
}
