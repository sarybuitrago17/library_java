package com.project.mysena.controllers;

import com.project.mysena.models.Book;
import com.project.mysena.models.Invoice;
import com.project.mysena.repositories.InvoiceRepository;
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
@RequestMapping("/api/invoices")
public class InvoiceController {

    private static final Logger LOGGER = Logger.getLogger(InvoiceController.class.getName());

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Map<String, List<Long>> request) {
        List<Long> bookIds = request.get("bookIds");
        LOGGER.info("Book IDs received: " + bookIds);

        List<Book> books = libroRepository.findAllById(bookIds);
        LOGGER.info("Books retrieved: " + books);

        // Calcular el total de la factura sumando los precios de los libros
        double total = books.stream().mapToDouble(Book::getPrice).sum();
        LOGGER.info("Total calculated: " + total);

        // Crear la factura con la fecha actual, el total y los libros seleccionados
        Invoice newInvoice = new Invoice(new Date(), total, books);

        // Guardar la factura junto con las relaciones de los libros
        invoiceRepository.save(newInvoice);
        LOGGER.info("Invoice created: " + newInvoice);

        return new ResponseEntity<>(newInvoice, HttpStatus.CREATED);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<ByteArrayInputStream> generateInvoicePdf(@PathVariable Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            ByteArrayInputStream bis = pdfGenerator.generateInvoicePdf(invoice);
            return ResponseEntity.ok().body(bis);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
