package com.hemebiotech.analytics;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AnalyticsCounter {
    private static final String IN_FILE = "symptoms.txt";
    private static final String OUT_FILE = "result.out";

    public static void main(String[] args) {
        try {
            run();
        } catch (CliException e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (Throwable t) {
            System.err.println("An unhandled error occurred!");
            t.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private static void run() throws CliException {
        // read symptoms.txt
        System.out.println("Reading \"" + IN_FILE + "\"...");
        List<String> symptoms;
        try {
            symptoms = new FileSymptomReader("symptoms.txt").getSymptoms();
        } catch (Exception e) {
            throw new CliException("An error occurred while reading the symptoms list.", e);
        }

        // count symptoms
        System.out.println("Counting symptoms occurrences...");
        SymptomsCounter counter = new SymptomsCounter();
        counter.add(symptoms);

        // write symptoms.txt
        System.out.println("Writing \"" + OUT_FILE + "\"...");
        try (SymptomsWriter wr = new SymptomsWriter(new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(OUT_FILE), StandardCharsets.UTF_8)))) {
            wr.writeCount(counter.getCount());
        } catch (Exception e) {
            throw new CliException("An error occurred while writing the result file.", e);
        }

        System.out.println("Done!");
    }

    private static class CliException extends Exception {
        public CliException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
