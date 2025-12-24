package year2015.day20;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day20 {
    private List<String> fileLines = new ArrayList<>();
    private final ReadLines reader = new ReadLines(2015, 20, 1);

    public void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();
        int target = Integer.parseInt(fileLines.getFirst());
        int limit = 1000000;

        long[] houses = new long[limit];

        for (int i = 1; i < limit; i++) {  // elf
            for (int j = i; j < limit; j += i) { // house
                houses[j] += 10L * i;
            }
        }

        for (int i = 1; i < limit; i++) { // elf
            if (houses[i] >= target) { // house
                System.out.println(i);
                return;
            }
        }
    }

    public void part2(){
        readData();
        int target = Integer.parseInt(fileLines.getFirst());
        int limit = 10000000;

        long[] houses = new long[limit];

        for (int i = 1; i < limit; i++) {  // elf
            int counter = 0;
            for (int j = i; j < limit; j += i) {
                counter ++;
                houses[j] += 11L * i;
                if(counter >= 50){
                    break;
                }
            }
        }

        for (int i = 1; i < limit; i++) {
            if (houses[i] >= target) {
                System.out.println(i);
                return;
            }
        }
    }
}
