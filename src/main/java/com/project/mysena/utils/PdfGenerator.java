/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.project.mysena.utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.project.mysena.models.Invoice;
import com.project.mysena.models.LoanReceipt;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Component
public class PdfGenerator {

public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Factura de Compra de los libros"));
        document.add(new Paragraph("Fecha: " + invoice.getDate()));
        
        int totalBooks = invoice.getBooks().size(); // Obtener la cantidad total de libros
        
        document.add(new Paragraph("Books: "));
        invoice.getBooks().forEach(book -> {
            try {
                document.add(new Paragraph("Nombre del libro: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Precio: $" + book.getPrice()));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        
        document.add(new Paragraph("cantidad de libros: " + totalBooks)); // Agregar la cantidad total al documento
        document.add(new Paragraph("Total a pagar: $" + invoice.getTotal()));
        document.close();
    } catch (DocumentException e) {
        e.printStackTrace();
    }

    return new ByteArrayInputStream(out.toByteArray());
}

public ByteArrayInputStream generateLoanReceiptPdf(LoanReceipt loanReceipt) {
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Factura de prestamo de los libros"));
        document.add(new Paragraph("Loan Receipt ID: " + loanReceipt.getId()));
        document.add(new Paragraph("Fecha de adquiracion: " + loanReceipt.getLoanDate()));
        document.add(new Paragraph("Fecha de devolver: " + loanReceipt.getReturnDate()));
        document.add(new Paragraph("Libros: "));
        
        int totalBooks = loanReceipt.getBooks().size(); // Obtener la cantidad total de libros
        
        loanReceipt.getBooks().forEach(book -> {
            try {
                document.add(new Paragraph("Nombre del libro: " + book.getTitle() + ", Author: " + book.getAuthor()));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        
        document.add(new Paragraph("Total de libros: " + totalBooks)); // Agregar la cantidad total al documento
        
        document.close();
    } catch (DocumentException e) {
        e.printStackTrace();
    }

    return new ByteArrayInputStream(out.toByteArray());
}


}
