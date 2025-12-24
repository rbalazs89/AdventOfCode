package year2015.day25;

import main.ReadLines;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day25 {
    // tool low 2662772
    // too low 7765756

    List<String> fileLines;
    int inputFileIndex = 2;

    int row = 0;
    int col = 0;
    long start = 20151125;
    long repeating = 16777196; // generated value repeats after this many cycles, previously calculated value

    private final ReadLines reader = new ReadLines(2015, 25);
    int inputNumber = 2; // use 1 for mock data, 2 for real data
    public void readData(){
        // READ INPUT
        fileLines = reader.readFile(inputNumber);
    }

    public void processFile(){
        readData();
        row = Integer.parseInt(fileLines.get(0));
        col = Integer.parseInt(fileLines.get(1));
    }

    public void part1(){
        processFile();
        //checkIfRepeating();
        long index = (long) (row + col - 2) * (row + col - 1) / 2 + col;
        //index = index % repeating;
        long number = start;

        for (long i = 1; i < index; i++) {
            number = generateBasedOnPrevious(number);
        }

        System.out.println(number);

    }

    public void checkIfRepeating(){
        long number = start;
        int counter = 0;
        Set<String> storage = new HashSet<>();
        storage.add(String.valueOf(start));
        for (int i = 0; i < 50000000; i++) {
            number = generateBasedOnPrevious(number);
            if(!storage.add(String.valueOf(number))){
                System.out.println(i);
                storage.clear();
                counter ++;
            }
            if(counter == 3){
                break;
            }
        }
        // 16777196 repeat 1st
    }

    public long generateBasedOnPrevious(long input){
        return (input * 252533) % 33554393;
    }

    public void part2(){
        // no part 2 nothing to do here
    }
}
