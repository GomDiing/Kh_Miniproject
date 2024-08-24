package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * credit_crew 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "credit_crew")
public class CreditCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id")
    private CastAndCrew castAndCrew;

    private String credit_id;

    private String department;

    private String job;
}
