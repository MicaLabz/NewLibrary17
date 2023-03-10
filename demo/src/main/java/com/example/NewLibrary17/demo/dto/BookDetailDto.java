package com.example.NewLibrary17.demo.dto;

import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Publisher;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailDto {

    @JsonProperty("bookId")
    private Integer bookId;

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

    @JsonProperty("softDelete")
    private Boolean softDelete;

    private Author author;

    private Publisher publisher;

}
