package claudiopostiglione.u5w2d4.payloads;

import java.time.LocalDate;

public record ErrorsDTO(String message, LocalDate timestamp) {
}
