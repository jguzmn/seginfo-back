package group1.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ExistingEntityException.class, ExistingEntityException.class})
    public ResponseEntity<Object> existingResourceHandler(RuntimeException e) {
        ResponseConstructor response = new ResponseConstructor(Arrays.asList(e.getMessage()));
        return new  ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
