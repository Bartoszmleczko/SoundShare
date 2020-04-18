package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SongModel {


    private Long songId;

    @NonNull
    @NotNull(message = "Title of song can't be null.")
    @NotEmpty(message = "Title of song can't be empty.")
    @NotBlank(message = "Title of song can't be blank.")
    private String title;

    private List<@Min(value = 1,message = "Song rate must be at least 1.") Integer> ratings;

    private List<Long> posts;

}
