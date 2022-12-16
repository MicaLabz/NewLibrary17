package com.example.NewLibrary17.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDetailDto {

    @JsonProperty("authorId")
    private Integer authorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("softDelete")
    private Boolean softDelete;
}
