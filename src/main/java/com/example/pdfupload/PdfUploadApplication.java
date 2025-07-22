package com.example.pdfupload;

import com.example.pdfupload.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class PdfUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdfUploadApplication.class, args);
    }
}