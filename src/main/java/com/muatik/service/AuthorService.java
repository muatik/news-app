package com.muatik.service;

import com.muatik.model.Author;
import com.muatik.repository.AuthorRepository;
import org.springframework.stereotype.Service;

/**
 * Created by muatik on 4/29/17.
 */
@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public void delete(long id) {
        authorRepository.delete(id);
    }

    public Author update(Author author) {
        Author current = authorRepository.findOne(author.getId());
        if (current == null)
            return null;
        return authorRepository.save(author);
    }

    public Author findOne(long id) {
        return authorRepository.findOne(id);
    }

    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    public static class AuthorNotFound extends Exception {
        public AuthorNotFound(long id) {
            super("author with " + id + " not found");
        }
    }
}
