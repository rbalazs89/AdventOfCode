package year2024.day11;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    static ArrayList<Long> numbers1 = new ArrayList<>();
    static Map<Long, Long> numbers2 = new HashMap<>();
    private static final int BLINKS = 75; // specified in task description

    private final ReadLines reader = new ReadLines(2024, 11, 2);

    private void processFile() {
        List<String> input = reader.readFile();
        String[] array1 = input.getFirst().split(" ");
        for (String s : array1) {
            numbers1.add(Long.valueOf(s));
        }
    }

    private void processFile2() {
        List<String> input = reader.readFile();
        String[] array1 = input.getFirst().split(" ");
        for (String s : array1) {
            Long num = Long.valueOf(s);
            numbers2.put(num, numbers2.getOrDefault(num, 0L) + 1);
        }
    }

    public void part1(){
        processFile();
        for (int k = 0; k < 25; k++) {
            ArrayList<Long> result = new ArrayList<>();
            for (Long number : numbers1) {
                result.addAll(processOneNumber(number));
            }
            numbers1 = result;
        }
        System.out.println(numbers1.size());
    }

    public void part2(){
        processFile2();
        long totalCount = 0;
        for (int k = 0; k < BLINKS; k++) {
            Map<Long, Long> newNumbers = new HashMap<>();
            for (Map.Entry<Long, Long> entry : numbers2.entrySet()) {
                Long number = entry.getKey();
                Long count = entry.getValue();
                for (Long result : processOneNumber(number)) {
                    newNumbers.put(result, newNumbers.getOrDefault(result, 0L) + count);
                }
            }
            numbers2 = newNumbers;
            totalCount = numbers2.values().stream().mapToLong(Long::longValue).sum();
        }
        System.out.println(totalCount);
    }

    private static ArrayList<Long> processOneNumber(Long number) {
        ArrayList<Long> result = new ArrayList<>();
        if (number == 0) {
            result.add(1L);
        } else if (String.valueOf(number).length() % 2 == 0) {
            String myString = String.valueOf(number);
            String s1 = myString.substring(0, myString.length() / 2);
            String s2 = myString.substring(myString.length() / 2);
            result.add(Long.valueOf(s1));
            result.add(Long.valueOf(s2));
        } else {
            result.add(number * 2024);
        }
        return result;
    }
}
