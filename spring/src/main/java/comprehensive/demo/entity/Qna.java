package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * qna 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String qna_title;

    private String qna_content;

    private LocalDateTime create_time;

    public void updateMember(Member member) {
        this.member = member;
    }

    /**
     * 문의 생성
     * JPA 양방향 매핑임으로 연관 관계 메서드 실행
     * @return 생성된 Qna Entity
     */
    public Qna createQna(Member member, String qna_title, String qna_content) {
        this.member = member;
        member.getQnaList().add(this);

        this.qna_title = qna_title;

        this.qna_content = qna_content;

        this.create_time = LocalDateTime.now();

        return this;
    }
}
