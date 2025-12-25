package year2017.day17;

import main.ReadLines;

import java.util.*;

public class Day17 {
    private final ReadLines reader = new ReadLines(2017, 17, 2);
    private List<String> fileLines; // Lines from txt file

    // specified by task description:
    private static final int PUZZLE_YEAR = 2017;
    private static final int PART_TWO_VALUE = 50000000;

    private void readData() {
        fileLines = reader.readFile();
    }

    private int getNumberOfSteps(){
        readData();
        return Integer.parseInt(fileLines.getFirst());
    }

    public void part1(){
        int steps = getNumberOfSteps();

        ArrayList<Integer> buffer = new ArrayList<>();
        int currentPosition = 0;
        buffer.add(0); // adding first element

        // run specified algorithm
        for (int i = 1; i <= PUZZLE_YEAR; i++) {
            currentPosition = (currentPosition + steps) % buffer.size();
            int insertPos = currentPosition + 1;
            buffer.add(insertPos, (i));
            currentPosition = insertPos;
        }

        // get the required element
        for (int i = 0; i < buffer.size(); i++) {
            if(buffer.get(i) == PUZZLE_YEAR){
                if(i + 1 >= buffer.size()){
                    throw new IllegalStateException("Puzzle is asking for the element after the last element");
                }
                System.out.println(buffer.get(i + 1));
                break;
            }
        }
    }

    public void part2(){
        int steps = getNumberOfSteps();
        int currentPosition = 0;
        int firstElement = 0;

        // only need to keep track of the element coming after 0, because 0 will always be the firts element:
        for (int i = 1; i <= PART_TWO_VALUE; i++) {
            currentPosition = (currentPosition + steps) % i;
            if(currentPosition == 0){
                firstElement = i;
            }
            currentPosition ++;
        }
        System.out.println(firstElement);
    }
}
