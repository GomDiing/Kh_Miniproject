package comprehensive.demo.repository;

import comprehensive.demo.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 장르(Genre) Repository
 */
@Repository
@Slf4j
public class GenreRepository {

    @PersistenceContext
    public EntityManager em;

    /**
     * 장르 조회
     */
    public Genre findOne(Integer id) {

        return em.find(Genre.class, id);
    }

    /**
     * 장르 저장
     */
    public void save(Genre genre) {

        em.persist(genre);
    }
}
