package comprehensive.demo.controller;

import comprehensive.demo.vo.reviewQna.SendReviewForm;
import comprehensive.demo.dto.reviewQna.QnaDto;
import comprehensive.demo.vo.reviewQna.SendQnaForm;
import comprehensive.demo.response.DefaultResponse;
import comprehensive.demo.service.ReviewQnaService;
import comprehensive.demo.response.ResponseMessage;
import comprehensive.demo.response.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class ReviewQnaApi {

    private final ReviewQnaService reviewQnaService;

    /**
     * 리뷰 전송 컨트롤러
     */
    @PostMapping(value = "/review")
    public ResponseEntity review(@Validated @RequestBody SendReviewForm sendReviewForm) {

        //리뷰 추가 서비스 호출해서 Map 컬렉션으로 변환
        Map<String, String> results = reviewQnaService.addReview(sendReviewForm);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_ADD_REVIEW, results), HttpStatus.OK);
    }

    /**
     * 문의 전송 컨트롤러
     */
    @PostMapping(value = "/qna/send")
    public ResponseEntity sendQna(@Validated @RequestBody SendQnaForm sendQnaForm) {

        //문의 추가 서비스 호출해서 Map 컬렉션으로 변환
        Map<String, String> results = reviewQnaService.addQna(sendQnaForm);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_SEND_QNA, results), HttpStatus.OK);
    }

    /**
     * 전체 문의 조회 컨트롤러
     */
    @PostMapping(value = "/qna/receive")
    public ResponseEntity receiveQna() {

        //전체 문의 조회 서비스 호출해서 List 컬렉션으로 변환
        List<QnaDto> qnaList = reviewQnaService.findAllQna();

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_RECEIVE_QNA, qnaList), HttpStatus.OK);
    }

}
