package com.example.ocr.controller;
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
}