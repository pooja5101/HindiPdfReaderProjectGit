package com.example.pdfupload.controller;

import com.example.pdfupload.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            pdfService.storeFile(file);
            return ResponseEntity.ok("PDF uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload PDF: " + e.getMessage());
        }
    }
}