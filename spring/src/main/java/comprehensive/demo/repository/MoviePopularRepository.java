package comprehensive.demo.repository;

import comprehensive.demo.entity.MoviePopular;
import comprehensive.demo.dto.movie.MovieCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 인기 영화 (MoviePopular) Repository
 */
@Repository
public interface MoviePopularRepository extends JpaRepository<MoviePopular, Integer> {

    /**
     * 인기 영화 목록 테이블에서 조회해서 DTO로 변환하면서 page 컬렉션으로 반환
     */
    @Query("select new comprehensive.demo.dto.movie.MovieCategoryDto(m.movie.id, p.file_path) from MoviePopular m, Posters p where m.movie.id = p.movie.id group by m.id")
    Page<MovieCategoryDto> findAllMoviePopularDto(Pageable pageable);
}
