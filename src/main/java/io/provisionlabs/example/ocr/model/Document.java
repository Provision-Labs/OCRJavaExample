package io.provisionlabs.example.ocr.model;

import java.util.List;

public class Document {
    public int width;
    public int height;
    public int schema_version;
    public List<Page> page;
}