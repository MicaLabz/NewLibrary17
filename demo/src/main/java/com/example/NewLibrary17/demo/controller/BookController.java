package com.example.NewLibrary17.demo.controller;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/register")
    public ResponseEntity<BookRegisteredDto> registerBook(@Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token , @RequestBody BookRequestDto book) throws ForbiddenAccessException {
        return bookService.createBook(token, book);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<BookUpdateDto> updateBook(@Parameter(description = "id book to be updated") @PathVariable("id") Integer id, @RequestBody BookUpdateDto bookUpdateDto, @Parameter(description = "authentication token") @RequestHeader("Authorization") String token) throws ForbiddenAccessException {
        return ResponseEntity.ok(bookService.updateBook(id,bookUpdateDto,token));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER_ROLE')")
    @GetMapping
    public BookPaginatedDto getAll(@Param("page") Integer page) throws ForbiddenAccessException {
        return bookService.getAllBooks(page);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseStatus( OK )
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @DeleteMapping( "/{id}" )
    public void deleteById( @Parameter(description = "id book to be deleted") @PathVariable Integer id, @Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token ) throws ResourceNotFoundException, ForbiddenAccessException {
        bookService.deleteBook(id ,token);
    }
}
