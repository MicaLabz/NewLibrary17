package com.example.NewLibrary17.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    @JsonProperty( "name" ) String name;
    @JsonProperty( "lastName" ) String lastName;
    @JsonProperty( "email" ) String email;
}
