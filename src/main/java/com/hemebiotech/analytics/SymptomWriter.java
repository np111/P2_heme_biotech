package com.hemebiotech.analytics;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.stream.Stream;
import lombok.SneakyThrows;

/**
 * A {@link Writer} for symptoms lists.
 */
public final class SymptomWriter extends FilterWriter {
    public SymptomWriter(Writer writer) {
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
                .forEach(this::writeEach);
    }

    /**
     * {@link SneakyThrows} is used to allow usage of this method with {@link Stream#forEach}. But the calling method
     * MUST catch or throws the suppressed {@link IOException}!
     */
    @SneakyThrows
    private void writeEach(Map.Entry<String, Integer> symptomsCountEntry) {
        String symptom = symptomsCountEntry.getKey();
        int count = symptomsCountEntry.getValue();
        out.write(symptom + ": " + count + "\n");
    }
}
