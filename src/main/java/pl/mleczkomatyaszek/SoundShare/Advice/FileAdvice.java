package pl.mleczkomatyaszek.SoundShare.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.mleczkomatyaszek.SoundShare.Exception.WrongFileFormatException;

@ControllerAdvice
public class FileAdvice {

    @ResponseBody
    @ExceptionHandler(WrongFileFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String wrongFormat(WrongFileFormatException ex){
        return ex.getMessage();
    }

}
