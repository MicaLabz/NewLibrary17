package com.example.NewLibrary17.demo.mapper;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherMapper {

    private final ModelMapper mapper;

    public PublisherDto convertToDto(Publisher publisher ) {
        //return mapper.map( user, UserDto.class );
        return new PublisherDto(publisher.getPublisherId(),publisher.getName());
    }

    public Publisher convertToEntity( PublisherDto publisherDto ) {
        return mapper.map( publisherDto, Publisher.class );
    }

    public Publisher convertRequestDtoToEntity(PublisherRequestDto publisherRequestDto){
        Publisher publisher = new Publisher();
        publisher.setName(publisherRequestDto.getName());

        return publisher;
    }

    public PublisherRegisteredDto convertToRegisteredDto(Publisher publisher, String token){
        return new PublisherRegisteredDto(publisher.getPublisherId(),publisher.getName(),publisher.getSoftDelete());
    }

    public PublisherDetailDto convertToDetailDto(Publisher publisher ) {
        return mapper.map(publisher, PublisherDetailDto.class );
    }

    public PublisherUpdateDto convertToUpdateDto(Publisher publisher){
        return mapper.map(publisher, PublisherUpdateDto.class );}

    public Publisher convertUpdateDtoToEntity(PublisherUpdateDto publisherUpdateDto){
        Publisher publisher= new Publisher();
        publisher.setName(publisherUpdateDto.getName());
        return publisher;
    }
}
