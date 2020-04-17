package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mleczkomatyaszek.SoundShare.Entity.Relationship;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Model.RelationshipModel;
import pl.mleczkomatyaszek.SoundShare.Repository.RelationshipRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final UserService userService;

    @Autowired
    public RelationshipService(RelationshipRepository relationshipRepository, UserService userService) {
        this.relationshipRepository = relationshipRepository;
        this.userService = userService;
    }

    @Transactional
    public List<Relationship> findAll(){
        return relationshipRepository.findAll();
    }

    @Transactional
    public Relationship getRelationship(Long id ){
        return relationshipRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(Relationship.class.getSimpleName(),id));
    }

    @Transactional
    public Relationship saveRelationship(RelationshipModel model, Principal principal){
        Relationship relationship = new Relationship();
        User requester = userService.findByUsername(principal.getName());
        User friend = userService.findById(model.getFriendId());
        relationship.setRequester(requester);
        relationship.setFriend(friend);
        relationship.setActive(false);
        return relationshipRepository.save(relationship);
    }

    @Transactional
    public Relationship editRelationship(RelationshipModel model){
        Relationship relationship = new Relationship();
        relationship = this.getRelationship(model.getRelationshipId());
        relationship.setActive(model.isActive());
        return relationshipRepository.save(relationship);
    }

    @Transactional
    public String deleteRelationship(Long id){
        Relationship relationship = new Relationship();
        relationship = this.getRelationship(id);
        relationshipRepository.delete(relationship);
        return "Relationship deleted";
    }

}
