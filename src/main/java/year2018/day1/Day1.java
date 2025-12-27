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
            numbers.add(Integer.parseInt(line));
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
        Set<Integer> seenFrequencies = new HashSet<>();
        int sum = 0;
        seenFrequencies.add(sum);

        // guaranteed to terminate because the frequency will eventually repeat
        while(true){
            for (int delta : numbers) {
                sum += delta;

                // first frequency reached twice
                if(!seenFrequencies.add(sum)){
                    System.out.println(sum);
                    return;
                }
            }
        }
    }
}
