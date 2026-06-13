package io.provisionlabs.example.ocr.model;

import java.util.List;

public class TableCell {
    public Loc loc;
    public int colspan;
    public int rowspan;
    public String text;
    public List<Block> blocks;
    public double prob;
    public String tag;
}
