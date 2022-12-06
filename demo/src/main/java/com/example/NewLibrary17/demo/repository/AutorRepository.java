package com.example.NewLibrary17.demo.repository;

import com.example.NewLibrary17.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Author, Integer> {

}
