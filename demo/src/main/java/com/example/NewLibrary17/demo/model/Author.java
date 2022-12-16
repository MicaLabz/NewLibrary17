package com.example.NewLibrary17.demo.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import static javax.persistence.GenerationType.IDENTITY;
import static java.lang.Boolean.FALSE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete( sql = "UPDATE authors SET soft_delete = true WHERE AUTHOR_ID=?" )
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
