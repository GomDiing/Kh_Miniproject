package comprehensive.demo.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 배우 정보 DTO
 */
@Getter
@AllArgsConstructor
public class CastDto {

    private String profile_path;

    private String name;

    private String character;

    private String known_for_department;

    /**
     * 배우 프로필 사진 경로 갱신
     * null 이 아니면 저화질 경로로, 그렇지 않으면 문자열 null 갱신
     */
    public void updateProfilePath() {

        this.profile_path = profile_path == null ? MovieCode.path_null : MovieCode.path_low + profile_path;
    }
}
