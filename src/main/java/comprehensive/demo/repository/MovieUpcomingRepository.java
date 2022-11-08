package comprehensive.demo.repository;

import comprehensive.demo.dto.movie.MovieCategoryDto;
import comprehensive.demo.entity.MovieUpcoming;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 곧 개봉예정 영화 (MovieUpcoming) Repository
 */
@Repository
public interface MovieUpcomingRepository extends JpaRepository<MovieUpcoming, Integer> {

    /**
     * 곧 개봉예정 영화 테이블에서 조회해서 DTO로 변환하면서 page 컬렉션으로 반환
     */
    @Query("select new comprehensive.demo.dto.movie.MovieCategoryDto(m.movie.id, p.file_path) from MovieUpcoming m, Posters p where m.movie.id = p.movie.id group by m.id")
    Page<MovieCategoryDto> findAllMovieUpcomingDto(Pageable pageable);
}
