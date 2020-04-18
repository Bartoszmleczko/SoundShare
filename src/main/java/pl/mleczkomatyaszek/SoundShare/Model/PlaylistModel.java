package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class PlaylistModel {

    private Long playlistId;

    @NotNull(message = "Name of playlist can't be null")
    @NotEmpty(message = "Name of playlist can't be empty")
    @NotBlank(message = "Name of playlist can't be blank")
    private String name;

    private List<Long> songs = new ArrayList<>();


}
