package com.example.NewLibrary17.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorPaginatedDto {

    private List<AuthorDto> authorList;
    private String nextUrl;
    private String previousUrl;
}
