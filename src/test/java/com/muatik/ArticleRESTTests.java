package com.muatik;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muatik.model.Article;
import com.muatik.model.Author;
import com.muatik.service.ArticleService;
import com.muatik.service.AuthorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.muatik.model.Article.DATE_PATTERN;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by muatik on 5/1/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsAppApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArticleRESTTests {

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws ParseException {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Author author1 = new Author(1, "a1@a.com");
        Author author2 = new Author(2, "a2@a.com");
        author1 = authorService.create(author1);
        author2 = authorService.create(author2);

        Date date1 = new SimpleDateFormat(DATE_PATTERN).parse("2017-01-15 14:10:50");
        Date date2 = new SimpleDateFormat(DATE_PATTERN).parse("2017-01-15 17:10:20");

        Article article1 = new Article(
                null, "header 1", "article body 1",
                date1,
                Sets.newSet(author1),
                Sets.newSet("tech", "Mobile"));

        Article article2 = new Article(
                null, "header 2", "article body 2",
                date2,
                Sets.newSet(author1, author2),
                Sets.newSet("tech", "AI"));

        articleService.create(article1);
        articleService.create(article2);
    }

    @Test
    public void addAuthorTest() throws Exception {
        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk());
        Author author3 = new Author();
        author3.setEmail("a3@a.com");

        mockMvc.perform(
                post("/api/v1/authors")
                        .content(toJson(author3))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3))); // checks that response has the author entity and has id

        Assert.assertEquals(authorService.findOne(3).getEmail(), author3.getEmail());
    }


    @Test
    public void testFilteringArticlesByAuthor() throws Exception {
        mockMvc.perform(get("/api/v1/articles?authorId=2"))
                .andExpect(jsonPath("$", hasSize(1))) // only the first article
                .andExpect(jsonPath("$[0].id", is(2)));

        mockMvc.perform(get("/api/v1/articles?authorId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // The second author has 2 articles
                .andExpect(jsonPath("$[0].id", anyOf(is(1), is(2))))
                .andExpect(jsonPath("$[1].id", anyOf(is(1), is(2))));
    }

    @Test
    public void testFilteringArticlesByKeyword() throws Exception {
        mockMvc.perform(get("/api/v1/articles?keyword=tech"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // only two articles match 'tech'
                .andExpect(jsonPath("$[0].id", anyOf(is(1), is(2))))
                .andExpect(jsonPath("$[1].id", anyOf(is(1), is(2))));
        mockMvc.perform(get("/api/v1/articles?keyword=AI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)));
        mockMvc.perform(get("/api/v1/articles?keyword=None"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testFilteringArticlesByDate() throws Exception {
        mockMvc.perform(get("/api/v1/articles?startDate=2017-01-15 14:10:49&endDate=2017-01-15 17:10:21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // only two articles match 'tech'
                .andExpect(jsonPath("$[0].id", anyOf(is(1), is(2))))
                .andExpect(jsonPath("$[1].id", anyOf(is(1), is(2))));
        mockMvc.perform(get("/api/v1/articles?startDate=2017-01-15 17:10:20&endDate=2017-01-15 17:10:20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)));
        mockMvc.perform(get("/api/v1/articles?startDate=2017-01-15 14:10:50&endDate=2017-01-15 14:10:50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
        mockMvc.perform(get("/api/v1/articles?startDate=xxx&endDate=2017"))
                .andExpect(status().isBadRequest());
    }

    private byte[] toJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsBytes(o);
    }

}
