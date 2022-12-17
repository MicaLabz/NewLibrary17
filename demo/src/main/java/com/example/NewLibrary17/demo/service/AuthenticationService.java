package com.example.NewLibrary17.demo.service;

import com.example.NewLibrary17.demo.dto.ClientRegisteredDto;
import com.example.NewLibrary17.demo.dto.ClientRequestDto;
import com.example.NewLibrary17.demo.security.AuthenticationRequest;
import com.example.NewLibrary17.demo.security.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<ClientRegisteredDto> registerClient(ClientRequestDto client);
    ResponseEntity<AuthenticationResponse> loginClient(AuthenticationRequest authenticationRequest);
}
