package io.provisionlabs.example.ocr.model;

import java.util.List;

public class Paragraph {
    public String text;
    public String tag;
    public Loc loc;
    public double prob;
    public List<Block> blocks;
}
