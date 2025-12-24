package year2025.day5;

import main.ReadLines;
import java.util.*;

public class Day5 {

    List<String> fileLines;
    long[][] ranges;
    long[] numbers;

    private final ReadLines reader = new ReadLines(2025, 5);
    int inputNumber = 2; // use 1 for mock data, 2 for real data
    private void readData(){
        // READ INPUT
        fileLines = reader.readFile(inputNumber);
    }

    public void processData(){
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
        readData();
        processData();

        int result = 0;

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < ranges.length; j++) {
                if(numbers[i] >= ranges[j][0] && numbers[i] <= ranges[j][1]){
                    result ++;
                    break;
                }
            }
        }
        System.out.println("part 1 result: " + result);
    }

    public void part2() {
        readData();
        processData();

        Arrays.sort(ranges, Comparator.comparingLong(a -> a[0]));

        List<long[]> merged = new ArrayList<>();

        for (int j = 0; j < ranges.length; j++) {
            long start = ranges[j][0];
            long end = ranges[j][1];

            if (merged.isEmpty()) {
                merged.add(new long[]{start, end});

            } else {
                long[] last = merged.get(merged.size() - 1);

                if (start <= last[1] + 1) {
                    last[1] = Math.max(last[1], end);
                } else {
                    merged.add(new long[]{start, end});
                }
            }
        }

        long result = 0;
        //merged.sort(Comparator.comparingLong(a -> a[0]));
        for (int i = 0; i < merged.size(); i++) {
            result = result + merged.get(i)[1] - merged.get(i)[0] + 1;
        }

        System.out.println("part 2 result: " + result);
    }
}