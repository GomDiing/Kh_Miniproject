package comprehensive.demo.controller;

import comprehensive.demo.dto.movie.MovieDetailDto;
import comprehensive.demo.response.DefaultResponse;
import comprehensive.demo.service.MovieService;
import comprehensive.demo.response.ResponseMessage;
import comprehensive.demo.response.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieApi {


    private final MovieService movieService;

    /**
     * 현재 상영중인 영화 조회 컨트롤러
     */
    @PostMapping("/nowPlaying")
    public ResponseEntity getNowPlaying(Pageable pageable) {

        //Pageable로 조회하여 Map 컬렉션으로 반환
        Map<String, Object> response = movieService.findAllMovieNowPlaying(pageable);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_MOVIE_NOW_PLAYING, response), HttpStatus.OK);
    }

    /**
     * 인기 영화 목록 조회 컨트롤러
     */
    @PostMapping("/popular")
    public ResponseEntity getPopular(Pageable pageable) {

        //Pageable로 조회하여 Map 컬렉션으로 반환
        Map<String, Object> response = movieService.findAllMoviePopular(pageable);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_MOVIE_POPULAR, response), HttpStatus.OK);
    }

    /**
     * 역대 최고 영화 목록 조회 컨트롤러
     */
    @PostMapping("/topRated")
    public ResponseEntity getTopRated(Pageable pageable) {

        //Pageable로 조회하여 Map 컬렉션으로 반환
        Map<String, Object> response = movieService.findAllMovieTopRated(pageable);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_MOVIE_TOP_RATED, response), HttpStatus.OK);
    }

    /**
     * 곧 개봉예정 영화 목록 조회 컨트롤러
     */
    @PostMapping("/upcoming")
    public ResponseEntity getUpcoming(Pageable pageable) {

        //Pageable로 조회하여 Map 컬렉션으로 반환
        Map<String, Object> response = movieService.findAllMovieUpcoming(pageable);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_MOVIE_UPCOMING, response), HttpStatus.OK);
    }

    /**
     * 영화 검색 컨트롤러
     */
    @PostMapping("/search")
    public ResponseEntity search(@Validated @RequestParam("query") String search, Pageable pageable) {

        //Pageable로 조회하여 Map 컬렉션으로 반환
        Map<String, Object> response = movieService.findAllSearch(search, pageable);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_MOVIE_SEARCH, response), HttpStatus.OK);
    }

    /**
     * 영화 상세 페이지 컨트롤러
     */
    @PostMapping("/{movieId}")
    public ResponseEntity detail(@Validated @PathVariable Integer movieId) {

        //영화 상세 페이지 서비스 호출
        MovieDetailDto movieDetail = movieService.findMovieDetail(movieId);

        //응답 페이지에 포함해서 반환
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.SUCCESS_MOVIE_INQUIRE, movieDetail), HttpStatus.OK);
    }
}
