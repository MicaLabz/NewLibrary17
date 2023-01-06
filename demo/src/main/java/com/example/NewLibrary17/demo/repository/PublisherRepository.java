package com.example.NewLibrary17.demo.repository;

import com.example.NewLibrary17.demo.model.Author;
import com.example.NewLibrary17.demo.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Integer> {

    @Query("SELECT p FROM Publisher p WHERE p.name = :name")
    public Optional<Publisher> getPublisherByName(@Param("name") String name);
}
