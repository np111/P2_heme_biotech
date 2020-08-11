# Analytics (Heme Biotech)

A utility program that counts the number of occurrences of different symptoms
from a list.

![Poster](.readme/poster.jpg?raw=true)

## Usage

You can download compiled binaries from the [releases page](https://github.com/np111/P2_heme_biotech/releases).

Run the program (using Java 8+): `java -jar hemebiotech-analytics-1.1.1.jar`
```
Usage: analytics-counter [-hV] [SOURCE] [DEST]
Count symptoms occurrences.
      [SOURCE]    The symptoms file to read (default: symptoms.txt).
                  Use - to read from the standard input.
      [DEST]      The file to write the results to (default: result.out).
                  Use - to write to the standard output.
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
```

## Build

This project uses maven.
You can compile the final jar by running: `mvn package`

## Notes

This is a school project (for OpenClassrooms).
