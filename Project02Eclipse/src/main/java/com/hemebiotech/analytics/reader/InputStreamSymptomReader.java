package com.hemebiotech.analytics.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Read symptoms from a stream which contains symptom strings line by line.
 */
public class InputStreamSymptomReader implements ISymptomReader {
    private final InputStream inputStream;

    /**
     * @param inputStream a stream with symptom strings in it
     * @throws NullPointerException if file is null
     */
    public InputStreamSymptomReader(InputStream inputStream) {
        this.inputStream = Objects.requireNonNull(inputStream, "inputStream cannot be null");
    }

    @Override
    public List<String> getSymptoms() throws IOException {
        ArrayList<String> res = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = rd.readLine()) != null) {
                res.add(line);
            }
        }
        return res;
    }
}
