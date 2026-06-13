package io.provisionlabs.example.ocr.model;

import java.util.List;

public class Page {
    public int page_number;
    public Loc loc;
    public List<Block> blocks;
    public List<Paragraph> paragraphs;
    public List<Table> tables;

}