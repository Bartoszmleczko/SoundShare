package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczkomatyaszek.SoundShare.Entity.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
}
