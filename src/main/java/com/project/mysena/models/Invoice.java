package com.project.mysena.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double total;

    @ManyToMany
    @JoinTable(
        name = "invoice_books",
        joinColumns = @JoinColumn(name = "invoice_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public Invoice() {
    }

    public Invoice(Date date, double total, List<Book> books) {
        this.date = date;
        this.total = total;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
