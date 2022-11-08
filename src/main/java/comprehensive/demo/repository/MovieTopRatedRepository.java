package comprehensive.demo.repository;

import comprehensive.demo.dto.movie.MovieCategoryDto;
import comprehensive.demo.entity.MovieTopRated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 역대 최고 영화 (MovieTopRated) Repository
 */
@Repository
public interface MovieTopRatedRepository extends JpaRepository<MovieTopRated, Integer> {

    /**
     * 역대 최고 영화 목록 조회 테이블에서 조회해서 DTO로 변환하면서 page 컬렉션으로 반환
     */
    @Query("select new comprehensive.demo.dto.movie.MovieCategoryDto(m.movie.id, p.file_path) from MovieTopRated m, Posters p where m.movie.id = p.movie.id group by m.id")
    Page<MovieCategoryDto> findAllMovieTopRatedDto(Pageable pageable);
}
