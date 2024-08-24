package comprehensive.demo.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import comprehensive.demo.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberDto {

    private Integer id;

    @JsonProperty("member_id")
    private String memberId;

    private String password;

    private String email;

    private String name;

    private String create_time;

    /**
     * Member Entity -> MemberDto DTO
     */
    public MemberDto toDto(Member member) {
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.password = member.getPassword();
        this.email = member.getEmail();
        this.name = member.getName();
        this.create_time = member.getCreate_time().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

        return this;
    }

}
