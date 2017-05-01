package com.muatik.controller;

import com.muatik.model.Article;
import com.muatik.service.ArticleService;
import com.muatik.service.AuthorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.muatik.model.Article.DATE_PATTERN;

/**
 * Created by muatik on 4/29/17.
 */
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private ArticleService articleService;
    private AuthorService authorService;

    public ArticleController(ArticleService articleService, AuthorService authorService) {
        this.articleService = articleService;
        this.authorService = authorService;
    }

    @GetMapping
    public Iterable<Article> getAll(
            @RequestParam(value = "authorId", required = false) Long authorId,
            @RequestParam(value = "startDate", required = false)
                @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
            @RequestParam(value = "endDate", required = false)
                @DateTimeFormat(pattern = DATE_PATTERN) Date endDate,
            @RequestParam(value = "keyword", required = false) String keyword) {

        if (authorId != null) {
            return articleService.findByAuthorId(authorService.findOne(authorId));
        } else if (startDate != null && endDate != null) {
            return articleService.findByDateRange(startDate, endDate);
        } else if (keyword != null) {
           return articleService.findByKeyword(keyword);
        } else {
            return articleService.findAll();
        }

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
