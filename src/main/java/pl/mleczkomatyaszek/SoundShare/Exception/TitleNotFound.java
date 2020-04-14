package pl.mleczkomatyaszek.SoundShare.Exception;

public class TitleNotFound extends  RuntimeException{

    public TitleNotFound(String name, String title) {
        super(name + " with title " + title + " does not exist");
    }
}
