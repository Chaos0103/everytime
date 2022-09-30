package project.everytime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.everytime.exception.DuplicateException;
import project.everytime.exception.NoSuchException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {

    @ExceptionHandler(DuplicateException.class)
    public String duplicateExceptionHandler(DuplicateException exception) {
        log.info("DuplicateExceptionHandler");
        return exception.getMessage();
    }

    @ExceptionHandler(NoSuchException.class)
    public String noSuchExceptionHandler(NoSuchException exception) {
        log.info("NoSuchExceptionHandler");
        return exception.getMessage();
    }
}
