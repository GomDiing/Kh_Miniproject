package comprehensive.demo.repository;

import comprehensive.demo.dto.movie.CastDto;
import comprehensive.demo.entity.CastAndCrew;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 출연진(CastCrew) Page Repository
 */
@Repository
public interface CastCrewPageRepository extends JpaRepository<CastAndCrew, Integer> {

    /**
     * CreditCast와 CastCrew 테이블에서 영화에 출연한 배우들을 조회해서 DTO로 변환하면서 page 컬렉션으로 반환
     */
    @Query("select new comprehensive.demo.dto.movie.CastDto(cac.profile_path, cac.name, cc.character, cc.known_for_department) from CreditCast cc, CastAndCrew cac where cc.movie.id =:movie_id and cac.id = cc.castAndCrew.id group by cac.id order by cc.order")
    List<CastDto> findAllMovieDefaultCastDto(@Param("movie_id") Integer movie_id, Pageable pageable);

    /**
     * 고유 번호로 조회하여 이름 반환
     */
    @Query("select cc.name from CastAndCrew cc where cc.id =:id")
    List<String> findOne(@Param("id") Integer id);
}
