package com.example.NewLibrary17.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookPaginatedDto {

    private List<BookDto> bookList;
    private String nextUrl;
    private String previousUrl;
}
