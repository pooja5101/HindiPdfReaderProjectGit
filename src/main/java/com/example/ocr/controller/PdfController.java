package com.example.ocr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ocr.model.PdfDocument;
import com.example.ocr.service.PdfStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfStorageService pdfStorageService;

    @GetMapping("/getHN")
    public String getHN() {
    	return "Hare Krishna";
    }
    @PostMapping("/upload")
    public Long uploadPdf(@RequestParam("file") MultipartFile file) throws SQLException {
        try {
            PdfDocument pdfDocObj = pdfStorageService.storePdf(file);
            return pdfDocObj.getId();
            //return ResponseEntity.status(HttpStatus.OK)
                   // .body("PDF uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Could not upload the file: " + file.getOriginalFilename());
        	return (long)404;
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        PdfDocument pdfDocument = pdfStorageService.getPdf(id);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + pdfDocument.getFileName() + "\"")
                .body(pdfDocument.getContent());
    }
    
    @GetMapping("/download-stream/{id}")
    public ResponseEntity<InputStreamResource> downloadPdfStream(@PathVariable Long id) throws SQLException {
        PdfDocument pdfDocument = pdfStorageService.getPdf(id);
        byte[] pdfContentaInputStream = pdfStorageService.getPdfContent(pdfDocument.getOid());
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfContentaInputStream);
        InputStreamResource resource = new InputStreamResource(inputStream);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                       "attachment; filename=\"" + pdfDocument.getFileName() + "\"")
                .contentLength(pdfDocument.getContent().length)
                .body(resource);
    }
}