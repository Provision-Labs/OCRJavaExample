package io.provisionlabs.example.ocr;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class OCRTestImageBinary {

    @Test
    public void test1() throws IOException {
        String processUrlTemplate = "http://localhost:8098/processing/{template}";
        String templateDoc = "default";
        RestTemplate restTemplate = new RestTemplate();
        byte[] imageBytes = new ClassPathResource("images/s4f_1.jpg").getContentAsByteArray();

        ResponseEntity<String> response = restTemplate.postForEntity(
                processUrlTemplate.replace("{template}", templateDoc),
                new HttpEntity<>(imageBytes, new HttpHeaders() {{ setContentType(MediaType.IMAGE_PNG); }}),
                String.class
        );

        System.out.println("Response: \n" + response.getBody() );
    }
}
