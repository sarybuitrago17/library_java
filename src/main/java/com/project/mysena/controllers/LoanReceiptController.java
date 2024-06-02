package com.project.mysena.controllers;

import com.project.mysena.models.Book;
import com.project.mysena.models.LoanReceipt;
import com.project.mysena.repositories.LoanReceiptRepository;
import com.project.mysena.repositories.LibroRepository;
import com.project.mysena.utils.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/loanreceipts")
public class LoanReceiptController {

    private static final Logger LOGGER = Logger.getLogger(LoanReceiptController.class.getName());

    @Autowired
    private LoanReceiptRepository loanReceiptRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<LoanReceipt> getAllLoanReceipts() {
        return loanReceiptRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanReceipt> getLoanReceiptById(@PathVariable Long id) {
        LoanReceipt loanReceipt = loanReceiptRepository.findById(id).orElse(null);
        if (loanReceipt != null) {
            return new ResponseEntity<>(loanReceipt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<LoanReceipt> createLoanReceipt(@RequestBody Map<String, List<Long>> request) {
        List<Long> bookIds = request.get("bookIds");
        LOGGER.info("Book IDs received: " + bookIds);

        List<Book> books = libroRepository.findAllById(bookIds);
        LOGGER.info("Books retrieved: " + books);

        // Crear el recibo de préstamo con la fecha de préstamo actual y una fecha de devolución una semana después
        LoanReceipt newLoanReceipt = new LoanReceipt(new Date(), new Date(System.currentTimeMillis() + 604800000L), books);

        // Guardar el recibo de préstamo junto con las relaciones de los libros
        loanReceiptRepository.save(newLoanReceipt);
        LOGGER.info("Loan receipt created: " + newLoanReceipt);

        return new ResponseEntity<>(newLoanReceipt, HttpStatus.CREATED);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<ByteArrayInputStream> generateLoanReceiptPdf(@PathVariable Long id) {
        LoanReceipt loanReceipt = loanReceiptRepository.findById(id).orElse(null);
        if (loanReceipt != null) {
            ByteArrayInputStream bis = pdfGenerator.generateLoanReceiptPdf(loanReceipt);
            return ResponseEntity.ok().body(bis);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
