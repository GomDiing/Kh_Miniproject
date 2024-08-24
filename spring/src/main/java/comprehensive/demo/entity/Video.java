package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * video 테이블과 매핑된 Entity
 */
@Getter
@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String name;

    @Column(name = "\"key\"")
    private String key;

    private Integer size;

    private String type;

    private Boolean official;

    private String published_at;

    private String video_id;

    private String site;

}
