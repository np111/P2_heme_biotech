package com.hemebiotech.analytics;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AnalyticsCounter {
    public static void main(String[] args) throws Exception {
        // read symptoms.txt
        List<String> symptoms = new FileSymptomReader("symptoms.txt").getSymptoms();

        // count symptoms
        SymptomsCounter counter = new SymptomsCounter();
        counter.add(symptoms);

        // write symptoms.txt
        try (SymptomsWriter wr = new SymptomsWriter(new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("result.out"), StandardCharsets.UTF_8)))) {
            wr.writeCount(counter.getCount());
        }
    }
}
