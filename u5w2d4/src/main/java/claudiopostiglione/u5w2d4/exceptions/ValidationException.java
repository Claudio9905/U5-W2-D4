package claudiopostiglione.u5w2d4.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private List<String> errorsMessages;

    public ValidationException(List<String> errorsMessages) {
        super("Si riportano degli errori di validazione");
        this.errorsMessages = errorsMessages;
    }
}
