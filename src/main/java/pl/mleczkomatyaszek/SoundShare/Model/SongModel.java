package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SongModel {


    private Long songId;

    @NonNull
    private String title;

    private List<Integer> ratings;

    private List<Long> posts;

}
