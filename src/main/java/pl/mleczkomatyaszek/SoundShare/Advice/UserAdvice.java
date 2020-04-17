package pl.mleczkomatyaszek.SoundShare.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.mleczkomatyaszek.SoundShare.Exception.UserAlreadyExists;

@ControllerAdvice
public class UserAdvice {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userAlreadyExists(UserAlreadyExists ex){
        return ex.getMessage();
    }

}
