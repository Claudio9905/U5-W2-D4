package claudiopostiglione.u5w2d4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutoreDTO(
        @NotBlank(message = "il nome è obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve avere un minimo di 2 caratteri e un massimo di 30")
        String nome,
        @NotBlank(message = "il cognome è obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve avere un minimo di 2 caratteri e un massimo di 30")
        String cognome,
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'indirizzo email inserito non è del formato corretto")
        String email,
        @NotBlank(message = "La data di nascita è obbligatoria")
        LocalDate dataDiNascita
) {
}
