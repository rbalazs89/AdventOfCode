package year2015.day17;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    List<String> fileLines;
    int[] containers;
    int eggNog = 150; // 25 in sample, 150 in real;
    int endResult = 0;
    ArrayList<Integer> allContainers = new ArrayList<>();
    int combinations = 0;
    int minContainers = 3;

    private final ReadLines reader = new ReadLines(2015, 17);
    int inputNumber = 2; // use 1 for mock data, 2 for real data
    public void readData(){
        // READ INPUT
        fileLines = reader.readFile(inputNumber);
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

        findCombinations(0, 0);

        System.out.println("part1: " + combinations);
    }

    public void part2(){
        combinations = 0;
        readData();
        processFile();

        findCombinations2(0, 0, 0);

        System.out.println("part2: " + combinations);
    }
    private void findCombinations2(int index, int currentSum, int currentContainers) {
        // if reached eggnog
        if (index == containers.length) {
            // dont go further if reached last container + 1
            return;
        }

        // branch path 1 -> skip current and go next
        findCombinations2(index + 1, currentSum, currentContainers);

        // branching path 2 -> include current and see then go next
        if (currentSum + containers[index] <= eggNog) {
            currentSum = currentSum + containers[index];
            if(currentSum == eggNog && currentContainers == minContainers){
                combinations++;
            }
            else {
                findCombinations2(index + 1, currentSum, currentContainers + 1);
            }
        }
    }

    private void findCombinations(int index, int currentSum) {
        // if reached eggnog
        if (index == containers.length) {
            // dont go further if reached last container + 1
            return;
        }

        // branch path 1 -> skip current and go next
        findCombinations(index + 1, currentSum);

        // branching path 2 -> include current and see then go next
        if (currentSum + containers[index] <= eggNog) {
            currentSum = currentSum + containers[index];
            if(currentSum == eggNog){
                combinations++;
            }
            else {
                findCombinations(index + 1, currentSum);
            }
        }
    }

    // for sample only
    public void part1SampleOnly() {
        readData();
        processFile();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            endResult += getSum(i, j, k, l, m);
                        }
                    }
                }
            }
        }

        System.out.println(endResult);
    }

    public int getSum(int i, int j, int k, int l, int m){
        if( i * containers[0] + j * containers[1] + k * containers[2] + l * containers[3] + m * containers[4] == eggNog){
            return 1;
        } else {
            return 0;
        }
    }
}
