package com.project.mysena.repositories;

import com.project.mysena.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Book, Long> {
}
