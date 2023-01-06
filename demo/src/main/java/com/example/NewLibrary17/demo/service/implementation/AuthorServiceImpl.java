package com.example.NewLibrary17.demo.service.implementation;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.AlreadyExistingThingException;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.exception.SoftDeleteException;
import com.example.NewLibrary17.demo.mapper.AuthorMapper;
import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.repository.AuthorRepository;
import com.example.NewLibrary17.demo.security.JWTUtil;
import com.example.NewLibrary17.demo.service.AuthorService;
import com.example.NewLibrary17.demo.service.ClientService;
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
public class AuthorServiceImpl implements AuthorService {

    private final JWTUtil jwtUtil;

    private final AuthorRepository authorRepository;

    private final ClientService clientService;

    private final AuthorMapper authorMapper;

    @Override
    public ResponseEntity<AuthorRegisteredDto> createAuthor(String token, AuthorRequestDto authorRequestDto) throws ForbiddenAccessException {
        String username = jwtUtil.extractClaimUsername(token.substring(7));
        Client client = clientService.loadUserByUsername(username);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        }else {
            var existingAuthor = getAuthorByName1(authorRequestDto.getName());
            if ( existingAuthor != null ) {
                if (existingAuthor.getSoftDelete() == Boolean.TRUE) {
                    existingAuthor.setSoftDelete(Boolean.FALSE);
                    reactivateAuthor(existingAuthor);
                    System.out.println("here");
                    return ResponseEntity.status( HttpStatus.OK ).body(authorMapper.convertToRegisteredDto(existingAuthor, token) );
                }else if (getAuthorByName(authorRequestDto.getName()).getName().equals(authorRequestDto.getName())) {
                    throw new AlreadyExistingThingException("Author with that name already exist");
                }
            }else{
                Author author = new Author();
                author.setName(authorRequestDto.getName());
                author.setSoftDelete(false);
                return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.convertToRegisteredDto(authorRepository.save(author),token));
            }
        }

        return null;
    }

    @Override
    public AuthorUpdateDto updateAuthor(Integer id, AuthorUpdateDto authorUpdateDto, String token) throws ForbiddenAccessException {
        String username = jwtUtil.extractClaimUsername(token.substring(7));
        Client client = clientService.loadUserByUsername(username);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        } else {
            var existingAuthor = getAuthorByName1(authorUpdateDto.getName());
            var existingAuthor1 = getAuthorById(id);
            if (existingAuthor != null || existingAuthor1.getSoftDelete().equals(Boolean.TRUE)) {
                throw new SoftDeleteException("Cannot access the author");
            } else if (authorUpdateDto.getName().equals(getAuthorByName(authorUpdateDto.getName()).getName())) {
                throw new AlreadyExistingThingException("Author with that name already exist");
            } else {
                Author author = this.getAuthorById(id);
                author.setName(authorUpdateDto.getName());
                return authorMapper.convertToUpdateDto(authorRepository.save(author));
            }
        }
    }

    @Override
    public void deleteAuthor(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException {
        try {
            Client client = clientService.loadUserByUsername(jwtUtil.extractUserName(token.substring(7)));
            if (client.getRole().getName().name().equals("ADMIN")){
                authorRepository.deleteById(id);
            }else{
               throw new ForbiddenAccessException("Can only access if you are an Admin");
            }
        } catch ( EmptyResultDataAccessException exception ) {
            throw new ResourceNotFoundException( exception.getMessage() );
        }
    }

    @Override
    public void reactivateAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public AuthorPaginatedDto getAllAuthors(Integer page) {

            Pageable pageable = PageRequest.of(page, 10);
            Page<Author> authorPage = authorRepository.findAll(pageable);

            AuthorPaginatedDto authorPaginatedDto = new AuthorPaginatedDto();

            List<AuthorDto> authorDtoList = new ArrayList<AuthorDto>();

            for (Author a: authorPage) {
                authorDtoList.add(authorMapper.convertToDto(a));
            }

            authorPaginatedDto.setAuthorList(authorDtoList);

            String url = "http://localhost:8080/authors?page=";

            if (authorPage.hasPrevious())
                authorPaginatedDto.setPreviousUrl(url + (page - 1));
            else
                authorPaginatedDto.setPreviousUrl("");

            if (authorPage.hasNext())
                authorPaginatedDto.setNextUrl(url + (page + 1));
            else
                authorPaginatedDto.setNextUrl("");

            return authorPaginatedDto;

    }

    @Override
    public Author getAuthorById(Integer id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if(optionalAuthor.isEmpty()){
            throw new ResourceNotFoundException("The author with id: " + id + " was not found");
        }
        return optionalAuthor.get();
    }

    @Override
    public Author getAuthorByName(String name) {
        var author = authorRepository.getAuthorByName(name);
        if(author.isPresent()){
            return author.get();
        }else{
            throw new ResourceNotFoundException("The author with name: " + name + " was not found");
        }
    }

    public Author getAuthorByName1(String name) {
        var author = authorRepository.getAuthorByName(name);
        if(author.isPresent()){
            return author.get();
        }else{
           return null;
        }
    }

    @Override
    public AuthorDetailDto getAuthorDetailById(Integer Id) {
        var author = authorRepository.findById(Id);
        if(author.isPresent()){
            return authorMapper.convertToDetailDto(author.get());
        }else{
            throw new ResourceNotFoundException("Author does not exist");
        }
    }

}
