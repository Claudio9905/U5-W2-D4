package claudiopostiglione.u5w2d4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "autore")
@NoArgsConstructor
@Data
public class Autore {

    //Attributi
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "Nome")
    private String nome;
    @Column(name = "Cognome")
    private String cognome;
    @Column(name = "E-mail")
    private String email;
    @Column(name = "Data_di_Nascita")
    private LocalDate dataDiNascita;
    @Column(name = "Avatar_url")
    private String avatar;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Blog> listaBlogs = new ArrayList<>();

    //Construttori
    public Autore(String nome, String cognome, String email, LocalDate dataDiNascita){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataDiNascita = dataDiNascita;
    }
}
