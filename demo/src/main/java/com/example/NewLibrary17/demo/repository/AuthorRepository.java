package com.example.NewLibrary17.demo.repository;

import com.example.NewLibrary17.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT a FROM Author a WHERE a.name = :name")
    public Optional<Author> getAuthorByName(@Param("name") String name);

}
