package com.example.NewLibrary17.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Entity
@Getter
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME", nullable = false)
    private RoleName name;

    @Column(name = "DESCRIPTION")
    private String description;

    private Timestamp creationDate;
    private Timestamp updateDate;

    public Role(RoleName name, String description, Timestamp creationDate, Timestamp updateDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Role(Integer id,RoleName name, String description, Timestamp creationDate, Timestamp updateDate) {
        this.roleId = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Role() {
    }
}
