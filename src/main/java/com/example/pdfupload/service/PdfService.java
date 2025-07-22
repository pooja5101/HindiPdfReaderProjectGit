package com.example.pdfupload.service;

import com.example.pdfupload.exception.FileStorageException;
import com.example.pdfupload.model.PdfDocument;
import com.example.pdfupload.repository.PdfDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Service
public class PdfService {

    @Autowired
    private PdfDocumentRepository pdfDocumentRepository;
    
    @Autowired
    private FileStorageService fileStorageService;

    public PdfDocument storeFile(MultipartFile file) {
        // Store the file on the server
        String fileName = fileStorageService.storeFile(file);
        
        // Create and save PDF document metadata
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.setFileName(fileName);
        pdfDocument.setFilePath(fileStorageService.getFileStorageLocation().resolve(fileName).toString());
        pdfDocument.setFileSize(file.getSize());
        pdfDocument.setUploadDate(LocalDateTime.now());
        
        return pdfDocumentRepository.save(pdfDocument);
    }
}