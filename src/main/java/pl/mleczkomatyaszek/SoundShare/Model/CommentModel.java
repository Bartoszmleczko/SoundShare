package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentModel {

    private Long commentId;

    private String content;

    private Long postId;

}
