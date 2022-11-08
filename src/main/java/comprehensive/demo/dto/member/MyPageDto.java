package comprehensive.demo.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import comprehensive.demo.entity.Member;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MyPageDto {

    @JsonProperty("id")
    private String memberId;

    private String email;

    private String name;

    private String create_time;

    /**
     * Member Entity -> MyPageDto DTO
     */
    public MyPageDto toDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.create_time = member.getCreate_time().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

        return this;
    }
}
