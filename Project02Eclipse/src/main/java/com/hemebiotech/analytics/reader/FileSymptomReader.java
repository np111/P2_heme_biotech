package com.hemebiotech.analytics.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * Read symptoms from a file which contains symptom strings line by line.
 */
public class FileSymptomReader extends InputStreamSymptomReader {
    /**
     * @param pathname a full or relative path to file with symptom strings in it
     * @throws NullPointerException  if pathname is null
     * @throws FileNotFoundException if the file does not exists
     */
    public FileSymptomReader(String pathname) throws FileNotFoundException {
        this(new File(Objects.requireNonNull(pathname, "pathname cannot be null")));
    }

    /**
     * @param file a file with symptom strings in it
     * @throws NullPointerException  if file is null
     * @throws FileNotFoundException if the file does not exists
     */
    public FileSymptomReader(File file) throws FileNotFoundException {
        super(new FileInputStream(Objects.requireNonNull(file, "file cannot be null")));
    }
}
