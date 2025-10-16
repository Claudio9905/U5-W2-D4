package claudiopostiglione.u5w2d4.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "blog")
@NoArgsConstructor
@Data
public class Blog {

    //Attributi
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "Categoria")
    private String categoria;
    @Column(name = "Titolo")
    private String titolo;
    @Column(name = "Cover_url")
    private String cover;
    @Column(name = "Contenuto")
    private String contenuto;
    @Column(name = "Tempo_Di_Lettura")
    private int tempoDiLettura;


    @ManyToOne
    @JoinColumn(name = "author")
    private Autore author;

    //Costruttori
    public Blog(String categoria, String titolo, String contenuto, int tempoDiLettura) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
    }
}
