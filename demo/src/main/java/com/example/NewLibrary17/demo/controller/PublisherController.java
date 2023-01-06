package com.example.NewLibrary17.demo.controller;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.service.AuthorService;
import com.example.NewLibrary17.demo.service.PublisherService;
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
@RequestMapping("/publishers")
@Slf4j
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER_ROLE')")
    @GetMapping
    public PublisherPaginatedDto getAll(@Param("page") Integer page) {
        return publisherService.getAllPublisher(page);
    }

    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/register")
    public ResponseEntity<PublisherRegisteredDto> registerPublisher(@Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token , @RequestBody PublisherRequestDto publisher) throws ForbiddenAccessException {
        return publisherService.createPublisher(token, publisher);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<PublisherUpdateDto> updatePublisher(@Parameter(description = "id publisher to be updated") @PathVariable("id") Integer id, @RequestBody PublisherUpdateDto publisherUpdateDto, @Parameter(description = "authentication token") @RequestHeader("Authorization") String token) throws ForbiddenAccessException {
        return ResponseEntity.ok(publisherService.updatePublisher(id,publisherUpdateDto,token));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseStatus( OK )
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @DeleteMapping( "/{id}" )
    public void deleteById( @Parameter(description = "id publisher to be deleted") @PathVariable Integer id, @Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token ) throws ResourceNotFoundException, ForbiddenAccessException {
        publisherService.deletePublisher(id, token);
    }
}