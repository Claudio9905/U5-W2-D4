package claudiopostiglione.u5w2d4.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ErrorsPayload {
    private String message;
    private LocalDate timestamp;
}
