package year2025.day5;

import main.ReadLines;
import java.util.*;

public class Day5 {
    private final ReadLines reader = new ReadLines(2025, 5, 2);

    private long[][] ranges;
    private long[] numbers;

    private void processData(){
        List<String> fileLines = reader.readFile();

        int rangesSize = 0;
        int numbersSize = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            if(fileLines.get(i).isEmpty()){
                break;
            }
            rangesSize ++;
        }

        for (int i = rangesSize + 1; i < fileLines.size(); i++) {
            numbersSize ++;
        }

        ranges = new long[rangesSize][2];
        numbers = new long[numbersSize];

        for (int i = 0; i < rangesSize; i++) {
            String line = fileLines.get(i);
            String[] parts = line.split("-");
            ranges[i][0] = Long.parseLong(parts[0]);
            ranges[i][1] = Long.parseLong(parts[1]);
        }

        int counter = 0;
        for (int i = rangesSize + 1; i < fileLines.size(); i++) {
            numbers[counter] = Long.parseLong(fileLines.get(i));
            counter ++;
        }
    }

    public void part1(){
        processData();

        int result = 0;

        for (long number : numbers) {
            for (long[] range : ranges) {
                if (number >= range[0] && number <= range[1]) {
                    result++;
                    break;
                }
            }
        }
        System.out.println(result);
    }

    public void part2() {
        processData();

        Arrays.sort(ranges, Comparator.comparingLong(a -> a[0]));
        List<long[]> merged = new ArrayList<>();

        for (long[] range : ranges) {
            long start = range[0];
            long end = range[1];

            if (merged.isEmpty()) {
                merged.add(new long[]{start, end});

            } else {
                long[] last = merged.getLast();

                if (start <= last[1] + 1) {
                    last[1] = Math.max(last[1], end);
                } else {
                    merged.add(new long[]{start, end});
                }
            }
        }

        long result = 0;
        for (int i = 0; i < merged.size(); i++) {
            result = result + merged.get(i)[1] - merged.get(i)[0] + 1;
        }

        System.out.println(result);
    }
}