package comprehensive.demo.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;

import javax.persistence.*;

/**
 * movies_genre 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "movies_genre")
@JsonIgnoreType
public class MovieGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
