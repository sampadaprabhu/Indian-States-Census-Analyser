package com.bridgelabz.census;

public class CSVBuilderFactory {
    public static ICSVBuilder createBuilder() {
        return new OpenCsvBuilder();
    }
}
