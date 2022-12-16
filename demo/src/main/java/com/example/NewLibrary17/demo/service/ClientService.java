package com.example.NewLibrary17.demo.service;

import com.example.NewLibrary17.demo.dto.ClientDetailDto;
import com.example.NewLibrary17.demo.dto.ClientPaginatedDto;
import com.example.NewLibrary17.demo.dto.ClientRequestDto;
import com.example.NewLibrary17.demo.dto.ClientUpdateDto;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.model.Client;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ClientService extends UserDetailsService {

    ClientPaginatedDto getAllClients(Integer page, String token) throws ForbiddenAccessException;
    Client createClient(ClientRequestDto clientRequestDto);
    ClientDetailDto getClientDetailById(Integer Id);
    Client matchClientToToken(Integer id, String token) throws ForbiddenAccessException;
    Client loadUserByUsername(String email);
    Client getClientById(Integer id);
    ClientUpdateDto updateClient(Integer id, ClientUpdateDto clientUpdateDto, String token) throws ForbiddenAccessException;
    void deleteClient(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException;
    void reactivateClient(Client client);
}
