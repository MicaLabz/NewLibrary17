package com.example.NewLibrary17.demo.controller;

import com.example.NewLibrary17.demo.dto.ClientRegisteredDto;
import com.example.NewLibrary17.demo.dto.ClientRequestDto;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.security.AuthenticationRequest;
import com.example.NewLibrary17.demo.security.AuthenticationResponse;
import com.example.NewLibrary17.demo.service.implementation.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<ClientRegisteredDto> registerClient(@RequestBody ClientRequestDto client){
        return authenticationServiceImpl.registerClient(client);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginClient(@RequestBody AuthenticationRequest authenticationRequest){
        return authenticationServiceImpl.loginClient(authenticationRequest);
    }
}
