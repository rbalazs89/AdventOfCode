package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReadLines {

    private final int year;
    private final int day;
    private int fileInputNumber;

    public ReadLines(int year, int day) {
        this.year = year;
        this.day = day;
    }

    public ReadLines(int year, int day, int fileInputNumber) {
        this.year = year;
        this.day = day;
        this.fileInputNumber = fileInputNumber;
    }
    public List<String> readFile(){
        int inputNumber = fileInputNumber;
        if (inputNumber != 1 && inputNumber != 2) {
            inputNumber = 1;
        }

        String filename = "input" + inputNumber + ".txt";
        String resourcePath = String.format("/year%d/day%d/%s", year, day, filename);


        InputStream is = getClass().getResourceAsStream(resourcePath);

        if (is != null) {

        } else {
            System.out.println("FAILED: getResourceAsStream returned null for " + resourcePath);
            // List all resources at parent to debug
            String parentPath = String.format("/year%d/day%d/", year, day);
            System.out.println("Trying to check if parent directory exists: " + parentPath);
            InputStream parentTest = getClass().getResourceAsStream(parentPath);
            System.out.println("Parent directory test: " + (parentTest == null ? "null (not found)" : "found (but directories don't stream)"));
        }

        List<String> lines = loadResource(resourcePath);

        if (!lines.isEmpty()) {
            return lines;
        }

        // Fallback logic same as before...
        if (inputNumber == 2) {
            System.out.println("Falling back to input1.txt");
            return readFile(1);
        }

        System.out.println("No data loaded at all.");
        return Collections.emptyList();
    }

    public List<String> readFile(int inputNumber) {
        if (inputNumber != 1 && inputNumber != 2) {
            inputNumber = 1;
        }

        String filename = "input" + inputNumber + ".txt";
        String resourcePath = String.format("/year%d/day%d/%s", year, day, filename);


        InputStream is = getClass().getResourceAsStream(resourcePath);

        if (is != null) {
            //System.out.println("SUCCESS: Found " + resourcePath);
        } else {
            System.out.println("FAILED: getResourceAsStream returned null for " + resourcePath);
            // List all resources at parent to debug
            String parentPath = String.format("/year%d/day%d/", year, day);
            System.out.println("Trying to check if parent directory exists: " + parentPath);
            InputStream parentTest = getClass().getResourceAsStream(parentPath);
            System.out.println("Parent directory test: " + (parentTest == null ? "null (not found)" : "found (but directories don't stream)"));
        }

        List<String> lines = loadResource(resourcePath);

        if (!lines.isEmpty()) {
            return lines;
        }

        // Fallback logic same as before...
        if (inputNumber == 2) {
            System.out.println("Falling back to input1.txt");
            return readFile(1);
        }

        System.out.println("No data loaded at all.");
        return Collections.emptyList();
    }

    private List<String> loadResource(String resourcePath) {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        if (is == null) {
            return Collections.emptyList();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines()
                    .map(String::strip)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error reading stream: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}