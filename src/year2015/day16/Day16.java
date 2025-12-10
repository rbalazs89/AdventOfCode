package year2015.day16;

import main.ReadLines;

import java.util.Arrays;
import java.util.List;

public class Day16 {
    int[] trace = new int[10];
    int[][] aunts = new int[10][500];

    /**
    children: 3
    cats: 7
    samoyeds: 2
    pomeranians: 3
    akitas: 0
    vizslas: 0
    goldfish: 5
    trees: 3
    cars: 2
    perfumes: 1 */
    String[] enums;
    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void processData(){
        trace[0] = 3;
        trace[1] = 7;
        trace[2] = 2;
        trace[3] = 3;
        trace[4] = 0;
        trace[5] = 0;
        trace[6] = 5;
        trace[7] = 3;
        trace[8] = 2;
        trace[9] = 1 ;
        for (int i = 0; i < aunts.length; i++) {
            for (int j = 0; j < aunts[0].length; j++) {
                aunts[i][j] = -1;
            }
        }

        enums = new String[]{
                "children",
                "cats",
                "samoyeds",
                "pomeranians",
                "akitas",
                "vizslas",
                "goldfish",
                "trees",
                "cars",
                "perfumes"
        };

        for (int i = 0; i < 500; i++) {
            String line = fileLines.get(i);
            String[] parts = line.split(" ");
            String item1 = parts[2].substring(0, parts[2].length() - 1);
            String item2 = parts[4].substring(0, parts[4].length() - 1);
            String item3 = parts[6].substring(0, parts[6].length() - 1);

            int value1 = Integer.parseInt(parts[3].substring(0, parts[3].length() - 1));
            int value2 = Integer.parseInt(parts[5].substring(0, parts[5].length() - 1));
            int value3 = Integer.parseInt(parts[7]);

            for (int j = 0; j < 10; j++) {
                if(item1.equals(enums[j]) && aunts[j][i] == -1){
                    aunts[j][i] = value1;
                }
                if(item2.equals(enums[j]) && aunts[j][i] == -1){
                    aunts[j][i] = value2;
                }

                if(item3.equals(enums[j]) && aunts[j][i] == -1){
                    aunts[j][i] = value3;
                }
            }
        }
    }

    public void part1(){
        readData();
        processData();
        for (int i = 0; i < aunts[0].length; i++) {
            int points = 0;
            for (int j = 0; j < 10; j++) {
                if (aunts[j][i] == trace[j]) {
                    points++;
                }
            }
            if(points == 3){
                System.out.println("part1: " + (i + 1));
            }
        }
    }

    public void part2(){
        //cats and trees greater than ( 1 , 7)
        //pomeranians and goldfish fewer than 3, 6
        readData();
        processData();
        for (int i = 0; i < aunts[0].length; i++) {
            int points = 0;
            for (int j = 0; j < 10; j++) {
                if(j == 0 || j == 2 || j == 4 || j == 5 || j == 8 || j == 9){
                    if (aunts[j][i] == trace[j]) {
                        points ++;
                    }
                }
                if(j == 1 || j == 7){
                    if(aunts[j][i] > trace[j]){
                        points ++;
                    }
                }
                if(j == 3 || j == 6){
                    if(aunts[j][i] < trace[j] && aunts[j][i] != -1){
                        points ++;
                    }
                }
            }
            if(points == 3){
                System.out.println("part2: " + (i + 1));
            }
        }
    }
}
