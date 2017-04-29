package com.muatik.controller;

import com.muatik.model.Author;
import com.muatik.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by muatik on 4/29/17.
 */
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Iterable<Author> getAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable long id) throws Exception {
        Author author = authorService.findOne(id);
        if (author == null)
            return new ResponseEntity("author not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(author, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@RequestBody Author author) {
        return authorService.create(author);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        authorService.delete(id);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Author author) {
        Author updated = authorService.update(author);
        if (updated == null)
            return new ResponseEntity("author not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(updated, HttpStatus.OK);
    }

}
