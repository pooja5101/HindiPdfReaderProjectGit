package com.example.ocr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ocr.model.PdfDocument;

public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
	
}
