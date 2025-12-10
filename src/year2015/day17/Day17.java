package year2015.day17;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    List<String> fileLines;
    int[] containers;
    int eggNog = 25; // 25 in sample, 150 in real;
    int part1result = 0;
    ArrayList<Integer> allContainers = new ArrayList<>();

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(1);
    }

    public void processFile(){
        containers = new int[fileLines.size()];
        for (int i = 0; i < fileLines.size(); i++) {
            containers[i] = Integer.parseInt(fileLines.get(i));
            allContainers.add(Integer.parseInt(fileLines.get(i)));
        }
    }

    public void part1() {
        readData();
        processFile();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            part1result += getSum(i, j, k, l, m);
                        }
                    }
                }
            }
        }

        System.out.println(part1result);
    }

    public int getSum(int i, int j, int k, int l, int m){
        if( i * containers[0] + j * containers[1] + k * containers[2] + l * containers[3] + m * containers[4] == eggNog){
            return 1;
        } else {
            return 0;
        }
    }


    public void part2(){

    }
}
