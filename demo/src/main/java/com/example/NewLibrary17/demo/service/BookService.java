package com.example.NewLibrary17.demo.service;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.model.Book;
import org.springframework.http.ResponseEntity;

public interface BookService {

    ResponseEntity<BookRegisteredDto> createBook(String token, BookRequestDto bookRequestDto) throws ForbiddenAccessException;
    BookUpdateDto updateBook(Integer id, BookUpdateDto bookUpdateDto, String token) throws ForbiddenAccessException;
    void deleteBook(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException;
    void reactivateBook(Book book);
    BookPaginatedDto getAllBooks(Integer page) throws ForbiddenAccessException;
    Book getBookById(Integer id);
    BookDetailDto getBookDetailById(Integer Id);
    Book getBookByTitle(String title);

}
