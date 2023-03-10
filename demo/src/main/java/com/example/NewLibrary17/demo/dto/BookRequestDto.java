package com.example.NewLibrary17.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("title")
    private String title;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("copies")
    private Integer copies;

    @JsonProperty("loanedCopies")
    private Integer loanedCopies;
    @JsonProperty("remainingCopies")
    private Integer remainingCopies;

    @JsonProperty( "authorName" )
    private String authorName;

    @JsonProperty( "publisherName" )
    private String publisherName;
}
