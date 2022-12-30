package com.example.NewLibrary17.demo.controller;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.service.AuthorService;
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
@RequestMapping("/authors")
@Slf4j
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER_ROLE')")
    @GetMapping
    public AuthorPaginatedDto getAll(@Param("page") Integer page) {
        return authorService.getAllAuthors(page);
    }

    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/register")
    public ResponseEntity<AuthorRegisteredDto> registerAuthor(@Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token , @RequestBody AuthorRequestDto author) throws ForbiddenAccessException {
        return authorService.createAuthor(token, author);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<AuthorUpdateDto> updateAuthor(@Parameter(description = "id author to be updated") @PathVariable("id") Integer id, @RequestBody AuthorUpdateDto authorUpdateDto, @Parameter(description = "authentication token") @RequestHeader("Authorization") String token) throws ForbiddenAccessException {
        return ResponseEntity.ok(authorService.updateAuthor(id,authorUpdateDto,token));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseStatus( OK )
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @DeleteMapping( "/{id}" )
    public void deleteById( @Parameter(description = "id author to be deleted") @PathVariable Integer id, @Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token ) throws ResourceNotFoundException, ForbiddenAccessException {
        authorService.deleteAuthor(id, token);
    }
}
