package com.example.NewLibrary17.demo.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import static java.lang.Boolean.FALSE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKS")
@SQLDelete( sql = "UPDATE books SET soft_delete = true WHERE BOOK_ID=?" )
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", nullable = false)
    private Integer bookId;

    @Column(nullable = false, name = "ISBN")
    private String isbn;

    @Column(nullable = false, name = "TITLE")
    private String title;

    @Column(nullable = false, name = "YEAR")
    private Integer year;

    @Column(nullable = false, name = "COPIES")
    private Integer copies;

    @Column(nullable = false, name = "LOANED_COPIES")
    private Integer loanedCopies;

    @Column(nullable = false, name = "REMAINING_COPIES")
    private Integer remainingCopies;

    @Column( name = "SOFT_DELETE" )
    private Boolean softDelete = FALSE;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "AUTHOR_ID")
    private Author author;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "PUBLISHER_ID")
    private Publisher publisher ;

}
