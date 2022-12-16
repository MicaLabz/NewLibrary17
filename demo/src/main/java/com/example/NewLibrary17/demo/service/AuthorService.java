package com.example.NewLibrary17.demo.service;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Client;

public interface AuthorService {

    AuthorDto createAuthor(String token, AuthorRequestDto authorRequestDto) throws ForbiddenAccessException;

    AuthorUpdateDto updateAuthor(Integer id, AuthorUpdateDto authorUpdateDtoUpdateDto, String token) throws ForbiddenAccessException;

    void deleteAuthor(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException;

    void reactivateAuthor(Author author);

    AuthorPaginatedDto getAllAuthors(Integer page, String token) throws ForbiddenAccessException;

    Author getAuthorById(Integer id);

    Author getAuthorByName(String name);

    AuthorDetailDto getAuthorDetailById(Integer Id);

}
