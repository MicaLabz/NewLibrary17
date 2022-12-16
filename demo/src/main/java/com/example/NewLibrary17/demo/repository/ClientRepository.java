package com.example.NewLibrary17.demo.repository;

import com.example.NewLibrary17.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

    @Query("select c from Client c where c.email = ?1")
    Client findByEmail(String email);
}
