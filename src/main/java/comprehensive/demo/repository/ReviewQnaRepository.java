package comprehensive.demo.repository;

import comprehensive.demo.entity.Member;
import comprehensive.demo.entity.Qna;
import comprehensive.demo.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 리뷰와 문의 Repository
 */
@Repository
@Slf4j
public class ReviewQnaRepository {

    @PersistenceContext
    public EntityManager em;

    /**
     * 리뷰 추가
     */
    public void saveReview(Review review) {
        em.persist(review);
    }

    /**
     * 문의 추가
     */
    public void saveQna(Qna qna) {
        em.persist(qna);
    }

    /**
     * 영화ID로 리뷰 조회
     */
    public List<Review> findMovieReview(Integer movieId) {
        return em.createQuery("select r from Review r where r.movie.id =:movieId", Review.class)
                .setParameter("movieId", movieId)
                .getResultList();
    }

    /**
     * 전체 문의 조회
     */
    public List<Qna> findAllQna() {
        return em.createQuery("select q from Qna q", Qna.class)
                .getResultList();
    }
}
