package comprehensive.demo.dto.movie;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * 영화 검색 DTO
 */
@Getter
public class MovieSearchDto {

    private Integer movie_id;

    private String title;

    private String poster_path;

    private String director;

    private List<CastDto> cast = new LinkedList<>();

    public MovieSearchDto(Integer movie_id, String title, String poster_path) {
        this.movie_id = movie_id;
        this.title = title;
        this.poster_path = poster_path;
    }

    /**
     * 배우 정보 List 컬렉션에 추가
     */
    public void addCast(CastDto castDto) {
        this.cast.add(castDto);
    }

    /**
     * 영화 포스터 경로 갱신
     * null 이 아니면 저화질 경로로, 그렇지 않으면 문자열 null 갱신
     */
    public void updatePosterPath() {

        this.poster_path = poster_path == null ? MovieCode.path_null : MovieCode.path_low + poster_path;
    }

    /**
     * 영화 감독 이름 갱신
     * 빈 문자열이 아니면 주어진 감독이름 입력, 그렇지 않으면 문자열 null 갱신
     */
    public void updateDirector(String director) {

        this.director = director.equals("") ? MovieCode.person_null : director;
    }
}
