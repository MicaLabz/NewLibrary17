package com.example.NewLibrary17.demo.service.implementation;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.exception.AlreadyExistingThingException;
import com.example.NewLibrary17.demo.exception.ForbiddenAccessException;
import com.example.NewLibrary17.demo.exception.ResourceNotFoundException;
import com.example.NewLibrary17.demo.exception.SoftDeleteException;
import com.example.NewLibrary17.demo.mapper.AuthorMapper;
import com.example.NewLibrary17.demo.mapper.BookMapper;
import com.example.NewLibrary17.demo.mapper.PublisherMapper;
import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Book;
import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.model.Publisher;
import com.example.NewLibrary17.demo.repository.BookRepository;
import com.example.NewLibrary17.demo.security.JWTUtil;
import com.example.NewLibrary17.demo.service.AuthorService;
import com.example.NewLibrary17.demo.service.BookService;
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
public class BookServiceImpl implements BookService {

    private final JWTUtil jwtUtil;

    private final ClientService clientService;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final AuthorService authorService;

    private final PublisherService publisherService;

    private final AuthorMapper authorMapper;

    private final PublisherMapper publisherMapper;

    @Override
    public ResponseEntity<BookRegisteredDto> createBook(String token, BookRequestDto bookRequestDto) throws ForbiddenAccessException {
        String username = jwtUtil.extractClaimUsername(token.substring(7));
        Client client = clientService.loadUserByUsername(username);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        } else {
            var existingBook = getBookByTitle1(bookRequestDto.getTitle());
            if (existingBook != null) {
                if (existingBook.getSoftDelete() == Boolean.TRUE) {
                    existingBook.setSoftDelete(Boolean.FALSE);
                    reactivateBook(existingBook);
                    return ResponseEntity.status(HttpStatus.OK).body(bookMapper.convertToRegisteredDto(existingBook, token));
                } else if (getBookByTitle(bookRequestDto.getTitle()).getTitle().equals(bookRequestDto.getTitle())) {
                    throw new AlreadyExistingThingException("Book with that title already exist");
                }
            } else {
                Book book = new Book();
                book.setIsbn(bookRequestDto.getIsbn());
                book.setTitle(bookRequestDto.getTitle());
                book.setYear(bookRequestDto.getYear());
                book.setCopies(bookRequestDto.getCopies());
                book.setLoanedCopies(0);
                book.setRemainingCopies(bookRequestDto.getCopies());
                book.setAuthor(authorService.getAuthorByName(bookRequestDto.getAuthorName()));
                book.setPublisher(publisherService.getPublisherByName(bookRequestDto.getPublisherName()));
                book.setSoftDelete(false);
                return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToRegisteredDto(bookRepository.save(book), token));
            }
        }

        return null;
    }

    //TODO: VER COMO RESOLVER CUANDO QUIERO CAMBIAR EL TITULO DE UN LIBRO QUE NO ES EL MISMO TITULO
    @Override
    public BookUpdateDto updateBook(Integer id, BookUpdateDto bookUpdateDto, String token) throws ForbiddenAccessException {
        String username = jwtUtil.extractClaimUsername(token.substring(7));
        Client client = clientService.loadUserByUsername(username);
        if (client.getRole().getName().name().equals("USER")) {
            throw new ForbiddenAccessException("Can only access if you are an Admin");
        } else {
            var existingBook = getBookByTitle1(bookUpdateDto.getTitle());
            var existingBook1 = getBookById(id);
            if (existingBook != null || existingBook1.getSoftDelete().equals(Boolean.TRUE)) {
                throw new SoftDeleteException("Cannot access the book");
            } else if (bookUpdateDto.getTitle().equals(getBookByTitle1(bookUpdateDto.getTitle()).getTitle()))  {
                throw new AlreadyExistingThingException("Book with that title already exist");
            } else {
                Book book = this.getBookById(id);
                book.setIsbn(bookUpdateDto.getIsbn());
                book.setTitle(bookUpdateDto.getTitle());
                book.setYear(bookUpdateDto.getYear());
                book.setCopies(bookUpdateDto.getCopies());
                Author author = authorService.getAuthorByName(bookUpdateDto.getAuthorName());
                AuthorUpdateDto authorUpdateDto1 = new AuthorUpdateDto(author.getAuthorId(), author.getName());
                book.setAuthor(authorMapper.convertUpdateDtoToEntity(authorService.updateAuthor(author.getAuthorId(), authorUpdateDto1, token)));
                Publisher publisher = publisherService.getPublisherByName(bookUpdateDto.getPublisherName());
                PublisherUpdateDto publisherUpdateDto1 = new PublisherUpdateDto(publisher.getPublisherId(), publisher.getName());
                book.setPublisher(publisherMapper.convertUpdateDtoToEntity(publisherService.updatePublisher(publisher.getPublisherId(), publisherUpdateDto1, token)));
                return bookMapper.convertToUpdateDto(bookRepository.save(book));
            }
        }
    }

    @Override
    public void deleteBook(Integer id, String token) throws ResourceNotFoundException, ForbiddenAccessException {
        try {
            Client client = clientService.loadUserByUsername(jwtUtil.extractUserName(token.substring(7)));
            if (client.getRole().getName().name().equals("ADMIN")) {
                bookRepository.deleteById(id);
            } else {
                throw new ForbiddenAccessException("Can only access if you are an Admin");
            }
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("The book with id: " + id + " was not found");
        }
    }

    @Override
    public void reactivateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public BookPaginatedDto getAllBooks(Integer page) throws ForbiddenAccessException {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        BookPaginatedDto bookPaginatedDto = new BookPaginatedDto();

        List<BookDto> bookDtoList = new ArrayList<BookDto>();

        for (Book a: bookPage) {
            bookDtoList.add(bookMapper.convertToDto(a));
        }

        bookPaginatedDto.setBookList(bookDtoList);

        String url = "http://localhost:8080/books?page=";

        if (bookPage.hasPrevious())
            bookPaginatedDto.setPreviousUrl(url + (page - 1));
        else
            bookPaginatedDto.setPreviousUrl("");

        if (bookPage.hasNext())
            bookPaginatedDto.setNextUrl(url + (page + 1));
        else
            bookPaginatedDto.setNextUrl("");

        return bookPaginatedDto;
    }

    @Override
    public Book getBookById(Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            throw new ResourceNotFoundException("The book with id: " + id + " was not found");
        }
        return optionalBook.get();
    }

    @Override
    public BookDetailDto getBookDetailById(Integer Id) {
        var book = bookRepository.findById(Id);
        if(book.isPresent()){
            return bookMapper.convertToDetailDto(book.get());
        }else{
            throw new ResourceNotFoundException("Book does not exist");
        }
    }

    public Book getBookByTitle1(String title) {
        var book = bookRepository.getBookByTitle(title);
        if(book.isPresent()){
            return book.get();
        }else{
            return null;
        }
    }

    public Book getBookByTitle(String title) {
        var book = bookRepository.getBookByTitle(title);
        if(book.isPresent()){
            return book.get();
        }else{
            throw new ResourceNotFoundException("The book with title: " + title + " was not found");
        }
    }

    /*@Transactional
    public void eliminarLibro(String id) throws Exception{
        Prestamo prestamo = prestamoRepositorio.buscarPrestamoPorIdLibro(id);
        if(prestamo == null) {
            Libro libro  = libroRepositorio.getById(id);
            libroRepositorio.delete(libro);
        }else {
            String idPrestamo = prestamo.getId();
            prestamoServicio.eliminarPrestamo(idPrestamo);
            Libro libro = libroRepositorio.getById(id);
            libroRepositorio.delete(libro);
        }
    }*/
}
