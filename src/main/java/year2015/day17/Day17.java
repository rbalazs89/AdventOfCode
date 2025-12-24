package year2015.day17;

import main.ReadLines;
import java.util.List;

public class Day17 {
    private List<String> fileLines;
    private int[] containers;
    private final int eggNog = 150; // 25 in sample, 150 in real;
    private int combinations = 0;

    private final ReadLines reader = new ReadLines(2015, 17, 2);
    public void readData(){
        fileLines = reader.readFile();
    }

    private void processFile(){
        containers = new int[fileLines.size()];
        for (int i = 0; i < fileLines.size(); i++) {
            containers[i] = Integer.parseInt(fileLines.get(i));
        }
    }

    public void part1() {
        readData();
        processFile();

        findCombinations(0, 0);

        System.out.println(combinations);
    }

    public void part2(){
        combinations = 0;
        readData();
        processFile();

        findCombinations2(0, 0, 0);

        System.out.println(combinations);
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
            int minContainers = 3;
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
}
