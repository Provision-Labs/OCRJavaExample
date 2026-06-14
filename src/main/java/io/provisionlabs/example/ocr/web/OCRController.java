package io.provisionlabs.example.ocr.web;

import io.provisionlabs.example.ocr.client.OCRClient;
import io.provisionlabs.example.ocr.model.Document;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/ocr")
public class OCRController {
    private final OCRClient ocrClient;

    public OCRController(OCRClient ocrClient) {
        this.ocrClient = ocrClient;
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "default") String template, Model model) {
        model.addAttribute("template", template);
        return "ocr";
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProcessResponse process(@RequestParam("file") MultipartFile file,
                                   @RequestParam(defaultValue = "default") String template) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Файл не выбран");
        }

        List<Document> documents = ocrClient.processAndParse(file.getBytes(), template, file.getContentType(), file.getOriginalFilename());
        return new ProcessResponse(documents != null ? documents : Collections.emptyList());
    }

    public record ProcessResponse(List<Document> documents) {
    }
}
