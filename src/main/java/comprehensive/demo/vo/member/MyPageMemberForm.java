package comprehensive.demo.vo.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import comprehensive.demo.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

/**
 * 회원 정보 조회 VO
 */
@Getter
public class MyPageMemberForm {

    @JsonProperty("id")
    private String memberId;
}
