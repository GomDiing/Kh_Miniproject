package comprehensive.demo.vo.reviewQna;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * 리뷰 전송 VO
 */
@Getter
public class SendReviewForm {

    @JsonProperty("id")
    @NotNull(message = "아이디는 필수 입력값입니다")
    private String memberId;

    @JsonProperty("movie_id")
    @NotNull(message = "영화 번호는 필수 입력값입니다")
    private Integer movieId;

    @NotNull(message = "리뷰 내용은 필수 입력값입니다")
    private String review;
}
