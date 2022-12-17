package com.example.NewLibrary17.demo.service.implementation;

import com.example.NewLibrary17.demo.dto.ClientRegisteredDto;
import com.example.NewLibrary17.demo.dto.ClientRequestDto;
import com.example.NewLibrary17.demo.exception.AlreadyExistingClientException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.mapper.ClientMapper;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.security.AuthenticationRequest;
import com.example.NewLibrary17.demo.security.AuthenticationResponse;
import com.example.NewLibrary17.demo.security.JWTUtil;
import com.example.NewLibrary17.demo.service.AuthenticationService;
import com.example.NewLibrary17.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ResponseEntity<ClientRegisteredDto> registerClient(ClientRequestDto client) {
        String token;

        //Check One or more fields are empty
        if ( client.getName() == null || client.getLastName() == null || client.getEmail() == null || client.getPassword() == null )
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( null );

        var existingClient = (Client) clientService.loadUserByUsername( client.getEmail() );
        //Check User already registered
        if ( existingClient != null ) {
            if ( existingClient.getSoftDelete() == Boolean.TRUE) {
                existingClient.setSoftDelete( Boolean.FALSE );
                //clientService.reactivateAccount(existingUser);
                token = jwtUtil.generateToken(existingClient);
                return ResponseEntity.status( HttpStatus.OK ).body(clientMapper.convertToRegisteredDto(existingClient,token) );
            }else {
                throw new AlreadyExistingClientException("Already exist a Client with that E-mail");
            }
        }

        Client clientSaved = clientService.createClient(client);
        token = jwtUtil.generateToken(clientSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.convertToRegisteredDto(clientSaved,token));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> loginClient(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        //If one field is empty
        if ( username == null || password == null )
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( null );

        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( username, password ) );

        Client client = null;
        final UserDetails userDetails = client= clientService.loadUserByUsername(authenticationRequest.getEmail());

        if(client.getSoftDelete())
            throw new ResourceNotFoundException("Client does not exist");

        if ( userDetails == null )
            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( null );

        final String jwt = jwtUtil.generateToken( userDetails );

        return ResponseEntity.status( HttpStatus.OK ).body( new AuthenticationResponse( jwt ) );
    }
}
