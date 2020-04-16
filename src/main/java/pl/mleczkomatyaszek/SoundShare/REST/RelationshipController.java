package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mleczkomatyaszek.SoundShare.Entity.Relationship;
import pl.mleczkomatyaszek.SoundShare.Model.RelationshipModel;
import pl.mleczkomatyaszek.SoundShare.Service.RelationshipService;

import java.security.Principal;

@RestController
public class RelationshipController {

    private final RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @GetMapping("/relationships/{id}")
    public Relationship findRelationship(@PathVariable Long id){
        return relationshipService.getRelationship(id);
    }

    @PostMapping("/relationships")
    public Relationship saveRelationship(@RequestBody RelationshipModel model, Principal principal){
        return relationshipService.saveRelationship(model, principal) ;
    }

    @PutMapping("/relationships")
    public Relationship editRelationship(@RequestBody RelationshipModel model){
        return relationshipService.editRelationship(model);
    }

    @DeleteMapping("/relationships/{id}")
    public String deleteRelationship(@PathVariable Long id){
        return relationshipService.deleteRelationship(id);
    }

}
