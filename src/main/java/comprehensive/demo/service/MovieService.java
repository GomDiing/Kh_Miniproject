package comprehensive.demo.service;

import comprehensive.demo.dto.movie.*;
import comprehensive.demo.test.MovieCastDto;
import comprehensive.demo.test.MovieCategoryMovieDto;
import comprehensive.demo.test.MovieCategoryResultDto;
import comprehensive.demo.entity.Review;
import comprehensive.demo.exception.CustomException;
import comprehensive.demo.exception.ErrorCode;
import comprehensive.demo.repository.*;
import comprehensive.demo.entity.Genre;
import comprehensive.demo.entity.Movie;
import comprehensive.demo.dto.movie.MovieCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 영화 관련 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieNowPlayingRepository movieNowPlayingRepository;

    private final MoviePopularRepository moviePopularRepository;

    private final MovieTopRatedRepository movieTopRatedRepository;

    private final MovieUpcomingRepository movieUpcomingRepository;

    private final CastCrewPageRepository castCrewPageRepository;

    private final MovieSearchRepository movieSearchRepository;

    private final CreditCrewRepository creditCrewRepository;

    private final ReviewQnaRepository reviewQnaRepository;

    /**
     * 현재 상영중인 영화 조회 서비스
     */
    @Transactional
    public Map<String, Object> findAllMovieNowPlaying(Pageable pageable) {
        //서비스명 입력
        String category = "movies_now_playing";

        //Page 컬렉션으로 영화 조회
        Page<MovieCategoryDto> movieNowPlayingDto = movieNowPlayingRepository.findAllMovieNowPlayingDto(pageable);

        //포스터 경로 갱신
        Page<MovieCategoryDto> updatePosterMovieDto = updateCategoryPosterService(movieNowPlayingDto);

        //응답 페이지에 맞게 변환해서 반환
        return convertMovieDtoResult(updatePosterMovieDto, category);
    }

    /**
     * 인기 영화 목록 조회 서비스
     */
    @Transactional
    public Map<String, Object> findAllMoviePopular(Pageable pageable) {
        //서비스명 입력
        String category = "movies_popular";

        //Page 컬렉션으로 영화 조회
        Page<MovieCategoryDto> moviePopularDto = moviePopularRepository.findAllMoviePopularDto(pageable);

        //포스터 경로 갱신
        Page<MovieCategoryDto> updatePosterMovieDto = updateCategoryPosterService(moviePopularDto);

        //응답 페이지에 맞게 변환해서 반환
        return convertMovieDtoResult(updatePosterMovieDto, category);
    }

    /**
     * 역대 최고 영화 목록 조회 서비스
     */
    @Transactional
    public Map<String, Object> findAllMovieTopRated(Pageable pageable) {
        //서비스명 입력
        String category = "movies_top_rated";

        //Page 컬렉션으로 영화 조회
        Page<MovieCategoryDto> movieTopRatedDto = movieTopRatedRepository.findAllMovieTopRatedDto(pageable);

        //포스터 경로 갱신
        Page<MovieCategoryDto> updatePosterMovieDto = updateCategoryPosterService(movieTopRatedDto);

        //응답 페이지에 맞게 변환해서 반환
        return convertMovieDtoResult(updatePosterMovieDto, category);
    }

    /**
     * 곧 개봉예정 영화 목록 조회 서비스
     */
    @Transactional
    public Map<String, Object> findAllMovieUpcoming(Pageable pageable) {
        //서비스명 입력
        String category = "movies_upcoming";

        //Page 컬렉션으로 영화 조회
        Page<MovieCategoryDto> movieUpcomingDto = movieUpcomingRepository.findAllMovieUpcomingDto(pageable);

        //포스터 경로 갱신
        Page<MovieCategoryDto> updatePosterMovieDto = updateCategoryPosterService(movieUpcomingDto);

        //응답 페이지에 맞게 변환해서 반환
        return convertMovieDtoResult(updatePosterMovieDto, category);
    }

    /**
     * 영화 카테고리 DTO의 포스터 경로 갱신하는 서비스
     */
    public Page<MovieCategoryDto> updateCategoryPosterService(Page<MovieCategoryDto> movieCategoryDtoPage) {

        //영화 포스터 경로 갱신
        List<MovieCategoryDto> movieCategoryDtoContent = movieCategoryDtoPage.getContent();
        for (MovieCategoryDto movieCategoryDto : movieCategoryDtoContent) {
            movieCategoryDto.updatePosterPath();
        }

        return movieCategoryDtoPage;
    }

    /**
     * 영화 카테고리 응답 필드에 맞게 변환해서 반환
     * @return 응답 필드에 맞게 변환된 Map 컬렉션
     */
    private Map<String, Object> convertMovieDtoResult(Page<MovieCategoryDto> movieCategoryDtoPage, String category) {

        Map<String, Object> responsePage = new LinkedHashMap<>();

        //현재 page 위치, 총 page 수, 전체 결과 갯수 조회
        Integer page = movieCategoryDtoPage.getNumber() + 1;
        Integer totalPages = movieCategoryDtoPage.getTotalPages();
        Long totalResults = movieCategoryDtoPage.getTotalElements();

        //Map 컬렉션을 응답 페이지에 맞게 변환
        responsePage.put("page", page);
        responsePage.put("total_pages", totalPages);
        responsePage.put("total_results", totalResults);
        responsePage.put("category", category);
        responsePage.put("contents", movieCategoryDtoPage.getContent());

        return responsePage;
    }

    /**
     * 영화 검색 서비스
     */
    @Transactional
    public Map<String, Object> findAllSearch(String search, Pageable pageable) {
        //서비스명 입력
        String category = "movies_search";

        //조회할 배우 수
        Pageable topFive = PageRequest.of(0, 5);

        Page<MovieSearchDto> movieSearchDto;

        //한글 유무에따라 분기하여 검색하는 필드가 다르게 변환
        if (search.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            //한글이 포함될 경우 해당 Repository를 호출해 Page 컬렉션으로 영화 조회
            movieSearchDto = movieSearchRepository.findAllMovieSearchDtoKorean(search, pageable);
        }
        else {
            //그렇지않을 경우 해당 Repository를 호출해 Page 컬렉션으로 영화 조회
            movieSearchDto = movieSearchRepository.findAllMovieSearchDtoOriginal(search, pageable);
        }

        //비어있을경우 예외 호출
        if (movieSearchDto.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_SEARCH_DATA);
        }

        //포스터 경로 갱신
        updateSearchPosterService(movieSearchDto);

        //감독, 배우 정보 갱신
        updateSearchDtoCastDirectorBySize(topFive, movieSearchDto.getContent());

        //응답 페이지에 맞게 변환해서 반환
        return convertMovieSearchDto(movieSearchDto, category);
    }

    /**
     * 영화 검색 DTO의 포스터 경로 갱신하는 서비스
     */
    public void updateSearchPosterService(Page<MovieSearchDto> movieSearchDtoPage) {

        //포스터 경로 List 컬렉션 순회하면서 포스터 경로 갱신
        List<MovieSearchDto> movieSearchDtoPageContent = movieSearchDtoPage.getContent();
        for (MovieSearchDto movieSearchDto : movieSearchDtoPageContent) {
            movieSearchDto.updatePosterPath();
        }
    }

    /**
     * 영화 검색 응답 필드에 맞게 변환해서 반환
     * @return 응답 필드에 맞게 변환된 Map 컬렉션
     */
    private Map<String, Object> convertMovieSearchDto(Page<MovieSearchDto> movieSearchDtoPage, String category) {

        Map<String, Object> responsePage = new LinkedHashMap<>();

        //현재 page 위치, 총 page 수, 전체 결과 갯수 조회
        Integer page = movieSearchDtoPage.getNumber() + 1;
        Integer totalPages = movieSearchDtoPage.getTotalPages();
        Long totalResults = movieSearchDtoPage.getTotalElements();

        //Map 컬렉션을 응답 페이지에 맞게 변환
        responsePage.put("page", page);
        responsePage.put("total_pages", totalPages);
        responsePage.put("total_results", totalResults);
        responsePage.put("category", category);
        responsePage.put("contents", movieSearchDtoPage.getContent());

        return responsePage;
    }

    /**
     * 영화 검색 DTO에 배우와 감독 정보 갱신해주는 서비스
     */
    private void updateSearchDtoCastDirectorBySize(Pageable pageSize, List<MovieSearchDto> movieSearchDtoList) {

        //List 컬렉션의 영화 검색 DTO를 순회
        for (MovieSearchDto movieSearchDto : movieSearchDtoList) {

            //영화 고유 ID
            Integer movieId = movieSearchDto.getMovie_id();

            //주어진 크기(size)에 맞게 배우를 조회
            List<CastDto> movieCastDto = castCrewPageRepository.findAllMovieDefaultCastDto(movieId, pageSize);

            String directorName = "";

            //해당 영화 감독을 조회
            List<Integer> directorSearchId = creditCrewRepository.findDirectorByMovieId(movieId);
            //조회한 감독 이름이 비어있지않으면 실행
            if (!directorSearchId.isEmpty()) {
                directorName = castCrewPageRepository.findOne(directorSearchId.get(0)).get(0);
            }

            //영화 감독 이름 갱신
            movieSearchDto.updateDirector(directorName);

            //조회한 배우 List 컬렉션 순회하면서 프로필 경로 갱신하고 DTO에 추가
            for (CastDto castDto : movieCastDto) {
                castDto.updateProfilePath();
                movieSearchDto.addCast(castDto);
            }
        }
    }


    /**
     * 영화 상세 페이지 서비스
     */
    @Transactional
    public MovieDetailDto findMovieDetail(Integer movieId) {

        //조회할 영화 개수
        Pageable topTen = PageRequest.of(0, 10);

        //조회하여 상세 페이지 DTO에 맞게 변환
        MovieDetailDto movieDetailDto = convertMovieDetail(movieId, topTen);

        //영화 상세 페이지 DTO 반환
        return movieDetailDto;
    }

    private MovieDetailDto getMovieDetailDto(Integer movieId) {

        Pageable topTen = PageRequest.of(0, 10);

        MovieDetailDto movieDetailDto = convertMovieDetail(movieId, topTen);

        return movieDetailDto;
    }

    /**
     * 영화 상세 페이지 응답 필드에 맞게 변환해서 반환
     * @return 응답 필드에 맞게 변환된 DTO 클래스
     */
    private MovieDetailDto convertMovieDetail(Integer movieId, Pageable pageSize) {
        //영화 조회
        Movie findMovie = movieRepository.findOne(movieId);

        if (Objects.isNull(findMovie)) {
            //없으면 예외 처리
            throw new CustomException(ErrorCode.NOT_MOVIE_DATA);
        }

        //영화 DTO로 변환
        MovieDto movieDto = new MovieDto().toMovieDto(findMovie);

        //영화 뒷 배경, 유튜브 링크 목록, 배우 목록, 감독 번호, 영화 리뷰 조회
        List<String> backdropsByIdTopOne = movieRepository.findBackdropsByIdTopOne(movieId);
        List<String> youtubeKeyList = movieRepository.findYoutubeKeyById(movieId);
        List<CastDto> castDtoList = castCrewPageRepository.findAllMovieDefaultCastDto(movieId, pageSize);
        List<Integer> directorSearchId = creditCrewRepository.findDirectorByMovieId(movieId);
        List<Review> movieReview = reviewQnaRepository.findMovieReview(movieId);

        //영화 리뷰 목록, 유튜브 링크 목록 List 컬렉션 생성
        List<MovieReviewDto> movieReviewDtoList = new ArrayList<>();
        List<YoutubeUrlDto> youtubeUrlDtoList = new ArrayList<>();

        //영화 뒷배경, 감독 조회
        //조회되면 뒷배경 주소와 감독 이름을, 그렇지 않으면 문자열 null 반환
        String backdropPath = backdropsByIdTopOne.isEmpty() ? MovieCode.path_null : backdropsByIdTopOne.get(0);
        String directorName = directorSearchId.isEmpty() ? MovieCode.person_null : castCrewPageRepository.findOne(directorSearchId.get(0)).get(0);

        //조회한 영화 리뷰 목록 DTO로 변환해서 List 컬렉션에 추가
        if (!Objects.isNull(movieReview)) {
            for (Review review : movieReview) {
                movieReviewDtoList.add(new MovieReviewDto().toDto(review));
            }
        }

        //조회한 영화 유튜브 링크 목록 DTO로 변환해서 List 컬렉션에 추가
        if (!Objects.isNull(youtubeKeyList)) {
            for (String youtubeKey : youtubeKeyList) {
                youtubeUrlDtoList.add(new YoutubeUrlDto().toDto(youtubeKey));
            }
        }

        //영화의 장르 조회
        List<Genre> genreSearchList = findGenre(movieId);
        List<GenreDto> genreDtoList = new ArrayList<>();
        //조회한 영화 장르 목록 DTO로 변환해서 List 컬렉션에 추가
        if (!Objects.isNull(genreSearchList)) {
            for (Genre genre : genreSearchList) {
                GenreDto genreDto = new GenreDto();
                genreDtoList.add(genreDto.toGenreDto(genre));
            }
        }

        //조회한 영화 배우 프로필 사진 경로 갱신
        for (CastDto castDto : castDtoList) {
            castDto.updateProfilePath();
        }

        //영화 상세 정보 응답 페이지에 맞는 DTO로 변환
        MovieDetailDto movieDetailDto = new MovieDetailDto().create(movieDto, backdropPath, youtubeUrlDtoList, genreDtoList, castDtoList, directorName, movieReviewDtoList);
        //영화 뒷 배경, 유튜브 링크 주소 갱신
        movieDetailDto.updateBackdropPath();
        movieDetailDto.updateYoutubeKey();

        return movieDetailDto;
    }


    /**
     * 영화 조회 서비스
     * @return 조회한 영화
     */
    @Transactional
    public Movie findOne(Integer moviesId) {
        Movie findOne = movieRepository.findOne(moviesId);
        return findOne;
    }

    /**
     * 인기있는 상위 10개 영화 조회 서비스
     * @return 조회한 영화 List 컬렉션
     */
    @Transactional
    public List<Movie> findPopularMovie() {
        List<Movie> popularMovies = movieRepository.findByPopularity();
        return popularMovies;
    }

    /**
     * 장르 조회 서비스
     * @return 조회한 장르
     */
    @Transactional
    public List<Genre> findGenre(Integer id) {
        return movieRepository.findMovieGenre(id);
    }

    private Map<String, Object> getMovieCategoryDto(Page<MovieCategoryDto> movieDefaultDto, String category) {
        Pageable topTen = PageRequest.of(0, 10);

        MovieCategoryResultDto movieCategoryResultDto = new MovieCategoryResultDto();

        List<MovieCategoryDto> movieCategoryDtoList = movieDefaultDto.getContent();

        addCastMovie(topTen, movieCategoryResultDto, movieCategoryDtoList);

        Map<String, Object> responsePage = new LinkedHashMap<>();

        Integer page = movieDefaultDto.getNumber() + 1;
        Integer totalPages = movieDefaultDto.getTotalPages();
        Long totalResults = movieDefaultDto.getTotalElements();

        responsePage.put("page", page);
        responsePage.put("total_pages", totalPages);
        responsePage.put("total_results", totalResults);
        responsePage.put("category", category);
        responsePage.put("contents", movieCategoryResultDto.getContents());

        return responsePage;
    }

    private void addCastMovie(Pageable pageSize, MovieCategoryResultDto movieCategoryResultDto, List<MovieCategoryDto> movieCategoryDtoList) {
//        Integer count = 1;

        for (MovieCategoryDto movieCategoryDto : movieCategoryDtoList) {

//            movieCategoryDto.updateId(count++);

            movieCategoryDto.updatePosterPath();
            Integer movieId = movieCategoryDto.getMovie_id();

            MovieCategoryMovieDto movieDto = collectMovieData(movieId);

            List<CastDto> movieDefaultCreditCastDto = castCrewPageRepository.findAllMovieDefaultCastDto(movieId, pageSize);

            MovieCastDto movieCastDto = new MovieCastDto(movieCategoryDto);

            movieDto.updateBackdropPath();
            movieDto.updateYoutubeKey();

            for (CastDto castDto : movieDefaultCreditCastDto) {
                castDto.updateProfilePath();
                movieCastDto.addCast(castDto);
            }

            movieCastDto.addMovieDto(movieDto);


            movieCategoryResultDto.addMovieCastDto(movieCastDto);
        }
    }

    private MovieCategoryMovieDto collectMovieData(Integer movie_id) {
        List<String> backdropsByIdTopOne = movieRepository.findBackdropsByIdTopOne(movie_id);
        List<String> youtubeKeyByIdTopOne = movieRepository.findYoutubeKeyByIdTopOne(movie_id);
        String backdropPath;
        String youtubeKey;

        if (backdropsByIdTopOne.isEmpty()) {
            backdropPath = "null";
        }
        else {
            backdropPath = backdropsByIdTopOne.get(0);
        }

        if (youtubeKeyByIdTopOne.isEmpty()) {
            youtubeKey = "null";
        }
        else {
            log.info("movie_id={}",movie_id);
            youtubeKey = youtubeKeyByIdTopOne.get(0);
        }

        Movie findMovie = movieRepository.findOne(movie_id);

        List<Genre> genreSearchList = findGenre(movie_id);
        List<GenreDto> genreDtoList = new ArrayList<>();

        for (Genre genre : genreSearchList) {
            GenreDto genreDto = new GenreDto();
            genreDtoList.add(genreDto.toGenreDto(genre));
        }
//        MovieGenreDto movieGenreDto = new MovieGenreDto().toMovieGenreDto(movie, genreDtoList);
//        movieGenreDtoList.add(movieGenreDto);

        return new MovieCategoryMovieDto(findMovie, backdropPath, youtubeKey,genreDtoList);
    }





