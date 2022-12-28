package com.example.NewLibrary17.demo.controller;

import com.example.NewLibrary17.demo.dto.ClientDetailDto;
import com.example.NewLibrary17.demo.dto.ClientPaginatedDto;
import com.example.NewLibrary17.demo.dto.ClientUpdateDto;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.service.ClientService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping( "/clients" )
public class ClientController {

    private final ClientService clientService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @GetMapping
    ClientPaginatedDto getAll(@Param("page") Integer page, @Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token) throws ForbiddenAccessException {
        return clientService.getAllClients(page, token);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping( value = "/{id}")
    @PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<ClientDetailDto> getClientDetailById(@Parameter(description = "id client")@PathVariable("id") Integer id, @Parameter(description = "authentication token") @RequestHeader("Authorization") String token ) throws ForbiddenAccessException {
        Client client = clientService.matchClientToToken(id,token);
        return ResponseEntity.ok(clientService.getClientDetailById(client.getClientId()));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<ClientUpdateDto> updateClient(@Parameter(description = "id client to be updated")@PathVariable("id") Integer id, @RequestBody ClientUpdateDto clientUpdateDto, @Parameter(description = "authentication token") @RequestHeader("Authorization") String token) throws ForbiddenAccessException {
        return ResponseEntity.ok(clientService.updateClient(id,clientUpdateDto,token));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseStatus( OK )
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @DeleteMapping( "/{id}" )
    public void deleteById( @Parameter(description = "id client to be deleted") @PathVariable Integer id, @Parameter(description = "authentication token") @RequestHeader( "Authorization" ) String token ) throws ResourceNotFoundException, ForbiddenAccessException {
        clientService.deleteClient( id ,token);
    }
}