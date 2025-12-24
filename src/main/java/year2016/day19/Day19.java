package year2016.day19;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day19 {
    private final ReadLines reader = new ReadLines(2016, 19, 1);
    private List<String> fileLines; // lines from txt file

    private int numberOfElves;
    private final ArrayList<Integer> elves = new ArrayList<>();

    private void readData(){
        fileLines = reader.readFile();
    }

    private void processData(){
        readData();
        numberOfElves = Integer.parseInt(fileLines.getFirst());
    }

    public void part1(){
        processData();
        int multiplied = 1;
        while(multiplied * 2 <= numberOfElves){
            multiplied *= 2;
        }

        // the remaining elf:
        int result = 2 * (numberOfElves - multiplied) + 1;
        System.out.println(result);
    }

    public void part2(){
        processData();
        fillElves();

        int positionCounter = 0;

        while(elves.size() > 1){

            positionCounter = stealOnePresent(positionCounter);
            if(positionCounter == elves.size()){
                positionCounter = 0;
            }
        }

        System.out.println(elves.getFirst());
    }

    private int stealOnePresent(int currentPosition){
        int n = elves.size();
        int removeThis = (n / 2 + currentPosition) % n;
        elves.remove(removeThis);
        if (removeThis < currentPosition) {
            currentPosition--;
        }
        currentPosition++;
        return currentPosition;
    }

    private void fillElves(){
        elves.clear();
        for (int i = 1; i <= numberOfElves; i++) {
            elves.add(i);
        }
    }
}
