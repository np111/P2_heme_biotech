package com.hemebiotech.analytics;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnalyticsCounter {
    public static void main(String[] args) throws Exception {
        // read symptoms.txt
        List<String> symptoms = new FileSymptomReader("symptoms.txt").getSymptoms();

        // count symptoms
        SymptomsCounter counter = new SymptomsCounter();
        counter.add(symptoms);

        // write symptoms.txt
        try (Writer wr = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("result.out"), StandardCharsets.UTF_8))) {
            // sort symptoms alphabetically
            Iterator<Map.Entry<String, Integer>> it = counter.getCount().entrySet()
                    .stream().sorted(Map.Entry.comparingByKey()).iterator();

            // write line by line
            while (it.hasNext()) {
                Map.Entry<String, Integer> e = it.next();
                wr.write(e.getKey() + ": " + e.getValue() + "\n");
            }
        }
    }
}
