package com.hemebiotech.analytics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An aggregator that counts the number of occurrences per symptom.
 */
public class SymptomCounter {
    private final Map<String, Integer> count = new HashMap<>();

    /**
     * Add a symptom occurrence.
     *
     * @param symptom the symptom to add
     */
    public void add(String symptom) {
        Objects.requireNonNull(symptom, "symptom cannot be null");
        count.merge(symptom, 1, Integer::sum);
    }

    /**
     * Add many symptoms occurrences.
     *
     * @param symptoms the symptoms to add
     */
    public void add(Iterable<String> symptoms) {
        symptoms.forEach(this::add);
    }

    /**
     * Reset the current count.
     */
    public void reset() {
        count.clear();
    }

    /**
     * Return the current count.
     *
     * @return an unmodifiable map associating each symptom to is number of occurrences
     */
    public Map<String, Integer> getCount() {
        return Collections.unmodifiableMap(count);
    }
}
