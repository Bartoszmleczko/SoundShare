package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SongModel {


    private Long song_id;

    @NonNull
    @NotNull(message = "Title of song can't be null.")
    @NotEmpty(message = "Title of song can't be empty.")
    @NotBlank(message = "Title of song can't be blank.")
    private String title;

    @NonNull
    private String lyrics;

    private List<@Min(value = 1,message = "Song rate must be at least 1.") Integer> ratings;

    private List<Long> posts;

    private Set<String> likes =new HashSet<>();

}
