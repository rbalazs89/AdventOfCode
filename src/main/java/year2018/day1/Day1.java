package year2018.day1;

import main.ReadLines;

import java.util.*;

public class Day1 {

    // setup:
    private final ReadLines reader = new ReadLines(2018, 1, 2);

    private List<Integer> processInput(){
        List<String> input = reader.readFile();
        List<Integer> numbers = new ArrayList<>();
        for (String line : input) {
            numbers.add(Integer.valueOf(line));
        }

        return numbers;
    }

    public void part1(){
        List<Integer> numbers = processInput();

        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        System.out.println(sum);
    }

    public void part2() {
        // setup
        List<Integer> numbers = processInput();

        // prepare while loop:
        Set<Integer> detectDuplicates = new HashSet<>();
        int sum = 0;
        detectDuplicates.add(sum);
        boolean duplicateFound = false;

        // run until duplicate is found
        while(!duplicateFound){
            for (int number : numbers) {
                sum += number;

                // first frequency reached twice
                if(!detectDuplicates.add(sum)){
                    duplicateFound = true;
                    break;
                }
            }
        }
        System.out.println(sum);
    }
}
