package io.provisionlabs.example.ocr;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class OCRTestImageMultipart {

    @Test
    public void test() throws IOException {
        String processUrlTemplate = "http://localhost:8098/processing/{template}";
        String templateDoc = "default";

        RestTemplate restTemplate = new RestTemplate();
        byte[] imageBytes = new ClassPathResource("images/s4f_1.jpg").getContentAsByteArray();

        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentType(MediaType.IMAGE_JPEG);
        fileHeaders.setContentDispositionFormData("file", "s4f_1.jpg");

        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        data.add("file", new HttpEntity<>(imageBytes, fileHeaders));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<String> response = restTemplate.postForEntity(
                processUrlTemplate.replace("{template}", templateDoc),
                new HttpEntity<>(data, requestHeaders),
                String.class
        );

        System.out.println("Response: \n" + response.getBody());
    }

}
