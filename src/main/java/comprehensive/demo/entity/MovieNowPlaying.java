package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * movies_now_playing 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "movies_now_playing")
public class MovieNowPlaying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
