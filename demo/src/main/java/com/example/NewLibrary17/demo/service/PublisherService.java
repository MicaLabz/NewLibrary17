package com.example.NewLibrary17.demo.service;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Publisher;
import org.springframework.http.ResponseEntity;

public interface PublisherService {

    ResponseEntity<PublisherRegisteredDto> createPublisher(String token, PublisherRequestDto publisherRequestDto) throws ForbiddenAccessException;

    PublisherUpdateDto updatePublisher(Integer id, PublisherUpdateDto publisherUpdateDto, String token) throws ForbiddenAccessException;

    void deletePublisher(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException;

    void reactivatePublisher(Publisher publisher);

    PublisherPaginatedDto getAllPublisher(Integer page);

    Publisher getPublisherById(Integer id);

    Publisher getPublisherByName(String name);

    PublisherDetailDto getPublisherDetailById(Integer Id);
}
