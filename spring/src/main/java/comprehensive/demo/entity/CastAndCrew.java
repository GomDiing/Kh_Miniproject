package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * cast_and_crew 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "cast_and_crew")
public class CastAndCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean adult;

    private Integer gender;

    private String profile_path;

    private String original_name;

    private String name;

    private Integer popularity;

    @OneToMany(mappedBy = "castAndCrew")
    private List<CreditCast> creditCastList = new ArrayList<>();

    @OneToMany(mappedBy = "castAndCrew")
    private List<CreditCrew> creditCrewList = new ArrayList<>();
}
