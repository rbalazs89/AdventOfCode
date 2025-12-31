package year2024.day6;

import main.ReadLines;

import java.util.Arrays;
import java.util.List;

public class Day6 {
    private final ReadLines reader = new ReadLines(2024, 6, 2);
    private int[][] table;
    private int[][] table2;
    private int[] currentPos = new int[3];
    private int[] currentPos2 = new int[3];

    private void prepare(){
        List<String> lines = reader.readFile();

        table = new int[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == '#') {
                    table[i][j] = 1;
                } else if (lines.get(i).charAt(j) == '.') {
                    table[i][j] = 0;
                } else if (lines.get(i).charAt(j) == '^') {
                    currentPos[2] = 2;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                } else if (lines.get(i).charAt(j) == '>') {
                    currentPos[2] = 3;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                } else if (lines.get(i).charAt(j) == 'v') {
                    currentPos[2] = 4;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                } else if (lines.get(i).charAt(j) == '<') {
                    currentPos[2] = 5;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                }
            }
        }
        table2 = deepCopy(table);
        currentPos2 = Arrays.copyOf(currentPos, currentPos.length);
    }

    public void part1(){
        prepare();
        int maxY = table.length;
        int maxX = table[0].length;
        // currentPos[0] -> y
        // currentPos[1] -> x
        // currentPos[2] -> direction

        int[] step = new int[2];
        while(currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX ){
            //change table to visited table:
            if(!(table[currentPos[0]][currentPos[1]] == 1)){
                table[currentPos[0]][currentPos[1]] = -1;
            }

            //step:
            currentPos[0] = currentPos[0] + step[0];
            currentPos[1] = currentPos[1] + step[1];

            if(!(currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX)){
                break;
            }

            //decide next step:
            int tempInt = table[currentPos[0]][currentPos[1]];
            if(tempInt == 1){
                if(currentPos[2] == 2){
                    currentPos[2] = 3; // continue right
                    step[0] = 1;
                    step[1] = 1; //
                } else if (currentPos[2] == 3){
                    currentPos[2] = 4; // continue down
                    step[0] = 1; //
                    step[1] = -1;
                } else if (currentPos[2] == 4){
                    currentPos[2] = 5; // continue left
                    step[0] = -1;
                    step[1] = -1; //
                } else if (currentPos[2] == 5){
                    currentPos[2] = 2; // continue up
                    step[0] = -1; //
                    step[1] = 1;
                }
            } else {
                if(currentPos[2] == 3){
                    step[0] = 0;
                    step[1] = 1;
                } else if(currentPos[2] == 4){
                    step[0] = 1;
                    step[1] = 0;
                } else if(currentPos[2] == 5){
                    step[0] = 0;
                    step[1] = -1;
                } else if(currentPos[2] == 2){
                    step[0] = -1;
                    step[1] = 0;
                }
            }
        }

        int counter = 0;
        for (int[] ints : table) {
            for (int j = 0; j < table[0].length; j++) {
                if (ints[j] == -1) {
                    counter++;
                }
            }
        }
        System.out.println(counter);
    }

    public void part2(){
        prepare();
        int maxY = table.length;
        int maxX = table[0].length;
        // currentPos[0] -> y
        // currentPos[1] -> x
        // currentPos[2] -> direction

        int[] step = new int[2];
        int counter = 0;
        int result = 0;

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[1].length; j++) {
                if ((table[i][j] == 0)){
                    table[i][j] = 1;
                    while (currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX) {
                        counter++;
                        if (counter > 10000000) {
                            result++;
                            counter = 0;
                            resetTableAndPos(-1,-1);
                            step[0] = -1;
                            step[1] = 0;
                            break;
                        }
                        //change table to visited table:
                        if (!(table[currentPos[0]][currentPos[1]] == 1)) {
                            table[currentPos[0]][currentPos[1]] = -1;
                        }

                        //step:
                        currentPos[0] = currentPos[0] + step[0];
                        currentPos[1] = currentPos[1] + step[1];

                        if (!(currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX)) {
                            resetTableAndPos(-1,-1);
                            step[0] = -1;
                            step[1] = 0;
                            break;
                        }

                        //decide next step:
                        int tempInt = table[currentPos[0]][currentPos[1]];
                        if (tempInt == 1) {
                            if (currentPos[2] == 2) {
                                currentPos[2] = 3; // step back and continue right
                                step[0] = 1;
                                step[1] = 1; //
                            } else if (currentPos[2] == 3) {
                                currentPos[2] = 4; // step back and continue down
                                step[0] = 1; //
                                step[1] = -1;
                            } else if (currentPos[2] == 4) {
                                currentPos[2] = 5; // step back and continue left
                                step[0] = -1;
                                step[1] = -1; //
                            } else if (currentPos[2] == 5) {
                                currentPos[2] = 2; // step back and continue up
                                step[0] = -1; //
                                step[1] = 1;
                            }
                        } else {
                            if (currentPos[2] == 3) {
                                step[0] = 0;
                                step[1] = 1;
                            } else if (currentPos[2] == 4) {
                                step[0] = 1;
                                step[1] = 0;
                            } else if (currentPos[2] == 5) {
                                step[0] = 0;
                                step[1] = -1;
                            } else if (currentPos[2] == 2) {
                                step[0] = -1;
                                step[1] = 0;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    /**
     * DEBUG METHOD
     */

    private void printoutTable(){
        for (int i = 0; i < table[0].length; i++) {
            for (int j = 0; j < table.length; j++) {
                System.out.print(table[i][j] + "  ");
                if(!(table[i][j] == -1)){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private void resetTableAndPos(int wallY, int wallX) {
        // Deep copy the original table
        for (int i = 0; i < table2.length; i++) {
            for (int j = 0; j < table2[i].length; j++) {
                // Restore original values except for the temporary wall
                table[i][j] = (i == wallY && j == wallX) ? 1 : table2[i][j];
            }
        }

        // Reset position
        currentPos = Arrays.copyOf(currentPos2, currentPos2.length);
    }
}
