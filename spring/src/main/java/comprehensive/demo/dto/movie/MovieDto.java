package comprehensive.demo.dto.movie;

import comprehensive.demo.entity.Movie;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 영화 DTO
 */
@Getter
public class MovieDto {
    private Integer id;

    private Boolean adult;

    private String backdrop_path;

    private Integer budget;

    private String homepage;

    private String imdb_id;

    private String original_language;

    private String original_title;

    private String overview;

    private Float popularity;

    private String poster_path;

    private String release_date;

    private Integer runtime;

    private String status;

    private String tagline;

    private String title;

    private Boolean video;

    private Integer vote_average;

    private Float vote_count;


    /**
     * Movie Entity -> MovieDto DTO
     */
    public MovieDto toMovieDto(Movie movie) {
        this.id = movie.getId();
        this.adult = movie.getAdult();
        this.backdrop_path = movie.getBackdrop_path();
        this.budget = movie.getBudget();
        this.homepage = movie.getHomepage();
        this.imdb_id = movie.getImdb_id();
        this.original_language = movie.getOriginal_language();
        this.original_title = movie.getOriginal_title();
        this.overview = movie.getOverview();
        this.popularity = movie.getPopularity();
        this.poster_path = movie.getPoster_path();
        this.release_date = movie.getRelease_date();
        this.runtime = movie.getRuntime();
        this.status = movie.getStatus();
        this.tagline = movie.getTagline();
        this.title = movie.getTitle();
        this.video = movie.getVideo();
        this.vote_average = movie.getVote_average();
        this.vote_count = movie.getVote_count();

        return this;
    }

    /**
     * List<Movie> -> List<MovieDto>
     */
    public List<MovieDto> toMovieDtoList(List<Movie> movieList) {
        List<MovieDto> movieDtoList = new ArrayList<>();

        for (Movie movie : movieList) {
            movieDtoList.add(toMovieDto(movie));

        }

        return movieDtoList;
    }
}
