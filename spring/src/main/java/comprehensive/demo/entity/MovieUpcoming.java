package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * movies_upcoming 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "movies_upcoming")
public class MovieUpcoming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
