package io.provisionlabs.example.ocr.model;

import java.util.List;

public class Table {
    public int table_number;
    public Loc loc;
    public List<List<TableCell>> cells;
}
