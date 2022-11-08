package comprehensive.demo.repository;

import comprehensive.demo.entity.Genre;
import comprehensive.demo.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 영화(Movie) Repository
 */
@Repository
@Slf4j
public class MovieRepository {

    @PersistenceContext
    public EntityManager em;

    /**
     * 영화 조회
     */
    public Movie findOne(Integer movieId) {

        return em.find(Movie.class, movieId);
    }

    /**
     * 인기있는 상위 10개 영화 조회
     */
    public List<Movie> findByPopularity() {
        return em.createQuery("select m from Movie m order by m.popularity DESC")
                .setMaxResults(10)
                .getResultList();
    }

    /**
     * 영화 장르 조회
     */
    public List<Genre> findMovieGenre(Integer id) {
        return em.createQuery("select mg.genre from MovieGenre mg where mg.movie.id =:id")
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * 인기가 높은 영화의 뒷 배경 상위 1개 조회
     */
    public List<String> findBackdropsByIdTopOne(Integer id) {
        return em.createQuery("select b.file_path from Backdrops b where b.movie.id =:id order by b.vote_average desc")
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
    }

    /**
     * 예고편인 유튜브 링크 List 조회
     */
    public List<String> findYoutubeKeyById(Integer id) {
        return em.createQuery("select v.key from Video v where v.movie.id =:id and v.type = 'Trailer' and v.site = 'Youtube'")
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * 예고편인 유튜브 링크 1개 조회
     */
    public List<String> findYoutubeKeyByIdTopOne(Integer id) {
        return em.createQuery("select v.key from Video v where v.movie.id =:id and v.type = 'Trailer' and v.site = 'Youtube'")
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
    }
}
