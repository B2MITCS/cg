package com.b2m.cg.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.b2m.cg.service.CertificateService;

@RestController
public class CgController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/generateCertificate")
    public ResponseEntity<InputStreamResource> generateCertificate(@RequestParam String username, @RequestParam String companyName) {
        try {
            ByteArrayOutputStream pdfStream = certificateService.generateCertificate(username, companyName);
            InputStreamResource resource = new InputStreamResource(new java.io.ByteArrayInputStream(pdfStream.toByteArray()));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfStream.size())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}




