package com.example.ocr.service;

import net.sourceforge.tess4j.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class OcrService {

    public String extractTextFromPdf(String pdfPath) {
        try {
            // Load PDF and convert to image
            PDDocument document = PDDocument.load(new File(pdfPath));
            PDFRenderer renderer = new PDFRenderer(document);
            StringBuilder extractedText = new StringBuilder();

            // Extract text from each page
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300); // 300 DPI for better accuracy
                extractedText.append(extractTextFromImage(image));
            }

            document.close();
            return extractedText.toString();
        } catch (Exception e) {
            throw new RuntimeException("OCR failed: " + e.getMessage());
        }
    }

    public String extractTextFromPdfFromPageNo(String pdfPath, int n) {
        try {
            // Load PDF and convert to image
            PDDocument document = PDDocument.load(new File(pdfPath));
            PDFRenderer renderer = new PDFRenderer(document);
            StringBuilder extractedText = new StringBuilder();

            // Extract text from each page
            for (int i = n; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300); // 300 DPI for better accuracy
                extractedText.append(extractTextFromImage(image));
            }

            document.close();
            return extractedText.toString();
        } catch (Exception e) {
            throw new RuntimeException("OCR failed: " + e.getMessage());
        }
    }

    public String extractTextFromImage(BufferedImage image) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // Path to tessdata
        tesseract.setLanguage("hin"); // Hindi language
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO); // Auto-detect layout

        try {
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            throw new RuntimeException("OCR failed: " + e.getMessage());
        }
    }
}