package comprehensive.demo.service;

import comprehensive.demo.repository.GenreRepository;
import comprehensive.demo.entity.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 장르 관련 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    /**
     * 장르 조회 서비스
     * @return 조회한 장르 반환
     */
    @Transactional
    public Genre findOne(Integer id) {
        Genre findOne = genreRepository.findOne(id);
        return findOne;
    }
}
