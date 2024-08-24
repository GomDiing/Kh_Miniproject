package comprehensive.demo.dto.movie;

import lombok.Getter;

/**
 * 영화 카테고리 DTO
 */
@Getter
public class MovieCategoryDto {

    private Integer movie_id;

    private String poster_path;

    public MovieCategoryDto(Integer movie_id, String poster_path) {
        this.movie_id = movie_id;
        this.poster_path = poster_path;
    }

    /**
     * 영화 포스터 경로 갱신
     * null 이 아니면 저화질 경로로, 그렇지 않으면 문자열 null 갱신
     */
    public void updatePosterPath() {
        this.poster_path = poster_path == null ? MovieCode.path_null : MovieCode.path_low + poster_path;
    }
}
