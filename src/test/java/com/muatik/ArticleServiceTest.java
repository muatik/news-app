package com.muatik;

import com.muatik.model.Article;
import com.muatik.model.Author;
import com.muatik.repository.ArticleRepository;
import com.muatik.service.ArticleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by muatik on 5/1/17.
 */
@RunWith(SpringRunner.class)
public class ArticleServiceTest {

    private Article article1;
    private Article article2;
    private Article article3;

    private ArticleService articleService;
    private ArticleRepository articleRepository;

    @Before
    public void setup() {


        Author author1 = new Author(1, "a1@a.com");
        Author author2 = new Author(2, "a2@a.com");
        article1 = new Article(
                1L, "header 1", "hello",
                new Date(),
                Sets.newSet(author1, author2),
                Sets.newSet("tech", "AI"));

        article2 = new Article(
                1L, "header 1", "hello",
                new Date(),
                Sets.newSet(author2),
                Sets.newSet("tech", "Mobile"));


        List<Article> articleList = Arrays.asList(article1, article2);
        articleRepository = mock(ArticleRepository.class);
        articleList.forEach(article -> {
            when(articleRepository.save(article)).thenReturn(article);
            when(articleRepository.findOne(article.getId())).thenReturn(article);
        });
        when(articleRepository.findAll()).thenReturn(articleList);

        articleService = new ArticleService(articleRepository);
    }

    @Test
    public void shouldCallSave() {
        articleService.create(article1);
        verify(articleRepository, times(1)).save(article1);
    }

    @Test
    public void shouldCreateAndReturn() {
        Article createdArticle = articleService.create(article2);
        Assert.assertEquals(article2, createdArticle);
    }

    @Test
    public void verifyArticleExistBeforeUpdate() {
        Article updatedArticle = articleService.update(article1);
        Assert.assertEquals(article1.getId(), updatedArticle.getId());
    }


    @Test
    public void verifyFindingAllArticles() {
        Assert.assertEquals(
                Arrays.asList(article1, article2),
                articleService.findAll());
        verify(articleRepository, times(1)).findAll();
    }

}
