package comprehensive.demo.entity;

import comprehensive.demo.vo.member.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * member 테이블과 매핑된 Entity
 */
@Entity
@Getter
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String memberId;

    private String password;

    private String email;

    private String name;

    private LocalDateTime create_time;

    @OneToMany(mappedBy = "member")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Qna> qnaList = new ArrayList<>();


    /**
     * SignUpMemberForm VO -> Member Entity
     */
    public Member toEntity(SignUpMemberForm signUpMemberForm) {
        this.memberId = signUpMemberForm.getMemberId();
        this.password = signUpMemberForm.getPassword();
        this.email = signUpMemberForm.getEmail();
        this.name = signUpMemberForm.getName();
        this.create_time = LocalDateTime.now();
        return this;
    }

    /**
     * UpdateMemberForm VO -> Member Entity
     */
    public Member toEntity(UpdatePasswordForm updatePasswordForm) {
        this.memberId = updatePasswordForm.getMemberId();
        this.password = updatePasswordForm.getPassword();
        return this;
    }

    /**
     * PasswordMemberForm VO -> Member Entity
     */
    public Member toEntity(SearchPasswordForm searchPasswordForm) {
        this.memberId = searchPasswordForm.getMemberId();
        this.email = searchPasswordForm.getEmail();
        return this;
    }

    /**
     * SignInMemberForm VO -> Member Entity
     */
    public Member toEntity(SignInMemberForm signInMemberForm) {
        this.memberId = signInMemberForm.getMemberId();
        this.password = signInMemberForm.getPassword();
        return this;
    }

    /**
     * DeleteMemberForm VO -> Member Entity
     */
    public Member toEntity(DeleteMemberForm deleteMemberForm) {
        this.memberId = deleteMemberForm.getMemberId();
        this.email = deleteMemberForm.getEmail();
        this.password = deleteMemberForm.getPassword();
        return this;
    }

    /**
     * SearchIdMemberForm VO -> Member Entity
     */
    public Member toEntity(SearchIdMemberForm searchIdMemberForm) {
        this.email = searchIdMemberForm.getEmail();
        this.name = searchIdMemberForm.getName();
        return this;
    }

    /**
     * MyPageMemberForm VO -> Member Entity
     */
    public Member toEntity(MyPageMemberForm myPageMemberForm) {
        this.memberId = myPageMemberForm.getMemberId();
        return this;
    }
}
