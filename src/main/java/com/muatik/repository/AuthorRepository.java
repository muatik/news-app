package com.muatik.repository;

import com.muatik.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by muatik on 4/29/17.
 */
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}