package claudiopostiglione.u5w2d4.controllers;

import claudiopostiglione.u5w2d4.entities.Autore;
import claudiopostiglione.u5w2d4.entities.Blog;
import claudiopostiglione.u5w2d4.exceptions.ValidationException;
import claudiopostiglione.u5w2d4.payloads.BlogDTO;
import claudiopostiglione.u5w2d4.payloads.BlogPayload;
import claudiopostiglione.u5w2d4.services.AutoreService;
import claudiopostiglione.u5w2d4.services.BlogService;
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
@RequestMapping("/blogPosts")
public class BlogController {

    @Autowired
    private BlogService blogService;
    private AutoreService autoreService;

    //1 GET http://localhost:3004/blogPosts
    @GetMapping
    public Page<Blog> getBlogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id")String sortBy) {
        return this.blogService.findAll(page,size,sortBy);
    }

    // GET http://localhost:3004/blogPosts/{blogId}
    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable UUID blogId) {
        return blogService.findBlogById(blogId);
    }

    // POST http://localhost:3004/blogPosts (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog createBlog(@RequestBody @Validated BlogDTO body, BindingResult validationResult) {

        if(validationResult.hasErrors()){
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.blogService.saveBlog(body);
    }

    // PUT http://localhost:3004/blogPosts/{blogId} (+ payload nuovo)
    @PutMapping("/{blogId}")
    public Blog findBlogByIdAndUpdate(@PathVariable UUID blogId, @RequestBody BlogPayload newBody) {
        return this.blogService.findBlogByIdAndUpdate(blogId, newBody);
    }

    // DELETE http://localhost:3004/blogPosts/{blogId}
    @DeleteMapping("{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findBlogByIdAndDelete(@PathVariable UUID blogId) {
        this.blogService.findBlogByIdAndDelete(blogId);
    }

    //PATCH http://localhost:3004/authors/{authorsId}/avatar
    @PatchMapping("/{blogId}/cover")
    public Blog uploadImageCover(@RequestParam("cover") MultipartFile file, @PathVariable UUID blogId) throws IOException {
        System.out.println(Arrays.toString(file.getBytes()));
        System.out.println(file.getOriginalFilename());
        return this.blogService.uploadCoverImage(file, blogId);
    }
}
