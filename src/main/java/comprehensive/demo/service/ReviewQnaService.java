package comprehensive.demo.service;

import comprehensive.demo.vo.reviewQna.SendReviewForm;
import comprehensive.demo.dto.reviewQna.QnaDto;
import comprehensive.demo.vo.reviewQna.SendQnaForm;
import comprehensive.demo.entity.Member;
import comprehensive.demo.entity.Movie;
import comprehensive.demo.entity.Qna;
import comprehensive.demo.entity.Review;
import comprehensive.demo.exception.CustomException;
import comprehensive.demo.exception.ErrorCode;
import comprehensive.demo.repository.MemberRepository;
import comprehensive.demo.repository.MovieRepository;
import comprehensive.demo.repository.ReviewQnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 리뷰와 문의 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewQnaService {


    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final ReviewQnaRepository reviewQnaRepository;

    /**
     * 리뷰 추가 서비스
     * 리뷰를 생성해서 DB에 추가하고 리뷰 내용 Map 컬렉션으로 반환
     */
    @Transactional
    public Map<String, String> addReview(SendReviewForm sendReviewForm) {

        //회원ID로 회원 조회
        List<Member> findOne = memberRepository.findById(sendReviewForm.getMemberId());
        if (findOne.isEmpty()) {
            //조회된 회원이 없다면 예외 처리
            throw new CustomException(ErrorCode.EMPTY_MEMBER);
        }

        Member member = findOne.get(0);

        //영화ID로 영화 조회
        Movie movie = movieRepository.findOne(sendReviewForm.getMovieId());

        //리뷰 생성
        Review review = new Review().createReview(movie, member, sendReviewForm.getReview());

        //리뷰 추가
        reviewQnaRepository.saveReview(review);

        //Map 컬렉션으로 반환
        Map<String, String> results = new HashMap<>();
        results.put("review", review.getComment());

        return results;
    }

    /**
     * 문의 추가 서비스
     * 문의를 생성해서 DB에 추가하고 문의 내용 Map 컬렉션으로 반환
     */
    @Transactional
    public Map<String, String> addQna(SendQnaForm sendQnaForm) {

        //회원ID로 회원 조회
        List<Member> findOne = memberRepository.findById(sendQnaForm.getMemberId());
        if (findOne.isEmpty()) {
            //조회된 회원이 없다면 예외 처리
            throw new CustomException(ErrorCode.EMPTY_MEMBER);
        }
        Member member = findOne.get(0);

        //문의 생성
        Qna qna = new Qna().createQna(member, sendQnaForm.getQna_title(), sendQnaForm.getQna_content());

        //문의 추가
        reviewQnaRepository.saveQna(qna);

        //Map 컬렉션으로 반환
        Map<String, String> results = new HashMap<>();
        results.put("qna_title", qna.getQna_title());
        results.put("qna_content", qna.getQna_content());

        return results;
    }

    /**
     * 전체 문의 조회 서비스
     * @return List 컬렉션으로 전체 문의 정보 반환
     */
    @Transactional
    public List<QnaDto> findAllQna() {
        List<QnaDto> qnaDtoList = new LinkedList<>();
        //전체 문의 조회
        List<Qna> qnaList = reviewQnaRepository.findAllQna();
        //Dto로 변환해서 반환
        for (Qna qna : qnaList) {
            qnaDtoList.add(new QnaDto().toDto(qna));
        }

        return qnaDtoList;
    }
}
