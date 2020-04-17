package pl.mleczkomatyaszek.SoundShare.Exception;

public class UserAlreadyExists extends  RuntimeException{

    public UserAlreadyExists() {
        super("User with that username already exists");
    }
}
