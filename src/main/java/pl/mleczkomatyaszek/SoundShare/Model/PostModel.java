package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostModel {

    private Long postId;

    private String title;

    private String description;

    private Long songId;
}
