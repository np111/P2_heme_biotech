package com.hemebiotech.analytics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnalyticsCounter {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> symptomsCounts = new HashMap<>();

        // read symptoms.txt line by line
        try (BufferedReader rd = new BufferedReader(
                new InputStreamReader(new FileInputStream("symptoms.txt"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = rd.readLine()) != null) {
                // count symptoms occurrences
                symptomsCounts.merge(line, 1, Integer::sum);
            }
        }

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
