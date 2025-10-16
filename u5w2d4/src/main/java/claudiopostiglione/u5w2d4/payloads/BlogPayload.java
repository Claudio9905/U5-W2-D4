package claudiopostiglione.u5w2d4.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BlogPayload {
    private String categoria;
    private String titolo;
    private String contenuto;
    private int tempoDiLettura;
    private UUID authorId;
}
