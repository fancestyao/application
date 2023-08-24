package study.neo.application.exception;

import feign.FeignException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
public class ExceptHandler {
    @ExceptionHandler(FirstNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse firstNameValidationException(FirstNameException nameException) {
        return new ErrorResponse(nameException.getMessage());
    }

    @ExceptionHandler(LastNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse lastNameValidationException(LastNameException nameException) {
        return new ErrorResponse(nameException.getMessage());
    }

    @ExceptionHandler(MiddleNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse middleNameValidationException(MiddleNameException nameException) {
        return new ErrorResponse(nameException.getMessage());
    }

    @ExceptionHandler(CreditAmountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse creditAmountValidationException(CreditAmountException creditAmountException) {
        return new ErrorResponse(creditAmountException.getMessage());
    }

    @ExceptionHandler(CreditTermException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse creditTermValidationException(CreditTermException creditTermException) {
        return new ErrorResponse(creditTermException.getMessage());
    }

    @ExceptionHandler(BirthDateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse birthDateValidationException(BirthDateException birthDateException) {
        return new ErrorResponse(birthDateException.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse emailValidationException(EmailException emailException) {
        return new ErrorResponse(emailException.getMessage());
    }

    @ExceptionHandler(FeignException.FeignClientException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictExceptionHandler(FeignException.FeignClientException feignClientException) {
        String message = feignClientException.getMessage();
        Pattern pattern = Pattern.compile("\"error\":\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(message);
        log.warn(message);
        return matcher.find() ? new ErrorResponse(matcher.group(1)) : new ErrorResponse(message) ;
    }

    @ExceptionHandler(PassportException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse passportValidationException(PassportException passportException) {
        return new ErrorResponse(passportException.getMessage());
    }

    @Data
    @RequiredArgsConstructor
    private static class ErrorResponse {
        private final String error;
        private String description;
    }
}