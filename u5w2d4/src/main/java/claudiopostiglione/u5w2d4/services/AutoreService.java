package claudiopostiglione.u5w2d4.services;

import claudiopostiglione.u5w2d4.entities.Autore;
import claudiopostiglione.u5w2d4.exceptions.BadRequestException;
import claudiopostiglione.u5w2d4.exceptions.IdNotFoundException;
import claudiopostiglione.u5w2d4.payloads.AutoreDTO;
import claudiopostiglione.u5w2d4.payloads.AutorePayload;
import claudiopostiglione.u5w2d4.repositories.AutoreRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AutoreService {

    private static final long MAX_SIZE = 5 * 1024 * 1024; //5MB
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");


    @Autowired
    private AutoreRepository autoreRepository;
    @Autowired
    private Cloudinary imageUploader;

    // 1.
    public Page<Autore> findAll(int numPage, int sizePage, String sortBy) {

        if (sizePage > 50) sizePage = 50;
        sortBy = "nome";
        Pageable pageable = PageRequest.of(numPage, sizePage, Sort.by(sortBy).ascending());

        return this.autoreRepository.findAll(pageable);
    }

    // 2.
    public Autore findAutoreById(UUID idAutore) {

        return this.autoreRepository.findById(idAutore).orElseThrow(() -> new IdNotFoundException(idAutore));
    }

    // 3.
    public Autore saveAutore(AutoreDTO body) {

        //Controllo se l'email non è già in uso
        this.autoreRepository.findByEmail(body.email()).ifPresent(autore -> {
            throw new BadRequestException("L'email " + autore.getEmail() + " esiste già");
        });

        //Aggiungo dei campi server-generated (in questo caso per l'avatar_url)
        Autore newAutore = new Autore(body.nome(), body.cognome(), body.email(), body.dataDiNascita());
        newAutore.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        this.autoreRepository.save(newAutore);

        log.info("L'autore + " + body.nome() + body.cognome() + " è stato aggiunto al database");
        return newAutore;
    }

    // 4.
    public Autore findAutoreByIdAndUpdate(UUID idAutore, AutorePayload newBody) {

        Autore autoreFound = this.findAutoreById(idAutore);

        if (!autoreFound.getEmail().equals(newBody.getEmail())) {
            this.autoreRepository.findByEmail(newBody.getEmail()).ifPresent(autore -> {
                throw new BadRequestException("L'e-mail " + autore.getEmail() + " è già presente");
            });
        }

        autoreFound.setNome((newBody.getNome()));
        autoreFound.setCognome((newBody.getCognome()));
        autoreFound.setEmail(newBody.getEmail());
        autoreFound.setDataDiNascita(newBody.getDataDiNascita());
        autoreFound.setAvatar("https://ui-avatars.com/api/?name=" + newBody.getNome() + "+" + newBody.getCognome());

        Autore updateAutore = this.autoreRepository.save(autoreFound);

        log.info("L'autore " + updateAutore.getId() + " è stato modificato correttamente");
        return updateAutore;
    }

    // 5.
    public void findAutoreByIdAndDelete(UUID idAutore) {

        Autore autoreFound = this.findAutoreById(idAutore);
        this.autoreRepository.delete(autoreFound);
    }

    // 6.
    public Autore uploadAvatarImage(MultipartFile file, UUID authorId) {

        if (file.isEmpty()) throw new BadRequestException("File vuoto!");
        if (file.getSize() > MAX_SIZE) throw new BadRequestException("La dimensione del file supera quella massima!");
        if (!(ALLOWED_TYPES.contains(file.getContentType())))
            throw new BadRequestException("Formato file permesso: .jpeg, .png");

        // Controllo sull'esistenza dell'utente
        Autore autoreFound = this.findAutoreById(authorId);

        try {

            //Catturo l'URL dell'immagine
            Map resultMap = imageUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageAvatarUrl = (String) resultMap.get("url");

            //Salvataggio dell'URL nel DB
            autoreFound.setAvatar(imageAvatarUrl);
            this.autoreRepository.save(autoreFound);
            return autoreFound;

        } catch (IOException e) {
            throw new BadRequestException("Errore nell'upload dell'immagine");
        }

    }

}
