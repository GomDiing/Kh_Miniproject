package comprehensive.demo.dto.movie;

import lombok.Getter;

/**
 * 유튜브 DTO
 */
@Getter
public class YoutubeUrlDto {
    private String youtube_url;

    /**
     * 유튜브 링크 주소 갱신
     */
    public void updateYoutubeUrl() {
        this.youtube_url = MovieCode.path_youtube + youtube_url;
    }

    /**
     * 유튜브 DTO 변환
     */
    public YoutubeUrlDto toDto(String youtubeUrl) {
        this.youtube_url = youtubeUrl;
        return this;
    }
}
