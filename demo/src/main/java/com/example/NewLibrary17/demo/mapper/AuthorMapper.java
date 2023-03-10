package com.example.NewLibrary17.demo.mapper;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.model.Author;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

    private final ModelMapper mapper;

    public AuthorDto convertToDto(Author author ) {
        //return mapper.map( user, UserDto.class );
        return new AuthorDto(author.getAuthorId(),author.getName());
    }

    public Author convertToEntity( AuthorDto authorDto ) {
        return mapper.map( authorDto, Author.class );
    }

    public Author convertRequestDtoToEntity(AuthorRequestDto authorRequestDto){
        Author author = new Author();
        author.setName(authorRequestDto.getName());

        return author;
    }

    public AuthorRegisteredDto convertToRegisteredDto(Author author, String token){
        return new AuthorRegisteredDto(author.getAuthorId(),author.getName(),author.getSoftDelete());
    }

    public AuthorDetailDto convertToDetailDto(Author author ) {
        return mapper.map( author, AuthorDetailDto.class );
    }

    public AuthorUpdateDto convertToUpdateDto(Author author){
        return mapper.map( author, AuthorUpdateDto.class );}

    public Author convertUpdateDtoToEntity(AuthorUpdateDto authorUpdateDto){
        Author author = new Author();
        author.setName(authorUpdateDto.getName());

        return author;
    }
}
