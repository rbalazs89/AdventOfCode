package year2015.day24;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day24 {
    private List<String> fileLines;
    private int[] packages;
    private int estimatedMax = 0; // maxNumber * 1/3
    private int estimatedMaxPart2 = 0;
    private int fullSum = 0;
    private final ArrayList<boolean[]> possibleSolutions = new ArrayList<>();


    private final ReadLines reader = new ReadLines(2015, 24, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    private void processData(){
        readData();
        packages = new int[fileLines.size()];
        estimatedMax = fileLines.size() / 3;
        estimatedMaxPart2 = fileLines.size() / 4;
        for (int i = 0; i < fileLines.size(); i++) {
            packages[i] = Integer.parseInt(fileLines.get(i));
            fullSum += packages[i];
        }
    }

    public void part1(){
        processData();
        recursive(new boolean[packages.length], 0);

        int minPackages = Integer.MAX_VALUE;
        long minQE = Long.MAX_VALUE;
        for (int i = 0; i < possibleSolutions.size(); i++) {
            int currentPackages = 0;
            long currentQE = 1;
            boolean[] current = possibleSolutions.get(i);
            for (int j = 0; j < current.length; j++) {
                if(current[j]){
                    currentPackages ++;
                    currentQE = currentQE * packages[j];
                }
            }
            if(currentPackages <= minPackages){
                minPackages = currentPackages;
                if(currentQE < minQE){
                    minQE = currentQE;
                }
            }
        }
        System.out.println(minQE);
    }

    private void recursive(boolean[] combination, int index){
        if(isItTooHigh(combination)){
            return;
        }
        if(index == combination.length){
            validateAndAdd(combination);
            return;
        }

        recursive(combination, index + 1);

        combination[index] = true;
        recursive(combination, index + 1);
        combination[index] = false;
    }

    private boolean isItTooHigh(boolean[] combination){
        int points = 0;
        for (boolean b : combination) {
            if (b) {
                points++;
            }
        }
        return points > estimatedMax;
    }

    public void validateAndAdd(boolean[] combination){
        int currentSum = 0;
        for (int i = 0; i < combination.length; i++) {
            if(combination[i]){
                currentSum = currentSum + packages[i];
            }
        }
        if(currentSum == fullSum / 3){
            possibleSolutions.add(combination.clone());
        }
    }

    public void part2(){
        possibleSolutions.clear();
        recursive2(new boolean[packages.length], 0);

        int minPackages = Integer.MAX_VALUE;
        long minQE = Long.MAX_VALUE;
        for (int i = 0; i < possibleSolutions.size(); i++) {
            int currentPackages = 0;
            long currentQE = 1;
            boolean[] current = possibleSolutions.get(i);
            for (int j = 0; j < current.length; j++) {
                if(current[j]){
                    currentPackages ++;
                    currentQE = currentQE * packages[j];
                }
            }
            if(currentPackages <= minPackages){
                minPackages = currentPackages;
                if(currentQE < minQE){
                    minQE = currentQE;
                }
            }
        }
        System.out.println(minQE);

    }

    private void recursive2(boolean[] combination, int index){
        if(isItTooHigh2(combination)){
            return;
        }
        if(index == combination.length){
            validateAndAdd2(combination);
            return;
        }

        recursive2(combination, index + 1);

        combination[index] = true;
        recursive2(combination, index + 1);
        combination[index] = false;
    }

    private boolean isItTooHigh2(boolean[] combination){
        int points = 0;
        for (boolean b : combination) {
            if (b) {
                points++;
            }
        }
        return points > estimatedMaxPart2;
    }

    private void validateAndAdd2(boolean[] combination){
        int currentSum = 0;
        for (int i = 0; i < combination.length; i++) {
            if(combination[i]){
                currentSum = currentSum + packages[i];
            }
        }
        if(currentSum == fullSum / 4){
            possibleSolutions.add(combination.clone());
        }
    }
}
