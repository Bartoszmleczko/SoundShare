package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RelationshipModel {

    private Long relationship_id;

    @NotNull(message = "Relationship must contain ID of a friend")
    @Min(value = 1,message = "ID of friend must be 1")
    private Long friendId;

    @NotNull(message = "Relationship status must be included")
    private boolean isActive;
}
