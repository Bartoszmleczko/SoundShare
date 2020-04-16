package pl.mleczkomatyaszek.SoundShare.Exception;

public class GenericIdNotFoundException extends RuntimeException {

    public GenericIdNotFoundException(String name, Long id) {
        super(name + " with id " + id + " does not exist");
    }
}
