package com.example.NewLibrary17.demo.service;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.model.Author;
import org.springframework.http.ResponseEntity;

public interface AuthorService {

    ResponseEntity<AuthorRegisteredDto> createAuthor(String token, AuthorRequestDto authorRequestDto) throws ForbiddenAccessException;

    AuthorUpdateDto updateAuthor(Integer id, AuthorUpdateDto authorUpdateDto, String token) throws ForbiddenAccessException;

    void deleteAuthor(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException;

    void reactivateAuthor(Author author);

    AuthorPaginatedDto getAllAuthors(Integer page);

    Author getAuthorById(Integer id);

    Author getAuthorByName(String name);

    AuthorDetailDto getAuthorDetailById(Integer Id);

}
