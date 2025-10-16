package claudiopostiglione.u5w2d4.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record BlogDTO(
        @NotBlank(message = "La categoria è obbligatoria")
        String categoria,
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,
        @NotBlank(message = "Il contenuto è obbligatorio")
        String contenuto,
        @NotBlank(message = "Il minutaggio della lettura è obbligatorio")
        @Min(value = 2, message = "Il tempo di lettura deve avere un minimo di 2 minuti")
        @Max(value = 20, message = "Il tempo di lettura deve avere un massimo di 20 minuti")
        int tempoDiLettura,
        @NotBlank(message = "L'ID dell'autore è obbligatorio")
        UUID authorID
) {
}
