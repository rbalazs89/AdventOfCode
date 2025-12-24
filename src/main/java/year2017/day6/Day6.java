package year2017.day6;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {
    //setup:
    private final ReadLines reader = new ReadLines(2017, 6, 2);
    private List<String> fileLines; // lines from txt file, updated once

    // puzzle:
    private int[] memoryBank;

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        ArrayList<Integer> temporaryList = new ArrayList<>();
        Pattern p = Pattern.compile("\\d+");
        String line = fileLines.getFirst();
        Matcher m = p.matcher(line);

        while (m.find()){
            temporaryList.add(Integer.parseInt(m.group()));
        }

        memoryBank = new int[temporaryList.size()];
        for (int i = 0; i < memoryBank.length; i++) {
            memoryBank[i] = temporaryList.get(i);
        }
    }

    public void part1(){
        prepare();

        int[] numbers = memoryBank.clone();
        int length = numbers.length;
        Set<String> duplicateDetector = new HashSet<>();

        boolean found = false;
        String firstDuplicate = "";

        int counter = 0;
        while(true) {
            counter++;
            // find the element to distribute in the next step:
            int index = findIndex(numbers);

            //redistribute element
            int steps = numbers[index];
            for (int i = index + 1; i <= index + steps; i++) {
                numbers[i % length] ++;
                numbers[index]--;
            }

            // save current state in set
            StringBuilder sb = new StringBuilder();
            for (int number : numbers) {
                sb.append(number);
                sb.append(",");
            }
            if(firstDuplicate.contentEquals(sb)){
                System.out.println(counter);
                break;
            }

            if(!found){
                if (!duplicateDetector.add(sb.toString())) {
                    found = true;
                    firstDuplicate = sb.toString();
                    System.out.println(counter);
                    counter = 0;
                }
            }
        }
    }

    public void part2(){
        // nothing to do here, printed in part 1
    }

    private int findIndex(int[] numbers){
        int findMax = Integer.MIN_VALUE;
        for (int number : numbers) {
            findMax = Math.max(findMax, number);
        }

        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i] == findMax){
                return i;
            }
        }
        throw new IllegalStateException("no maximum found");
    }
}
