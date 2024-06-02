package com.project.mysena.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "loan_receipts")
public class LoanReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date loanDate;
    
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @ManyToMany
    @JoinTable(
        name = "loan_receipt_books",
        joinColumns = @JoinColumn(name = "loan_receipt_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public LoanReceipt() {
    }

    public LoanReceipt(Date loanDate, Date returnDate, List<Book> books) {
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
