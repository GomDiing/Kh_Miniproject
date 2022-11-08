package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * credit_cast 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "credit_cast")
public class CreditCast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cast_id")
    private CastAndCrew castAndCrew;

    @Column(name = "\"character\"")
    private String character;

    private String known_for_department;

    private Integer cast_order_id;

    @Column(name = "\"order\"")
    private Integer order;

    private String credit_id;
}
