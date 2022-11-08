package comprehensive.demo.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * genre 테이블과 매핑된 Entity
 */
@Entity
@Getter
@Table(name = "genre")
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<MovieGenre> movieGenres = new ArrayList<>();

    public Genre (Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
