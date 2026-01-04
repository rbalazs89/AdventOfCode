package year2023.day14;

import main.ReadLines;

import java.util.Arrays;
import java.util.List;

public class Day14 {
    private final ReadLines reader = new ReadLines(2023, 14, 2);
    private int width;
    private int length;
    private int[][] table;

    private void prepare(){
        List<String> input = reader.readFile();
        width = input.getFirst().length();
        length = input.size();
        table = new int[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(input.get(j).charAt(i) == 'O'){
                    table[i][j] = 1;
                } else if(input.get(j).charAt(i) == '#'){
                    table[i][j] = 2;
                }
            }
        }
    }

    public void part1(){
        prepare();
        rollUp2();
        System.out.println(calculateLoad());
    }

    public void part2(){
        // Start rotation to get the repeating cycle and to see how many times in needs to be repeated
        prepare();
        for (int i = 0; i < 100; i++) {
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
        }

        int[][] table2 = new int[table.length][];
        for (int i = 0; i < table.length; i++) {
            table2[i] = table[i].clone();
        }

        int period = 0;
        int repeatThisManyTimes;

        for (int i = 100; i < 1000; i++) {
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
            if (Arrays.deepEquals(table, table2)) {
                period = i - 99;
                break;
            }
        }
        repeatThisManyTimes = 999999900 / period;
        repeatThisManyTimes = repeatThisManyTimes - 1;


        //calculate the final state:
        prepare();
        for (int i = 0; i < 100; i++) {
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
        }
        boolean addThisOnce = false;
        for (int i = 100; i < 1000000000; i++) {
            if(!addThisOnce){
                i = i + period * repeatThisManyTimes;
                addThisOnce = true;
            }
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
        }
        System.out.println(calculateLoad());
    }

    private void rollUp2(){
        for (int i = 0; i < width; i++) {
            for (int j = 1; j < length; j++) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = j - 1; k >= 0; k--) {
                        if (table[i][k] > 0) {
                            table[i][j] = 0;
                            table[i][k+1] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[i][0] = 1;
                    }
                }
            }
        }
    }

    private void rollDown2(){
        for (int i = 0; i < width; i++) {
            for (int j = length - 1; j >= 0; j--) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = j + 1; k < length; k++) {
                        if (table[i][k] > 0) {
                            table[i][j] = 0;
                            table[i][k - 1] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[i][length - 1] = 1;
                    }
                }
            }
        }
    }

    private void rollLeft2(){
        for (int i = 1; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = i - 1; k >= 0; k--) {
                        if (table[k][j] > 0) {
                            table[i][j] = 0;
                            table[k + 1][j] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[0][j] = 1;
                    }
                }
            }
        }
    }

    private void rollRight2(){
        for (int i = width - 2; i >= 0; i--) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = i + 1; k < width; k++) {
                        if (table[k][j] > 0) {
                            table[i][j] = 0;
                            table[k - 1][j] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[width - 1][j] = 1;
                    }
                }
            }
        }
    }

    private int calculateLoad(){
        int load = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == 1){
                    load += length - j;
                }
            }
        }
        return load;
    }
}