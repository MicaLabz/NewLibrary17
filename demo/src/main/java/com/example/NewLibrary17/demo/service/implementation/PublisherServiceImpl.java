package com.example.NewLibrary17.demo.service.implementation;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.AlreadyExistingThingException;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.exception.SoftDeleteException;
import com.example.NewLibrary17.demo.mapper.PublisherMapper;
import com.example.NewLibrary17.demo.model.Publisher;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.repository.PublisherRepository;
import com.example.NewLibrary17.demo.security.JWTUtil;
import com.example.NewLibrary17.demo.service.ClientService;
import com.example.NewLibrary17.demo.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final JWTUtil jwtUtil;

    private final PublisherRepository publisherRepository;

    private final ClientService clientService;

    private final PublisherMapper publisherMapper;

    @Override
    public ResponseEntity<PublisherRegisteredDto> createPublisher(String token, PublisherRequestDto publisherRequestDto) throws ForbiddenAccessException {
        String username = jwtUtil.extractClaimUsername(token.substring(7));
        Client client = clientService.loadUserByUsername(username);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        }else {
            var existingPublisher = getPublisherByName1(publisherRequestDto.getName());
            if ( existingPublisher != null ) {
                if (existingPublisher.getSoftDelete() == Boolean.TRUE) {
                    existingPublisher.setSoftDelete(Boolean.FALSE);
                    reactivatePublisher(existingPublisher);
                    return ResponseEntity.status( HttpStatus.OK ).body(publisherMapper.convertToRegisteredDto(existingPublisher, token) );
                }else if (getPublisherByName(publisherRequestDto.getName()).getName().equals(publisherRequestDto.getName())) {
                    throw new AlreadyExistingThingException("Publisher with that name already exist");
                }
            }else{
                Publisher publisher = new Publisher();
                publisher.setName(publisherRequestDto.getName());
                publisher.setSoftDelete(false);
                return ResponseEntity.status(HttpStatus.CREATED).body(publisherMapper.convertToRegisteredDto(publisherRepository.save(publisher),token));
            }
        }

        return null;
    }

    @Override
    public PublisherUpdateDto updatePublisher(Integer id, PublisherUpdateDto publisherUpdateDto, String token) throws ForbiddenAccessException {
        String username = jwtUtil.extractClaimUsername(token.substring(7));
        Client client = clientService.loadUserByUsername(username);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        } else {
            var existingPublisher = getPublisherByName1(publisherUpdateDto.getName());
            var existingPublisher1 = getPublisherById(id);
            if (existingPublisher != null || existingPublisher1.getSoftDelete().equals(Boolean.TRUE)) {
                throw new SoftDeleteException("Cannot access the publisher");
            } else if (publisherUpdateDto.getName().equals(getPublisherByName(publisherUpdateDto.getName()).getName())) {
                throw new AlreadyExistingThingException("Publisher with that name already exist");
            } else {
                Publisher publisher = this.getPublisherById(id);
                publisher.setName(publisherUpdateDto.getName());
                return publisherMapper.convertToUpdateDto(publisherRepository.save(publisher));
            }
        }
    }

    @Override
    public void deletePublisher(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException {
        try {
            Client client = clientService.loadUserByUsername(jwtUtil.extractUserName(token.substring(7)));
            if (client.getRole().getName().name().equals("ADMIN")){
                publisherRepository.deleteById(id);
            }else{
                throw new ForbiddenAccessException("Can only access if you are an Admin");
            }
        } catch ( EmptyResultDataAccessException exception ) {
            throw new ResourceNotFoundException( exception.getMessage() );
        }
    }

    @Override
    public void reactivatePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    @Override
    public PublisherPaginatedDto getAllPublisher(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Publisher> publisherPage = publisherRepository.findAll(pageable);

        PublisherPaginatedDto publisherPaginatedDto = new PublisherPaginatedDto();

        List<PublisherDto> publisherDtoList = new ArrayList<PublisherDto>();

        for (Publisher a: publisherPage) {
            publisherDtoList.add(publisherMapper.convertToDto(a));
        }

        publisherPaginatedDto.setPublisherList(publisherDtoList);

        String url = "http://localhost:8080/publishers?page=";

        if (publisherPage.hasPrevious())
            publisherPaginatedDto.setPreviousUrl(url + (page - 1));
        else
            publisherPaginatedDto.setPreviousUrl("");

        if (publisherPage.hasNext())
            publisherPaginatedDto.setNextUrl(url + (page + 1));
        else
            publisherPaginatedDto.setNextUrl("");

        return publisherPaginatedDto;
    }

    @Override
    public Publisher getPublisherById(Integer id) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if(optionalPublisher.isEmpty()){
            throw new ResourceNotFoundException("The publisher with id: " + id + " was not found");
        }
        return optionalPublisher.get();
    }

    @Override
    public Publisher getPublisherByName(String name) {
        var publisher = publisherRepository.getPublisherByName(name);
        if(publisher.isPresent()){
            return publisher.get();
        }else{
            throw new ResourceNotFoundException("The publisher with name: " + name + " was not found");
        }
    }

    public Publisher getPublisherByName1(String name) {
        var publisher = publisherRepository.getPublisherByName(name);
        if(publisher.isPresent()){
            return publisher.get();
        }else{
            return null;
        }
    }

    @Override
    public PublisherDetailDto getPublisherDetailById(Integer Id) {
        var publisher = publisherRepository.findById(Id);
        if(publisher.isPresent()){
            return publisherMapper.convertToDetailDto(publisher.get());
        }else{
            throw new ResourceNotFoundException("Publisher does not exist");
        }
    }
}
