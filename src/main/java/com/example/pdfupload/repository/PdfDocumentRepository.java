package com.example.pdfupload.repository;

import com.example.pdfupload.model.PdfDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
}