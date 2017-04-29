package com.muatik.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by muatik on 4/29/17.
 */
@Data
@Entity
public class Author {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    @Column(unique = true)
    private String email;

}