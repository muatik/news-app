package com.muatik.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by muatik on 4/29/17.
 */
@Data
@Entity
public class Article {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String text;

    // an article may have more than one author.
    // authors should already be persisted.
    @NotEmpty
    @ManyToMany(cascade = CascadeType.MERGE)
    Set<Author> authors;
}
