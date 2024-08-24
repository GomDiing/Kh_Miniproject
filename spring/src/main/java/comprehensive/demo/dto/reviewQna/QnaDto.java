package comprehensive.demo.dto.reviewQna;

import com.fasterxml.jackson.annotation.JsonProperty;
import comprehensive.demo.dto.member.MemberDto;
import comprehensive.demo.entity.Member;
import comprehensive.demo.entity.Qna;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 문의 DTO
 */
@Getter
public class QnaDto {

    private Integer id;

    @JsonProperty("member_id")
    private String memberId;

    private String qna_title;

    private String qna_content;

    private String create_time;

    /**
     * Qna Entity -> QnaDto DTO
     */
    public QnaDto toDto(Qna qna) {
        this.id = qna.getId();
        this.memberId = qna.getMember().getMemberId();
        this.qna_title = qna.getQna_title();
        this.qna_content = qna.getQna_content();
        this.create_time = qna.getCreate_time().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

        return this;
    }
}
