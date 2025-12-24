package year2015.day25;

import main.ReadLines;

import java.util.List;

public class Day25 {
    private List<String> fileLines;

    private int row = 0;
    private int col = 0;

    private final ReadLines reader = new ReadLines(2015, 25, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    private void processFile(){
        readData();
        row = Integer.parseInt(fileLines.get(0));
        col = Integer.parseInt(fileLines.get(1));
    }

    public void part1(){
        processFile();
        long index = (long) (row + col - 2) * (row + col - 1) / 2 + col;
        long number = 20151125;

        for (long i = 1; i < index; i++) {
            number = generateBasedOnPrevious(number);
        }

        System.out.println(number);

    }

    public long generateBasedOnPrevious(long input){
        return (input * 252533) % 33554393;
    }

    public void part2(){
        // no part 2 nothing to do here
    }
}
