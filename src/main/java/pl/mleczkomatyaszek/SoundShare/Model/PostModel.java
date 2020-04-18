package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PostModel {

    private Long postId;

    @NotNull(message = "Title of post can't be null")
    @NotEmpty(message = "Title of post can't be empty")
    @NotBlank(message = "Title of post can't be blank")
    private String title;

    @NotNull(message = "Post description can't be null")
    @NotEmpty(message = "Post description can't be empty")
    @NotBlank(message = "Post description can't be blank")
    private String description;

    @NotNull(message = "Post must contain song")
    @Min(value = 1,message = "Song id must be at least 1")
    private Long songId;
}
