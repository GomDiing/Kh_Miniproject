package comprehensive.demo.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 스태프(CreditCrew) Repository
 */
@Repository
public class CreditCrewRepository {

    @PersistenceContext
    public EntityManager em;

    /**
     * 영화를 감독한 감독 이름을 반환
     */
    public List<Integer> findDirectorByMovieId(Integer movieId) {

        return em.createQuery("select cc.castAndCrew.id from CreditCrew cc where cc.movie.id = :movieId and cc.job = 'Director'")
                .setParameter("movieId", movieId)
                .getResultList();
    }
}
