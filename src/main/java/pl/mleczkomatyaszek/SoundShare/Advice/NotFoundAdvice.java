package pl.mleczkomatyaszek.SoundShare.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;

@ControllerAdvice
public class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(GenericIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String idNotFound(GenericIdNotFoundException ex){
        return ex.getMessage();
    }

}
