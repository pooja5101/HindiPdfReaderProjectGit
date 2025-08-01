package com.example.tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Test {
    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();
        try {
            // Updated path
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("hin");
            
            URL imageUrl = Test.class.getResource("hindi-text.jpg");
            BufferedImage image = ImageIO.read(imageUrl);
            String text = tesseract.doOCR(image);
            System.out.println("OCR Output:\n" + text);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}