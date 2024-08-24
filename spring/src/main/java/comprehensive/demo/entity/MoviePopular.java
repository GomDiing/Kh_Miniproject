package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * movies_popular 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "movies_popular")
public class MoviePopular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
