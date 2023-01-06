package com.example.NewLibrary17.demo.dto;

import com.example.NewLibrary17.demo.model.Publisher;
import lombok.Data;

import java.util.List;

@Data
public class PublisherPaginatedDto {

    private List<PublisherDto> publisherList;
    private String nextUrl;
    private String previousUrl;
}
