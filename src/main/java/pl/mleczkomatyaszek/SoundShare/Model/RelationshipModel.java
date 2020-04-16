package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class RelationshipModel {

    private Long relationshipId;

    private Long friendId;

    private boolean isActive;
}