/*
        @Transactional
    public List<Genre> findGenre(Integer id) {
        List<MovieGenre> moviesGenre = movieRepository.findMovieGenre(id);
        List<Genre> genres = new ArrayList<>();
        for (MovieGenre movieGenre : moviesGenre) {
            log.info("movieGenre={}",movieGenre.getMovie());
            genres.add(movieRepository.findGenre(movieGenre.getGenre().getId()));
        }
        return genres;
    }

 */


//    @Transactional
//    public Map<String, Object> findAllMovieUpcoming(Pageable pageable) {
//        String category = "movies_upcoming";
//
//        Page<MovieCategoryDto> movieUpcomingDto = movieUpcomingRepository.findAllMovieUpcomingDto(pageable);
//
//        return getMovieCategoryDto(movieUpcomingDto, category);
//    }

    /*
    @Transactional
    public Map<String, Object> findAllSearch(String search, Pageable pageable) {
        String category = "movies_search";

//        Page<MovieCategoryDto> movieSearchDto = movieSearchRepository.findAllMovieSearchDto(search, pageable);
        Page<MovieSearchDto> movieSearchDto = movieSearchRepository.findAllMovieSearchDto(search, pageable);

        if (movieSearchDto.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_SEARCH_DATA);
        }

//        return movieSearchDto;

        return getMovieCategoryDto(movieSearchDto, category);
    }
*/

//    @Transactional
//    public Map<String, Object> findAllMovieNowPlaying(Pageable pageable) {
//        String category = "movies_now_playing";
//
//        Page<MovieCategoryDto> movieNowPlayingDto = movieNowPlayingRepository.findAllMovieNowPlayingDto(pageable);
//
//        return getMovieCategoryDto(movieNowPlayingDto, category);
//    }
}
