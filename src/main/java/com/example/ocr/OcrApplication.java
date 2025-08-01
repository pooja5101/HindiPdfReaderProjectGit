package com.example.ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.pdfupload.PdfUploadApplication;

@SpringBootApplication
@EnableConfigurationProperties
public class OcrApplication {

	public static void main(String[] args) {
		 SpringApplication.run(OcrApplication.class, args);
	}

}