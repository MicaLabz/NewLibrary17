package com.example.NewLibrary17.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.lang.Boolean.FALSE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTHORS")

public class Author {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "AUTHOR_ID", nullable = false)
    private Integer authorId;

    @Column(unique = true , name = "NAME")
    private String name;

    @Column( name = "SOFT_DELETE" )
    private Boolean softDelete = FALSE;

}
