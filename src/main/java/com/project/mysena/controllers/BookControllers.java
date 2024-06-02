package com.project.mysena.controllers;

import com.project.mysena.models.Book;
import com.project.mysena.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class BookControllers  {
 @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Book> getAllLibros() {
        return libroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getLibroById(@PathVariable Long id) {
        Book libro = libroRepository.findById(id).orElse(null);
        if (libro != null) {
            return new ResponseEntity<>(libro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Book> createLibro(@RequestBody Book libro) {
        Book nuevoLibro = libroRepository.save(libro);
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateLibro(@PathVariable Long id, @RequestBody Book libroDetails) {
        Book libro = libroRepository.findById(id).orElse(null);
        if (libro != null) {
            libro.setTitle(libroDetails.getTitle());
            libro.setAuthor(libroDetails.getAuthor());
            libro.setIsbn(libroDetails.getIsbn());
            libro.setPrice(libroDetails.getPrice());
            libroRepository.save(libro);
            return new ResponseEntity<>(libro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        Book libro = libroRepository.findById(id).orElse(null);
        if (libro != null) {
            libroRepository.delete(libro);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
