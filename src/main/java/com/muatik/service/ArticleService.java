package com.muatik.service;

import com.muatik.model.Article;
import com.muatik.model.Author;
import com.muatik.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by muatik on 4/29/17.
 */
@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    public void delete(long id) {
        articleRepository.delete(id);
    }

    public Article update(Article article) {
        Article current = articleRepository.findOne(article.getId());
        if (current == null)
            return null;
        return articleRepository.save(article);
    }

    public Article findOne(long id) {
        return articleRepository.findOne(id);
    }

    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    public Iterable<Article> findByAuthorId(Author author) {
        return articleRepository.findAllByAuthors(author);
    }

    public Iterable<Article> findByDateRange(Date startDate, Date endDate) {
        return articleRepository.findAllByPublishDateBetween(startDate, endDate);
    }

    public Iterable<Article> findByKeyword(String keyword) {
        return articleRepository.findAllByKeywords(keyword);
    }
}
