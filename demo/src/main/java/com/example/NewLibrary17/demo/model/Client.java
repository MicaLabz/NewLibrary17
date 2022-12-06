package com.example.NewLibrary17.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Boolean.FALSE;

@Entity
@NoArgsConstructor
@Data
@Table(name = "CLIENTS")
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID", nullable = false)
    private Integer clientId;

    @Column(nullable = false, name = "DNI")
    private Long dni;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(nullable = false, name = "LAST_NAME")
    private String lastName;

    @Column(nullable = false, name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(nullable = false, name = "SOFT_DELETE")
    private Boolean softDelete = FALSE;

}
