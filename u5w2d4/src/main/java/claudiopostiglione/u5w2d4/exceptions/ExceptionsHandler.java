package claudiopostiglione.u5w2d4.exceptions;


import claudiopostiglione.u5w2d4.payloads.ErrorsDTO;
import claudiopostiglione.u5w2d4.payloads.ErrorsPayload;
import claudiopostiglione.u5w2d4.payloads.ErrorsWithListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class ExceptionsHandler extends RuntimeException {


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsWithListDTO handleValidationErrors(ValidationException ex) {
        return new ErrorsWithListDTO(ex.getMessage(), LocalDate.now(), ex.getErrorsMessages());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayload handleBadRequest(BadRequestException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDate.now());
    }

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleIdNotFound(IdNotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDate.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload handleServerError(Exception ex) {
        ex.printStackTrace();
        return new ErrorsPayload(ex.getMessage(), LocalDate.now());
    }

}
