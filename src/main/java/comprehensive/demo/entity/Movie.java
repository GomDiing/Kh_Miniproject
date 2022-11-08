package comprehensive.demo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * movie 테이블과 매핑된 Entity
 */
@Entity
@Getter
@Table(name = "movies", schema = "miniproject")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean adult;

    private String backdrop_path;

    private Integer budget;

    private String homepage;

    private String imdb_id;

    private String original_language;

    private String original_title;

    @Column(columnDefinition = "LONGTEXT")
    private String overview;

    private Float popularity;

    private String poster_path;

    private String release_date;

    private Integer runtime;

    private Float revenue;

    private String status;

    private String tagline;

    private String title;

    private Boolean video;

    private Integer vote_average;

    @Column(columnDefinition = "BIGINT")
    private Float vote_count;

    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> movieGenreList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MoviePopular> moviePopularList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieTopRated> movieTopRatedList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieNowPlaying> movieNowPlayingList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieUpcoming> movieUpcomingList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Video> videoList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Posters> postersList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Backdrops> backdropsList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<CreditCast> creditCastList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<CreditCrew> creditCrewList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Review> reviewList = new ArrayList<>();

    /**
     * 연관 메서드
     */
    public void addReview(Review review) {
        reviewList.add(review);
        review.updateMovie(this);
    }
}
