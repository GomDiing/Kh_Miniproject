package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * posters 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "posters")
public class Posters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Float aspect_ratio;

    private Integer height;

    private String file_path;

    private Float vote_average;

    private Integer vote_count;

    private Integer width;
}
