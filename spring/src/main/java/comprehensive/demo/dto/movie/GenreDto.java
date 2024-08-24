package comprehensive.demo.dto.movie;

import comprehensive.demo.entity.Genre;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 장르 DTO
 */
@Getter
public class GenreDto {
    private Integer id;
    private String name;

    /**
     * Genre Entity -> GenreDto DTO
     */
    public GenreDto toGenreDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.id = genre.getId();
        genreDto.name = genre.getName();

        return genreDto;
    }

    /**
     * List<Genre> -> List<GenreDto>
     */
    public List<GenreDto> toGenreDtoList(List<Genre> genreList) {
        List<GenreDto> genreDtoList = new ArrayList<>();

        for (Genre genre : genreList) {
            genreDtoList.add(toGenreDto(genre));

        }

        return genreDtoList;
    }
}
