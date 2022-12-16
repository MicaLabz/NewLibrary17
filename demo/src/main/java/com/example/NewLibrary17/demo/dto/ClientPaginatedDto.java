package com.example.NewLibrary17.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientPaginatedDto {

    private List<ClientDto> clientList;
    private String nextUrl;
    private String previousUrl;
}
