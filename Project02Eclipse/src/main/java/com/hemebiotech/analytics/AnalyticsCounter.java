package com.hemebiotech.analytics;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnalyticsCounter {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> symptomsCounts = new HashMap<>();

        // read symptoms.txt line by line
        List<String> symptoms = new FileSymptomReader("symptoms.txt").getSymptoms();
        symptoms.forEach(symptom -> symptomsCounts.merge(symptom, 1, Integer::sum));

        // write symptoms.txt
        try (Writer wr = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("result.out"), StandardCharsets.UTF_8))) {
            // sort symptoms alphabetically
            Iterator<Map.Entry<String, Integer>> it = symptomsCounts.entrySet()
                    .stream().sorted(Map.Entry.comparingByKey()).iterator();

            // write line by line
            while (it.hasNext()) {
                Map.Entry<String, Integer> e = it.next();
                wr.write(e.getKey() + ": " + e.getValue() + "\n");
            }
        }
    }
}
