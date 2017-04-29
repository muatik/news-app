package com.muatik.repository;

import com.muatik.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by muatik on 4/29/17.
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
