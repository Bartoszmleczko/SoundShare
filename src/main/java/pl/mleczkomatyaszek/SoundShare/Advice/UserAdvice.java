package pl.mleczkomatyaszek.SoundShare.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.mleczkomatyaszek.SoundShare.Exception.UsernameAlreadyExistsException;

@ControllerAdvice
public class UserAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String usernameAlreadyExists(UsernameAlreadyExistsException ex){
        return ex.getMessage();
    }

}
