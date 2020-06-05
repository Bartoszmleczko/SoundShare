package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LikeModel {

    @NonNull
    private Long postId;
    @NonNull
    private String like;

}
