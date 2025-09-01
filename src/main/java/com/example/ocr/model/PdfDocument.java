package com.example.ocr.model;
import jakarta.persistence.*;

@Entity
@Table(name = "pdf_documents")
public class PdfDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Lob
    private byte[] content;
    
    @Column(name = "oid")
    private Long oid; // PostgreSQL large object OID
    
    // Constructors, getters, and setters
    public PdfDocument() {
    }

    public PdfDocument(String fileName, Long oid) {
        this.fileName = fileName;
        this.oid = oid;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}