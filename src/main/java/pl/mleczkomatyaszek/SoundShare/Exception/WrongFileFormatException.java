package pl.mleczkomatyaszek.SoundShare.Exception;

public class WrongFileFormatException extends RuntimeException{
    public WrongFileFormatException() {
        super("File format must be .mp3 or .wav");
    }
}
