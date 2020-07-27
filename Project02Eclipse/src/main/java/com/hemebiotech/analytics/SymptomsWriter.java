package com.hemebiotech.analytics;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * A {@link Writer} for symptoms lists.
 */
public final class SymptomsWriter extends FilterWriter {
    public SymptomsWriter(Writer writer) {
        super(writer);
    }

    /**
     * Write each symptoms with its number of occurrences line by line.
     *
     * @param symptomsCount the symptoms count to write
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("RedundantThrows")
    public void writeCount(Map<String, Integer> symptomsCount) throws IOException {
        symptomsCount.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> {
                    String symptom = e.getKey();
                    int count = e.getValue();
                    try {
                        out.write(symptom + ": " + count + "\n");
                    } catch (IOException ex) {
                        sneakyThrow(ex);
                    }
                });
    }

    /*
     * A bit of magic for unchecked lambdas. See: https://www.baeldung.com/java-sneaky-throws
     */
    private static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }
}
