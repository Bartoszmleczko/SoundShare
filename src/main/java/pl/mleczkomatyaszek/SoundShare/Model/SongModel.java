package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SongModel {


    private Long songId;

    @NonNull
    private String title;

    @NonNull
    private String path;

    @NonNull
    private Integer rate;


}
