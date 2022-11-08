package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * review 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String comment;

    private LocalDateTime create_time;

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateMovie(Movie movie) {
        this.movie = movie;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    /**
     * 리뷰 생성
     * JPA 양방향 매핑임으로 연관 관계 메서드 실행
     * @return 생성된 Review Entity
     */
    public Review createReview(Movie movie, Member member,String comment) {
        this.movie = movie;
        movie.getReviewList().add(this);

        this.member = member;
        member.getReviewList().add(this);

        this.comment = comment;

        this.create_time = LocalDateTime.now();

        return this;
    }
}
