/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.mysena.controllers;

import com.project.mysena.models.Invoice;
import com.project.mysena.models.LoanReceipt;
import com.project.mysena.repositories.InvoiceRepository;
import com.project.mysena.repositories.LoanReceiptRepository;
import com.project.mysena.utils.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private LoanReceiptRepository loanReceiptRepository;

    @GetMapping("/invoice/{id}")
    public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable Long id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isPresent()) {
            Invoice invoice = invoiceOptional.get();
            ByteArrayInputStream bis = pdfGenerator.generateInvoicePdf(invoice);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "invoice.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(bis.readAllBytes(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/loanReceipt/{id}")
    public ResponseEntity<byte[]> generateLoanReceiptPdf(@PathVariable Long id) {
        Optional<LoanReceipt> loanReceiptOptional = loanReceiptRepository.findById(id);
        if (loanReceiptOptional.isPresent()) {
            LoanReceipt loanReceipt = loanReceiptOptional.get();
            ByteArrayInputStream bis = pdfGenerator.generateLoanReceiptPdf(loanReceipt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "loanReceipt.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(bis.readAllBytes(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
