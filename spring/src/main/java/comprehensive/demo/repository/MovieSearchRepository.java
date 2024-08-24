package comprehensive.demo.repository;

import comprehensive.demo.dto.movie.MovieSearchDto;
import comprehensive.demo.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 영화 검색 Repository
 */
@Repository
public interface MovieSearchRepository extends JpaRepository<Movie, Integer> {
    /**
     * 영화 제목이 한국어일 경우 검색하는 Repository
     * 검색어를 영화 테이블의 title 필드에서 검색하여 DTO로 변환하면서 page 컬렉션으로 반환
     */
    @Query("select new comprehensive.demo.dto.movie.MovieSearchDto(m.id, m.title, p.file_path) from Movie m, Posters p where m.id = p.movie.id and m.title LIKE concat('%',:search,'%') group by m.id order by m.popularity desc")
    Page<MovieSearchDto> findAllMovieSearchDtoKorean(@Param("search") String search, Pageable pageable);

    /**
     * 영화 제목이 영어일 경우 검색하는 Repository
     * 검색어를 영화 테이블의 original_title 필드에서 검색하여 DTO로 변환하면서 page 컬렉션으로 반환
     */
    @Query("select new comprehensive.demo.dto.movie.MovieSearchDto(m.id, m.title, p.file_path) from Movie m, Posters p where m.id = p.movie.id and m.original_title LIKE concat('%',:search,'%') group by m.id order by m.popularity desc")
    Page<MovieSearchDto> findAllMovieSearchDtoOriginal(@Param("search") String search, Pageable pageable);
}
