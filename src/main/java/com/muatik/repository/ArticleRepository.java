package com.muatik.repository;

import com.muatik.model.Article;
import com.muatik.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by muatik on 4/29/17.
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Iterable<Article> findAllByAuthors(Author author);

    Iterable<Article> findAllByPublishDateBetween(Date startDate, Date endDate);

    Iterable<Article> findAllByKeywords(String keyword);
}
