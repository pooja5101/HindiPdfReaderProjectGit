package com.example.ocr.controller;

import com.example.ocr.model.CustomMultipartFile;
import com.example.ocr.model.PdfDocument;
import com.example.ocr.service.PdfStorageService;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ocr.service.OcrService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    private PdfStorageService pdfStorageService;

    @GetMapping("/test")
    public String testGet() {
    	return "Hare Krishna";
    }
    
    @PostMapping("/extract-text")
    public String extractText(@RequestParam("file") MultipartFile file) throws IOException {
        // Save the uploaded file temporarily
        File tempFile = File.createTempFile("ocr-", ".pdf");
        file.transferTo(tempFile);

        // Extract text
        String extractedText = ocrService.extractTextFromPdf(tempFile.getAbsolutePath());

        // Delete temp file
        tempFile.delete();

        return extractedText;
    }

    @GetMapping("/getExtractedTextByPageNo")
    public String extractTextFromPageNo(@RequestParam int id, @RequestParam int pn ) throws IOException {
        // Save the uploaded file temporarily
        PdfDocument pdfDocument = pdfStorageService.getPdf((long)id);

        //convert pdfDocument to file , MultipartFile
        CustomMultipartFile file = new CustomMultipartFile(pdfDocument.getFileName(), pdfDocument.getFileName(), "pdf", pdfDocument.getContent());
        File tempFile = File.createTempFile("ocr-", ".pdf");
        file.transferTo(tempFile);

        // Extract text
        String extractedText = ocrService.extractTextFromPdfFromPageNo(tempFile.getAbsolutePath(), pn);

        // Delete temp file
        tempFile.delete();

        return extractedText;
    }
}