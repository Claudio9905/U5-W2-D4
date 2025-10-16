package claudiopostiglione.u5w2d4.payloads;

import java.time.LocalDate;
import java.util.List;

public record ErrorsWithListDTO(String message, LocalDate timestamp, List<String> errorsList) {
}
