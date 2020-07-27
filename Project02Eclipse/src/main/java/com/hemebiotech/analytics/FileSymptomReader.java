package com.hemebiotech.analytics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Read symptoms from a file which contains symptom strings line by line.
 */
public class FileSymptomReader implements ISymptomReader {
    private final File file;

    /**
     * @param pathname a full or relative path to file with symptom strings in it
     * @throws NullPointerException if pathname is null
     */
    public FileSymptomReader(String pathname) {
        this(new File(Objects.requireNonNull(pathname, "pathname cannot be null")));
    }

    /**
     * @param file a file with symptom strings in it
     * @throws NullPointerException if file is null
     */
    public FileSymptomReader(File file) {
        this.file = Objects.requireNonNull(file, "file cannot be null");
    }

    @Override
    public List<String> getSymptoms() throws IOException {
        ArrayList<String> res = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = rd.readLine()) != null) {
                res.add(line);
            }
        }
        return res;
    }
}
