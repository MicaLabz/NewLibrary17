package com.example.NewLibrary17.demo.mapper;

import com.example.NewLibrary17.demo.dto.*;
import com.example.NewLibrary17.demo.model.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper mapper;

    public BookDto convertToDto(Book book ) {
        //return mapper.map( user, UserDto.class );
        return new BookDto(book.getBookId(),book.getIsbn(),book.getTitle(),book.getYear(),book.getCopies(),book.getLoanedCopies(), book.getRemainingCopies(), book.getAuthor(),book.getPublisher());
    }

    public Book convertToEntity( BookDto bookDto ) {
        return mapper.map( bookDto, Book.class );
    }

    public Book convertRequestDtoToEntity(BookRequestDto bookRequestDto){
        Book book = new Book();
        book.setIsbn(bookRequestDto.getIsbn());
        book.setTitle(bookRequestDto.getTitle());
        book.setYear(bookRequestDto.getYear());
        book.setCopies(bookRequestDto.getCopies());
        return book;
    }

    public BookRegisteredDto convertToRegisteredDto(Book book, String token){
        return new BookRegisteredDto(book.getBookId(),book.getIsbn(),book.getTitle(),book.getYear(),book.getCopies(),book.getLoanedCopies(), book.getRemainingCopies(), book.getSoftDelete(), book.getAuthor(), book.getPublisher());
    }

    public BookDetailDto convertToDetailDto(Book book ) {
        return mapper.map( book, BookDetailDto.class );
    }

    public BookUpdateDto convertToUpdateDto(Book book){
        return mapper.map( book, BookUpdateDto.class );}
}
