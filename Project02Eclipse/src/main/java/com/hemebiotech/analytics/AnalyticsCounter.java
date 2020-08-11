package com.hemebiotech.analytics;

import com.hemebiotech.analytics.reader.FileSymptomReader;
import com.hemebiotech.analytics.reader.ISymptomReader;
import com.hemebiotech.analytics.reader.InputStreamSymptomReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Callable;
import lombok.Data;
import picocli.CommandLine;

@CommandLine.Command(
        name = "analytics-counter",
        description = "Count symptoms occurrences.",
        mixinStandardHelpOptions = true
)
@Data
public class AnalyticsCounter implements Callable<Integer> {
    private static final String DEFAULT_SOURCE = "symptoms.txt";
    private static final String DEFAULT_DEST = "result.out";
    private static final String STDIO_PATH = "-";
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new AnalyticsCounter()).execute(args);
        System.exit(exitCode);
    }

    @CommandLine.Parameters(index = "0", paramLabel = "[SOURCE]", defaultValue = DEFAULT_SOURCE,
            description = "The symptoms file to read.")
    private String source;

    @CommandLine.Parameters(index = "1", paramLabel = "[DEST]", defaultValue = DEFAULT_DEST,
            description = "The file to write the results to.")
    private String dest;

    /**
     * Run the program.
     *
     * @return the exit code (0 for success)
     */
    @Override
    public Integer call() {
        // read symptoms.txt
        printInfo("Reading symptoms...");
        List<String> symptoms;
        try {
            ISymptomReader symptomReader = createSymptomReader();
            symptoms = symptomReader.getSymptoms();
        } catch (Exception e) {
            printError("An error occurred while reading the symptoms list:", e);
            return EXIT_FAILURE;
        }

        // count symptoms
        printInfo("Counting symptoms occurrences...");
        SymptomCounter counter = new SymptomCounter();
        counter.add(symptoms);

        // write symptoms.txt
        printInfo("Writing results...");
        try (SymptomWriter wr = new SymptomWriter(new BufferedWriter(
                new OutputStreamWriter(createOutputStream(), StandardCharsets.UTF_8)))) {
            wr.writeCount(counter.getCount());
        } catch (Exception e) {
            printError("An error occurred while writing the result file:", e);
            return EXIT_FAILURE;
        }

        printInfo("Done!");
        return EXIT_SUCCESS;
    }

    /**
     * Create the symptom reader to be used by the program.
     */
    private ISymptomReader createSymptomReader() throws IOException {
        if (STDIO_PATH.equals(source)) {
            return new InputStreamSymptomReader(System.in);
        }
        return new FileSymptomReader(source);
    }

    /**
     * Create the results output stream to be used by the program.
     */
    private OutputStream createOutputStream() throws IOException {
        if (STDIO_PATH.equals(dest)) {
            return System.out;
        }
        return new FileOutputStream(dest);
    }

    /**
     * Print an informative message from the program.
     */
    private void printInfo(String message) {
        // Note: info is printed to stderr to keep stdout clean (for an eventual result on it)
        System.err.println(message);
    }

    /**
     * Print an error message from the program.
     *
     * @param message   the message
     * @param exception the error cause (or null if none)
     */
    private void printError(String message, Exception exception) {
        if (exception instanceof FileNotFoundException) {
            // don't print the stacktrace for FileNotFoundException, the message is enough
            message += " " + exception.getMessage();
            exception = null;
        }

        System.err.println(message);
        if (exception != null) {
            exception.printStackTrace(System.err);
        }
    }
}
