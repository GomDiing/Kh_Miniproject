package comprehensive.demo.service;

import comprehensive.demo.dto.member.MemberDto;
import comprehensive.demo.dto.member.MyPageDto;
import comprehensive.demo.repository.MemberRepository;
import comprehensive.demo.entity.Member;
import comprehensive.demo.exception.CustomException;
import comprehensive.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 회원 관련 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입 서비스
     * @return 가입한 회원ID 반환
     */
    @Transactional
    public String join(Member member) {

        //중복 회원ID 확인
        validateDuplicateById(member);

        //중복 이메일 확인
        validateDuplicateByEmail(member);

        //회원 추가
        memberRepository.save(member);

        return member.getMemberId();
    }

    /**
     * 아이디로 중복 아이디 확인
     */
    private void validateDuplicateById(Member member) {

        List<Member> findMembers = memberRepository.findById(member.getMemberId());

        //조회된 회원이 있다면 예외처리
        if (!findMembers.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLI_MEMBER_ID);
        }
    }
    /**
     * 중복 이메일 확인
     */
    private void validateDuplicateByEmail(Member member) {

        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());

        //조회된 회원이 없다면 예외처리
        if (!findMembers.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLI_EMAIL);
        }
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public void updatePassword(Member member) {
        memberRepository.updatePasswordByMemberId(member);
    }

    /**
     * 아이디로 회원 조회
     * @return 조회된 회원 반환, 없으면 예외 처리
     */
    @Transactional
    public Member search(String memberId) {
        List<Member> findOne = findByIdService(memberId);

        return findOne.get(0);
    }

    /**
     * 회원ID로 회원 조회
     * @return 조회된 회원 반환, 없으면 예외처리
     */
    private List<Member> findByIdService(String memberId) {
        //회원ID로 회원 조회
        List<Member> findOne = memberRepository.findById(memberId);

        //조회된 회원이 없다면 예외처리
        if (findOne.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_MEMBER);
        }

        return findOne;
    }

    /**
     * 회원ID와 비밀번호로 이름과 비밀번호 조회
     * Map 컬렉션으로 이름과 비밀번호 반환
     * @return Map {"name":"조회한 회원 이름", "password":"조회한 회원 비밀번호"}
     */
    @Transactional
    public Map<String, String> searchPasswordName(Member member) {

        List<Member> findOne = memberRepository.findByMemberIdEmail(member);

        //조회된 회원이 없으면 예외처리
        if (findOne.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD_ID);
        }

        //Map으로 반환
        Map<String, String> memberInform = new LinkedHashMap<>();

        memberInform.put("name", findOne.get(0).getName());
        memberInform.put("password", findOne.get(0).getPassword());

        return memberInform;
    }

    /**
     * 회원ID 조회
     * Map 컬렉션으로 회원ID 반환
     * @return {"id" : "조회한 회원 ID"}
     */
    @Transactional
    public Map<String, String> searchMemberId(Member member) {
        //메일과 이름으로 회원 조회
        List<Member> findOne = memberRepository.findByEmailName(member);
        if (findOne.isEmpty()) {
            //조회된 회원이 없으면 예외처리
            throw new CustomException(ErrorCode.NOT_MATCH_EMAIL_NAME);
        }

        //Map 컬렉션으로 반환
        Map<String, String> memberInform = new LinkedHashMap<>();

        memberInform.put("id", findOne.get(0).getMemberId());

        return memberInform;
    }

    /**
     * 로그인 서비스
     * @return DB의 회원과 일치하면 true, 아니면 false
     */
    @Transactional
    public Boolean logIn(Member member) {

        //회원ID로 회원 조회
        findByIdService(member.getMemberId());


        //회원ID와 비밀번호로 회원 조회
        List<Member> pwdMember = memberRepository.findByPasswordMemberId(member);
        if (pwdMember.isEmpty()) {
            //조회된 회원이 없으면 예외처리
            throw new CustomException(ErrorCode.NOT_MATCH_ID);
        }

        return true;
    }

    /**
     * 삭제 이전 회원 확인 서비스
     * @return DB의 회원과 일치하면 true, 아니면 false
     */
    @Transactional
    public Boolean checkBeforeDelete(Member member) {
        //회원 ID로 회원 조회
        findByIdService(member.getMemberId());

        //비밀번호와 이메일로 회원 조회
        List<Member> pwdMember = memberRepository.findByPasswordEmailMemberId(member);
        if (pwdMember.isEmpty()) {
            //조회된 회원이 없으면 예외처리
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD_ID);
        }

        return true;
    }

    /**
     * 회원 삭제 서비스
     * 이전 단계에서 검증을 했음으로 예외처리는 두지 않음
     */
    @Transactional
    public void deleteOne(String memberId) {
        //회원ID로 회원 조회
        List<Member> findOne = findByIdService(memberId);

        //회원 삭제
        memberRepository.deleteOne(findOne.get(0).getId());
    }

    /**
     * 회원 정보 조회 서비스
     * @return 회원ID로 조회한 회원 반환
     */
    @Transactional
    public MyPageDto searchMyPage(Member member) {
        //회원ID로 회원 조회
        List<Member> findOne = findByIdService(member.getMemberId());

        //Dto로 변환
        MyPageDto myPageDto = new MyPageDto().toDto(findOne.get(0));

        //회원 반환
        return myPageDto;
    }

    /**
     * 전체 회원 정보 조회 서비스
     * @return List 컬렉션으로 전체 회원 정보 반환
     */
    @Transactional
    public List<MemberDto> findMembers() {
        List<MemberDto> memberDtoList = new LinkedList<>();
        //전체 회원 조회
        List<Member> memberList = memberRepository.findAll();
        //Dto로 변환해서 반환
        for (Member member : memberList) {
            memberDtoList.add(new MemberDto().toDto(member));
        }
        return memberDtoList;
    }
}
