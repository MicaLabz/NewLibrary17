package com.example.NewLibrary17.demo.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import static java.lang.Boolean.FALSE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PUBLISHERS")
@SQLDelete( sql = "UPDATE publishers SET soft_delete = true WHERE PUBLISHER_ID=?" )
@Data
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUBLISHER_ID", nullable = false)
    private Integer publisherId;

    @Column(unique = true, name = "NAME")
    private String name;

    @Column(name = "SOFT_DELETE")
    private Boolean softDelete = FALSE;
}
