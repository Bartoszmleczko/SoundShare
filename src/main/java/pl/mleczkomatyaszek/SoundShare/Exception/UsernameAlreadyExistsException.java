package pl.mleczkomatyaszek.SoundShare.Exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("User with that login already exists");
    }
}
