package claudiopostiglione.u5w2d4.controllers;

import claudiopostiglione.u5w2d4.entities.Autore;
import claudiopostiglione.u5w2d4.exceptions.ValidationException;
import claudiopostiglione.u5w2d4.payloads.AutoreDTO;
import claudiopostiglione.u5w2d4.payloads.AutorePayload;
import claudiopostiglione.u5w2d4.services.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AutoreController {

    @Autowired
    private AutoreService autoreService;

    //1 GET http://localhost:3004/authors
    @GetMapping
    public Page<Autore> getAuthors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.autoreService.findAll(page, size, sortBy);
    }

    // GET http://localhost:3004/authors/{authorsId}
    @GetMapping("/{authorId}")
    public Autore getAutoreById(@PathVariable UUID authorId) {
        return this.autoreService.findAutoreById(authorId);
    }

    // POST http://localhost:3004/authors (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autore createAutore(@RequestBody @Validated AutoreDTO body, BindingResult validationResult) {

        if(validationResult.hasErrors()){
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.autoreService.saveAutore(body);
    }

    // PUT http://localhost:3004/authors/{authorsId} (+ payload nuovo)
    @PutMapping("/{authorId}")
    public Autore findAutoreAndUpdate(@PathVariable UUID authorId, @RequestBody AutorePayload newBody) {
        return this.autoreService.findAutoreByIdAndUpdate(authorId, newBody);
    }

    // DELETE http://localhost:3004/authors/{authorsId}
    @DeleteMapping("{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAutoreAndDelete(@PathVariable UUID authorId) {
        this.autoreService.findAutoreByIdAndDelete(authorId);
    }

    //PATCH http://localhost:3004/authors/{authorsId}/avatar
    @PatchMapping("/{authorId}/avatar")
    public Autore uploadImageAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID authorId) throws IOException{
        System.out.println(Arrays.toString(file.getBytes()));
        System.out.println(file.getOriginalFilename());
        return this.autoreService.uploadAvatarImage(file, authorId);
    }

}
