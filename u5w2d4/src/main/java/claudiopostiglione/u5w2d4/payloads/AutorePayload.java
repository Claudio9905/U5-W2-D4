package claudiopostiglione.u5w2d4.payloads;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AutorePayload {
    private String nome;
    private String cognome;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDiNascita;
}
