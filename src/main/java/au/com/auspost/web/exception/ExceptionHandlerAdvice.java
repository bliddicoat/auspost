package au.com.auspost.web.exception;

import au.com.auspost.dto.MessageDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
@RestController
public class ExceptionHandlerAdvice {
    private final Logger log = getLogger(getClass());
    @Autowired
    private MessageSource messageSource;
    private static final String INTERNAL_SERVER_ERROR = "internal.server.error";
    private static final String NOT_FOUND = "notfound.postage.error";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageDTO handleValidationException(MethodArgumentNotValidException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String code;
        if (ex.getBindingResult().getGlobalError() == null) {
            code = ex.getBindingResult().getFieldError().getDefaultMessage();
        }
        else {
            code = ex.getBindingResult().getGlobalError().getDefaultMessage();
        }
        return new MessageDTO(messageSource.getMessage(code, null, locale));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public MessageDTO handleThrowable(final Throwable exception) {
        log.error("An unexpected error occurred: {}", exception.getClass().getName());
        log.debug("Error details: ", exception);
        Locale locale = LocaleContextHolder.getLocale();
        return new MessageDTO(messageSource.getMessage(INTERNAL_SERVER_ERROR, null, locale));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public MessageDTO handleNotFound(NotFoundException e) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = String.valueOf(e.getMessage());
        return new MessageDTO(messageSource.getMessage(NOT_FOUND, new Object[]{message}, null, locale));
    }
}
