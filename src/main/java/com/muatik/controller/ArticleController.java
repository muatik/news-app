package com.muatik.controller;

import com.muatik.model.Article;
import com.muatik.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by muatik on 4/29/17.
 */
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public Iterable<Article> getAll() {
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable long id) throws Exception {
        Article article = articleService.findOne(id);
        if (article == null)
            return new ResponseEntity("article not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(article, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Article create(@RequestBody Article article) {
        return articleService.create(article);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        articleService.delete(id);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Article article) {
        Article updated = articleService.update(article);
        if (updated == null)
            return new ResponseEntity("article not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(updated, HttpStatus.OK);
    }
}
