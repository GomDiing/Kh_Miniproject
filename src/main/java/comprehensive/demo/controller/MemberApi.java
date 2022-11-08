package comprehensive.demo.controller;

import comprehensive.demo.entity.Member;
import comprehensive.demo.dto.member.*;
import comprehensive.demo.response.DefaultResponse;
import comprehensive.demo.response.ResponseMessage;
import comprehensive.demo.response.StatusCode;
import comprehensive.demo.service.MemberService;
import comprehensive.demo.vo.member.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberApi {

    private final MemberService memberService;


    /**
     * 회원가입 컨트롤러
     */
    @PostMapping("/signup")
    public ResponseEntity signupMember(@Validated @RequestBody SignUpMemberForm signUpMemberForm) {

        //Entity 변환
        Member member = new Member().toEntity(signUpMemberForm);

        //회원가입 서비스 호출
        memberService.join(member);

        //응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_JOIN_MEMBER), HttpStatus.OK);
    }


    /**
     * 비밀번호 변경 컨트롤러
     */
    @PostMapping(value = "/updatePassword")
    public ResponseEntity updatePassword(@Validated @RequestBody UpdatePasswordForm updatePasswordForm) {

        //Entity 변환
        Member member = new Member().toEntity(updatePasswordForm);

        //조회 실행 (비밀번호 수정 전 디버깅용으로 조회, 없어도 무방)
        memberService.search(member.getMemberId());

        //비밀번호 수정 서비스 호출
        memberService.updatePassword(member);

        //응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_UPDATE_MEMBER), HttpStatus.OK);

    }

    /**
     * 비밀번호 찾기 컨트롤러
     */
    @PostMapping(value = "/searchPassword")
    public ResponseEntity searchPassword(@Validated @RequestBody SearchPasswordForm searchPasswordForm) {

        //Entity 변환
        Member member = new Member().toEntity(searchPasswordForm);

        //비밀번호와 이름 조회 서비스 호출
        Map<String, String> memberPasswordName = memberService.searchPasswordName(member);

        //응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_SEARCH_PASSWORD, memberPasswordName), HttpStatus.OK);
    }

    /**
     * 아이디 찾기 컨트롤러
     */
    @PostMapping(value = "/searchId")
    public ResponseEntity memberId(@Validated @RequestBody SearchIdMemberForm searchIdMemberForm) {

        //Entity 변환
        Member member = new Member().toEntity(searchIdMemberForm);

        //회원ID 조회 서비스 호출
        Map<String, String> memberName = memberService.searchMemberId(member);

        //응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_SEARCH_ID, memberName), HttpStatus.OK);
    }

    /**
     * 비밀번호 확인 컨트롤러
     */
    @PostMapping(value = "/checkPassword")
    public ResponseEntity checkPassword(@Validated @RequestBody SearchPasswordForm searchPasswordForm) {

        //Entity 변환
        Member member = new Member().toEntity(searchPasswordForm);

        //비밀번호와 이름 조회 서비스 호출
        memberService.searchPasswordName(member);

        //응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_CHECK_PASSWORD), HttpStatus.OK);
    }

    /**
     * 로그인 컨트롤러
     */
    @PostMapping(value = "/signin")
    public ResponseEntity signinMember(@Validated @RequestBody SignInMemberForm signInMemberForm) {

        //Entity 변환
        Member member = new Member().toEntity(signInMemberForm);

        //로그인 서비스 호출
        memberService.logIn(member);

        //응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_LOGIN), HttpStatus.OK);
    }

    /**
     * 회원 탈퇴 컨트롤러
     */
    @PostMapping(value = "/delete")
    public ResponseEntity deleteMember(@RequestBody DeleteMemberForm deleteMemberForm) {

        //Entity 변환
        Member member = new Member().toEntity(deleteMemberForm);

        //삭제 전 확인 서비스 호출
        memberService.checkBeforeDelete(member);

        //회원 삭제 서비스 호출
        memberService.deleteOne(member.getMemberId());

        //응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_DELETE), HttpStatus.OK);
    }

    /**
     * 회원 정보 조회 컨트롤러
     */
    @PostMapping(value = "/mypage")
    public ResponseEntity mypage(@RequestBody MyPageMemberForm myPageMemberForm) {

        //Entity 변환
        Member member = new Member().toEntity(myPageMemberForm);

        //회원 정보 조회 서비스 호출해서 DTO로 반환
        MyPageDto mypage = memberService.searchMyPage(member);

        //조회한 회원 정보 포함해서 응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_MYPAGE, mypage), HttpStatus.OK);
    }

    /**
     * 전체 회원 정보 조회 컨트롤러
     */
    @PostMapping(value = "/viewMembers")
    public ResponseEntity viewMembers() {

        //전체 회원 정보 조회 서비스 호출해서 List 컬렉션으로 반환
        List<MemberDto> memberList = memberService.findMembers();

        //조회한 전체 회원 정보 포함해서 응답 페이지 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_VIEW_MEMBERS, memberList), HttpStatus.OK);
    }
}