package com.muatik.service;

import com.muatik.model.Article;
import com.muatik.repository.ArticleRepository;
import org.springframework.stereotype.Service;

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
}
