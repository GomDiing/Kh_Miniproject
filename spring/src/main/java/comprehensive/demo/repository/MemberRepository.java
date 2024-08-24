package comprehensive.demo.repository;

import comprehensive.demo.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 회원(Member) Repository
 */
@Repository
public class MemberRepository {

    @PersistenceContext
    public EntityManager em;

    /**
     * 회원 추가
     */
    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Integer id) {
        return em.find(Member.class, id);
    }

    /**
     * 회원 ID로 비밀번호 변경
     */
    public void updatePasswordByMemberId(Member member) {
        em.createQuery("update Member m set m.password = :password where m.memberId =:memberId")
                .setParameter("password", member.getPassword())
                .setParameter("memberId", member.getMemberId())
                .executeUpdate();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /**
     * 회원ID로 회원 조회
     */
    public List<Member> findById(String memberId) {
        return em.createQuery("select m from Member m where m.memberId = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    /**
     * 회원ID와 이메일로 회원 조회
     */
    public List<Member> findByMemberIdEmail(Member member) {
        return em.createQuery("select m from Member m where m.memberId =:memberId and m.email =:email")
                .setParameter("memberId", member.getMemberId())
                .setParameter("email", member.getEmail())
                .getResultList();
    }

    /**
     * 메일로 회원 조회
     */
    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email =:email")
                .setParameter("email", email)
                .getResultList();
    }

    /**
     * 메일과 이름으로 회원 조회
     */
    public List<Member> findByEmailName(Member member) {
        return em.createQuery("select m from Member m where m.email =:email and m.name =:name")
                .setParameter("email", member.getEmail())
                .setParameter("name", member.getName())
                .getResultList();
    }

    /**
     * 비밀번호와 회원ID로 회원 조회
     */
    public List<Member> findByPasswordMemberId(Member member) {
        return em.createQuery("select m from Member m where m.password =:password and m.memberId =:memberId")
                .setParameter("password", member.getPassword())
                .setParameter("memberId", member.getMemberId())
                .getResultList();
    }

    /**
     * 비밀번호, 메일, 회원ID로 회원 조회
     */
    public List<Member> findByPasswordEmailMemberId(Member member) {
        return em.createQuery("select m from Member m where m.password =:password and m.email =:email and m.memberId =:memberId")
                .setParameter("password", member.getPassword())
                .setParameter("email", member.getEmail())
                .setParameter("memberId", member.getMemberId())
                .getResultList();
    }

    /**
     * ID로 회원 삭제
     */
    public Integer deleteOne(Integer id) {
        return em.createQuery("delete from Member m where m.id =:id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
