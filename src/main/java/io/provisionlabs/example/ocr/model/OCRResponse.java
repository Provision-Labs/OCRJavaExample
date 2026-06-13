package io.provisionlabs.example.ocr.model;

import java.util.List;

public class OCRResponse {
    private List<Document> document;

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }
}