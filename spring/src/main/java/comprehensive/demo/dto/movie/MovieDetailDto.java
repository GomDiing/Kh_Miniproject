package comprehensive.demo.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * 영화 상세 페이지 DTO
 */
@Getter
public class MovieDetailDto {
    private String backdrop_path;
    private String title;
    private String release_date;
    private Integer runtime;
    private Integer vote_average;
    private String overview;

    @JsonProperty("youtube_url_list")
    private List<YoutubeUrlDto> youtubeUrlList;
    private String director;
    @JsonProperty("genre")
    private List<GenreDto> genreList;
    @JsonProperty("cast")
    private List<CastDto> castList;
    @JsonProperty("review")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MovieReviewDto> reviewDtoList;

    /**
     * 생성 메서드
     */
    public MovieDetailDto create(MovieDto movieDto, String backdrop_path, List<YoutubeUrlDto> youtubeUrlList, List<GenreDto> genreList, List<CastDto> castList, String director, List<MovieReviewDto> movieReviewDtoList) {
        this.title = movieDto.getTitle();
        this.release_date = movieDto.getRelease_date();
        this.runtime = movieDto.getRuntime();
        this.vote_average = movieDto.getVote_average();
        this.overview = movieDto.getOverview();
        this.backdrop_path = backdrop_path;
        this.youtubeUrlList = youtubeUrlList;
        this.genreList = genreList;
        this.castList = castList;
        this.director = director;
        this.reviewDtoList = movieReviewDtoList;

        return this;
    }

    /**
     * 뒷 배경 경로 갱신
     */
    public void updateBackdropPath() {
        if (!backdrop_path.equals(MovieCode.path_null))
            this.backdrop_path = MovieCode.path_original + backdrop_path;
    }

    /**
     * 유튜브 링크 주소 갱신
     */
    public void updateYoutubeKey() {
        for (YoutubeUrlDto youtubeUrlDto : youtubeUrlList) {
            youtubeUrlDto.updateYoutubeUrl();
        }
    }
}
