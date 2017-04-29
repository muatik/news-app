package com.muatik.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
}
