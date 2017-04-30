package com.muatik.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by muatik on 4/29/17.
 */
@Data
@Entity
public class Article {
    public static final String DATE_PATTERN = "YYYY-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(length = 255)
    private String header;

    @NotNull
    private String text;

    /**
     * this filed is indexed because there is need to list all articles
     * for a given period
     */
    @NotNull
    @Column(name = "publish_date")
    @JsonFormat(pattern = DATE_PATTERN)
    private Date publishDate;

    // an article may have more than one author.
    // authors should already be persisted.
    @NotEmpty
    @ManyToMany(cascade = CascadeType.MERGE)
    Set<Author> authors;

    // I assume that keywords at least should not be longer than 255.
    // probably it will be shorter but not specified in the project description.
    @ElementCollection
    @Column(length = 255)
    Set<String> keywords = new HashSet<>();
}
