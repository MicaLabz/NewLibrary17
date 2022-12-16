package com.example.NewLibrary17.demo.service.implementation;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.mapper.ClientMapper;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.model.Role;
import com.example.NewLibrary17.demo.model.RoleName;
import com.example.NewLibrary17.demo.repository.ClientRepository;
import com.example.NewLibrary17.demo.repository.RoleRepository;
import com.example.NewLibrary17.demo.security.JWTUtil;
import com.example.NewLibrary17.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final JWTUtil jwtUtil;

    private final ClientMapper clientMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ClientPaginatedDto getAllClients(Integer page, String token) throws ForbiddenAccessException {
        String jwt = token.substring(7);
        String email = jwtUtil.extractUserName(jwt);
        Client client = loadUserByUsername(email);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        }else {

            Pageable pageable = PageRequest.of(page, 10);
            Page<Client> clientPage = clientRepository.findAll(pageable);

            ClientPaginatedDto clientPaginatedDto = new ClientPaginatedDto();

            List<ClientDto> clientDtoList = new ArrayList<ClientDto>();

            for (Client u : clientPage) {
                clientDtoList.add(clientMapper.convertToDto(u));
            }

            clientPaginatedDto.setClientList(clientDtoList);

            String url = "http://localhost:8080/clients?page=";

            if (clientPage.hasPrevious())
                clientPaginatedDto.setPreviousUrl(url + (page - 1));
            else
                clientPaginatedDto.setPreviousUrl("");

            if (clientPage.hasNext())
                clientPaginatedDto.setNextUrl(url + (page + 1));
            else
                clientPaginatedDto.setNextUrl("");

            return clientPaginatedDto;
        }
    }

    @Override
    public Client createClient(ClientRequestDto clientRequestDto) {
        Client client = clientMapper.convertRequestDtoToEntity(clientRequestDto);

        Role role;
        role = roleRepository.findById(1).orElse(new Role(1, RoleName.USER, "Clients rol", new Timestamp( System.currentTimeMillis() ), null ));

        client.setRole(role);

        client.setCreationDate( new Timestamp( System.currentTimeMillis() ) );
        client.setSoftDelete( false );

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passEncoded = passwordEncoder.encode( client.getPassword() );
        client.setPassword( passEncoded );

        return clientRepository.save(client);
    }

    @Override
    public ClientDetailDto getClientDetailById(Integer Id) {
        var client = clientRepository.findById(Id);
        if(client.isPresent()){
            return clientMapper.convertToDetailDto(client.get());
        }else{
            throw new ResourceNotFoundException("Client does not exist");
        }
    }

    @Override
    public Client matchClientToToken(Integer id, String token) throws ForbiddenAccessException {
        String jwt = token.substring(7);
        String email = jwtUtil.extractUserName(jwt);
        Client client = loadUserByUsername(email);
        if(client.getClientId().equals(id)){
            return client;
        }else{
            throw new ForbiddenAccessException("Cannot access another client details");
        }
    }

    @Override
    public Client loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return clientRepository.findByEmail( email );
        } catch ( UsernameNotFoundException exception ) {
            System.out.println("hola");
            throw exception;
        }
    }

    @Override
    public Client getClientById(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        optionalClient.get().getRole().getName().name();
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("The client with id: " + id + " was not found");
        }
        return optionalClient.get();
    }

    @Override
    public ClientUpdateDto updateClient(Integer id, ClientUpdateDto clientUpdateDto, String token) throws ForbiddenAccessException {
        Client client = this.getClientById(id);
        Client clientToken = loadUserByUsername(jwtUtil.extractClaimUsername(token.substring(7)));
        if(client.getClientId()!=clientToken.getClientId()){

            throw new ForbiddenAccessException("You are trying to modify a client that is not you");
        }
        client.setName(clientUpdateDto.getName());
        client.setLastName(clientUpdateDto.getLastName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passEncoded =passwordEncoder.encode(clientUpdateDto.getPassword());
        client.setPassword(passEncoded);
        Timestamp timestamp=new Timestamp(new Date().getTime());
        client.setUpdateDate(timestamp);
        clientRepository.save(client);
        return clientMapper.convertToUpdateDto(client);
    }

    @Override
    public void deleteClient(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException {
        try {
            Client client = loadUserByUsername(jwtUtil.extractUserName(token.substring(7)));

            if(client.getRole().getName().name()=="ADMIN")
                clientRepository.deleteById(id);
            else{
                if(client.getClientId()==id)
                    clientRepository.deleteById(id);
                else
                    throw new ForbiddenAccessException("You cannot delete another client");
            }

        } catch ( EmptyResultDataAccessException exception ) {
            throw new ResourceNotFoundException( exception.getMessage() );
        }

    }

    @Override
    public void reactivateClient(Client client) {
        clientRepository.save(client);
    }

}
