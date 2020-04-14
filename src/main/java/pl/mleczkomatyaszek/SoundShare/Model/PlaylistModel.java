package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class PlaylistModel {

    private Long playlistId;

    private String name;

    private List<Long> songs = new ArrayList<>();


}
