package com.surendra.java;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
public class FileParse {

    private List<String[]> csvData;
    List<String[]> csvParse(FileReader csvFile)
    {
        try {
            CSVReader reader = new CSVReader(csvFile);
             csvData = reader.readAll();
            reader.close();
        } catch(CsvException | IOException e) {
                e.printStackTrace();
            }

        return csvData;

    }
}
