package year2015.day20;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day20 {
    List<String> fileLines = new ArrayList<>();
    int inputFileIndex = 2;


    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }

    public void part1(){
        int limit = 10;
        long[] houses = new long[limit];

        for (int i = 1; i < limit; i++) { // elf
            for (int j = i; j < limit; j += i) { // house
                houses[j] += 10L * i;
            }
        }

        for (int i = 1; i < houses.length; i++) {
            System.out.println(houses[i]);
        }
        long result = 0;
        int counter = 0;
        //36000000
        System.out.println(presentsAtHouse(743224));
    }

    long presentsAtHouse(int n) {
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                sum += i;
            }
        }
        return 10 * sum;
    }

    public void part2(){

    }
}
