package com.example.hotelmanagment.dto;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Component
public class CsvExportUtil {
    public byte[] exportToCsv(List<String> headers, List<List<String>> data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(out), CSVFormat.DEFAULT);

        csvPrinter.printRecord(headers);
        for (List<String> record : data) {
            csvPrinter.printRecord(record);
        }

        csvPrinter.flush();
        return out.toByteArray();
    }
}
