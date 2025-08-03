package com.example.ocr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.ocr.model.PdfDocument;
import com.example.ocr.repository.PdfDocumentRepository;

import java.io.IOException;

@Service
public class PdfStorageService {

    @Autowired
    private PdfDocumentRepository pdfDocumentRepository;

    public PdfDocument storePdf(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PdfDocument pdfDocument = new PdfDocument(fileName, file.getBytes());
        return pdfDocumentRepository.save(pdfDocument);
    }

    public PdfDocument getPdf(Long id) {
        return pdfDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }
}