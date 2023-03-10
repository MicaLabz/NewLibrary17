package com.example.NewLibrary17.demo.repository;

import com.example.NewLibrary17.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    public Optional<Book> getBookByTitle(@Param("title") String title);
}
