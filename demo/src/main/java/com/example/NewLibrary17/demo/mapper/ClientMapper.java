package com.example.NewLibrary17.demo.mapper;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.model.Client;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final ModelMapper mapper;

    public ClientDto convertToDto(Client client ) {
        //return mapper.map( user, UserDto.class );
        return new ClientDto(client.getName(), client.getLastName(), client.getEmail());
    }

    public Client convertToEntity( ClientDto clientDto ) {
        return mapper.map( clientDto, Client.class );
    }

    public Client convertRequestDtoToEntity(ClientRequestDto clientRequestDto){
        Client client = new Client();
        client.setName(clientRequestDto.getName());
        client.setLastName(clientRequestDto.getLastName());
        client.setEmail(clientRequestDto.getEmail());
        client.setPassword(clientRequestDto.getPassword());
        client.setPhoneNumber(clientRequestDto.getPhoneNumber());

        return client;
    }

    public ClientRegisteredDto convertToRegisteredDto(Client client, String token){
        return new ClientRegisteredDto(client.getName(),client.getName(), client.getEmail(),token);
    }

    public ClientDetailDto convertToDetailDto(Client client ) {
        return mapper.map( client, ClientDetailDto.class );
    }

    public ClientUpdateDto convertToUpdateDto(Client client){
        return mapper.map( client, ClientUpdateDto.class );}
    }
