package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentModel {

    private Long commentId;

    @NotNull(message = "Content cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Comment must be attached to post")
    @Min(value = 1,message = "Id of post must be at least 1")
    private Long postId;

}
