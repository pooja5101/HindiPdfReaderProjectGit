package com.example.ocr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.ocr.model.PdfDocument;
import com.example.ocr.repository.PdfDocumentRepository;


import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import java.io.IOException;

//@Service
//public class PdfStorageService {
//
//    @Autowired
//    private PdfDocumentRepository pdfDocumentRepository;
//
//    public PdfDocument storePdf(MultipartFile file) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        PdfDocument pdfDocument = new PdfDocument(fileName, file.getBytes());
//        return pdfDocumentRepository.save(pdfDocument);
//    }
//
//    public PdfDocument getPdf(Long id) {
//        return pdfDocumentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
//    }
//}


@Service
public class PdfStorageService {

    @Autowired
    private PdfDocumentRepository pdfDocumentRepository;
    
    @Autowired
    private DataSource dataSource;

    public PdfDocument storePdf(MultipartFile file) throws IOException, SQLException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try (Connection conn = dataSource.getConnection()) {
            // Begin transaction
            conn.setAutoCommit(false);
            
            // Get the Large Object Manager
            LargeObjectManager lobj = conn.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
            
            // Create a new large object
            long oid = lobj.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
            
            // Open the large object for writing
            try (LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE)) {
                obj.write(file.getBytes());
            }
            
            PdfDocument pdfDocument = new PdfDocument(fileName, oid);
            pdfDocument.setOid(oid);
            pdfDocument.setContent(file.getBytes());
            PdfDocument savedDoc = pdfDocumentRepository.save(pdfDocument);
            conn.commit();
            return savedDoc;
        }
    }
    
    public PdfDocument getPdf(Long id) {
        return pdfDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }
    
    public byte[] getPdfContent(Long id) throws SQLException {
        PdfDocument pdfDocument = pdfDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
        
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager lobj = conn.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
            
            try (LargeObject obj = lobj.open(pdfDocument.getOid(), LargeObjectManager.READ)) {
                byte[] content = new byte[obj.size()];
                obj.read(content, 0, obj.size());
                return content;
            }
        }
    }
}