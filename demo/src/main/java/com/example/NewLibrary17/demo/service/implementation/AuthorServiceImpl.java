package com.example.NewLibrary17.demo.service.implementation;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.AlreadyExistingAuthor;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.mapper.AuthorMapper;
import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.repository.AuthorRepository;
import com.example.NewLibrary17.demo.security.JWTUtil;
import com.example.NewLibrary17.demo.service.AuthorService;
import com.example.NewLibrary17.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final JWTUtil jwtUtil;

    private final AuthorRepository authorRepository;

    private final ClientService clientService;

    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto createAuthor(String token, AuthorRequestDto authorRequestDto) throws ForbiddenAccessException {
        String username = jwtUtil.extractClaimUsername(token.substring(7));
        Client client = clientService.loadUserByUsername(username);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        }else {
            if (authorRequestDto.getName().equals(getAuthorByName(authorRequestDto.getName()))) {
                throw new AlreadyExistingAuthor("Author with that name already exist");
            } else {
                Author author = new Author();
                author.setName(authorRequestDto.getName());
                author.setSoftDelete(false);
                return authorMapper.convertToDto(authorRepository.save(author));
            }
        }

    }

    @Override
    public AuthorUpdateDto updateAuthor(Integer id, AuthorUpdateDto authorUpdateDtoUpdateDto, String token) throws ForbiddenAccessException {
        return null;
    }

    @Override
    public void deleteAuthor(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException {

    }

    @Override
    public void reactivateAuthor(Author author) {

    }

    @Override
    public AuthorPaginatedDto getAllAuthors(Integer page, String token) throws ForbiddenAccessException {
        return null;
    }

    @Override
    public Author getAuthorById(Integer id) {
        return null;
    }

    @Override
    public Author getAuthorByName(String name) {
        return null;
    }

    @Override
    public AuthorDetailDto getAuthorDetailById(Integer Id) {
        return null;
    }
}
