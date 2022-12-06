package com.example.NewLibrary17.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static java.lang.Boolean.FALSE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOANS")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_ID", nullable = false)
    private Integer loanId;

    private Timestamp loanDate;

    private Timestamp returnDate;

    @Column(nullable = false, name = "SOFT_DELETE")
    private Boolean softDelete = FALSE;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
