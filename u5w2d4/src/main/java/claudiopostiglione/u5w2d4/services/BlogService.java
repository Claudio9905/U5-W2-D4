package claudiopostiglione.u5w2d4.services;

import claudiopostiglione.u5w2d4.entities.Autore;
import claudiopostiglione.u5w2d4.entities.Blog;
import claudiopostiglione.u5w2d4.exceptions.BadRequestException;
import claudiopostiglione.u5w2d4.exceptions.IdNotFoundException;
import claudiopostiglione.u5w2d4.payloads.BlogDTO;
import claudiopostiglione.u5w2d4.payloads.BlogPayload;
import claudiopostiglione.u5w2d4.repositories.AutoreRepository;
import claudiopostiglione.u5w2d4.repositories.BlogRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BlogService {

    private static final long MAX_SIZE = 5*1024*1024; //5MB
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");


    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AutoreService autoreService;

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private Cloudinary getImageUpLoader;


    // 1.
    public Page<Blog> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.blogRepository.findAll(pageable);
    }

    // 2.
    public Blog findBlogById(UUID idBlog) {
        return this.blogRepository.findById(idBlog).orElseThrow(() -> new IdNotFoundException(idBlog));
    }

    // 3.
    public Blog saveBlog(BlogDTO body) {

        //Verifico che un blog non ci sia già
        this.blogRepository.findByTitolo(body.titolo()).ifPresent(blog -> {
            throw new BadRequestException("Il blog con titolo " + blog.getTitolo() + " esiste già");
        });
        Blog newBlog = new Blog(body.categoria(), body.titolo(), body.contenuto(), body.tempoDiLettura());

        Autore autore = this.autoreService.findAutoreById(body.authorID());
        autore.getListaBlogs().add(newBlog);
        newBlog.setCover("https://picsum.photos/200/300");
        newBlog.setAuthor(autore);

        this.blogRepository.save(newBlog);

        log.info("Il blog + " + body.titolo() + " è stato aggiunto al database");
        return newBlog;
    }

    // 4.
    public Blog findBlogByIdAndUpdate(UUID idBlog, BlogPayload newBody) {

        Blog blogFound = this.findBlogById(idBlog);

        if (!blogFound.getTitolo().equals(newBody.getTitolo())) {
            this.blogRepository.findByTitolo(newBody.getTitolo()).ifPresent(blog -> {
                        throw new BadRequestException("Il blog con titolo " + blog.getTitolo() + " esiste già");
                    }
            );
        }

        blogFound.setCategoria(newBody.getCategoria());
        blogFound.setTitolo(newBody.getTitolo());
        blogFound.setCover("https://picsum.photos/200/300");
        blogFound.setContenuto(newBody.getContenuto());
        blogFound.setTempoDiLettura(newBody.getTempoDiLettura());

        Blog updateBlog = this.blogRepository.save(blogFound);

        log.info("Il blog con id " + updateBlog.getId() + " è stato modificato correttamente");

        return blogFound;

    }

    // 5.
    public void findBlogByIdAndDelete(UUID idBlog) {
        Blog blogFound = this.findBlogById(idBlog);
        this.blogRepository.delete(blogFound);
    }

    // 6.
    public Blog uploadCoverImage(MultipartFile file, UUID blogId){

        if(file.isEmpty()) throw new BadRequestException("File vuoto!");
        if(file.getSize()> MAX_SIZE) throw new BadRequestException("La dimensione del file supera quella massima!");
        if(!(ALLOWED_TYPES.contains(file.getContentType())))throw new BadRequestException("Formato file permesso: .jpeg, .png");

        // Controllo sull'esistenza dell'utente
        Blog blogFound = this.findBlogById(blogId);

        try {

            //Catturo l'URL dell'immagine della cover
            Map resultMap = getImageUpLoader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageCoverUrl = (String) resultMap.get("url");

            //Salvataggio dell'URL nel DB
            blogFound.setCover(imageCoverUrl);
            this.blogRepository.save(blogFound);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return blogFound;
    }
}
