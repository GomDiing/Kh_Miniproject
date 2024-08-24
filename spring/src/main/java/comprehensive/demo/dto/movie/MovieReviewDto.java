package comprehensive.demo.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import comprehensive.demo.entity.Review;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * 영화 리뷰 DTO
 */
@Getter
public class MovieReviewDto {

    @JsonProperty("id")
    private String memberId;

    private String comment;
    private String create_time;

    /**
     * Review Entity -> MovieReviewDto Dto
     */
    public MovieReviewDto toDto(Review review) {
        this.memberId = review.getMember().getName();
        this.comment = review.getComment();
        this.create_time = review.getCreate_time().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return this;
    }
}
