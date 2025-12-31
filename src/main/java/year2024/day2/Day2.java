package year2024.day2;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day2 {
    private final ReadLines reader = new ReadLines(2024, 2, 2);
    private List<List<Integer>> getReports(){
        List<String> lines = reader.readFile();

        ArrayList<List<Integer>> inputs = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String[] oneLine = lines.get(i).split(" ");
            ArrayList<Integer> input1line = new ArrayList<>();
            for (String s : oneLine) {
                input1line.add(Integer.valueOf(s));
            }
            inputs.add(input1line);
        }
        return inputs;
    }

    public void part1(){
        List<List<Integer>> reports = getReports();
        int counter = 0;

        outerLoop:
        for(int i = 0; i < reports.size(); i ++){
            boolean isSafe = false;
            boolean isIncreasing = false;

            if (reports.get(i).get(0) - reports.get(i).get(1) == 0){
                continue;
            }

            if (reports.get(i).get(0) - reports.get(i).get(1) < 0){
                isIncreasing = true;
            }

            for (int j = 0; j < reports.get(i).size() - 1; j++) {
                int temp = reports.get(i).get(j) - reports.get(i).get(j + 1);
                if (isIncreasing) {
                    if (temp == -1 || temp == -2 || temp == -3) {
                        isSafe = true;
                    } else {
                        continue outerLoop;
                    }
                } else {
                    if (temp == 1 || temp == 2 || temp == 3) {
                        isSafe = true;
                    } else {
                        continue outerLoop;
                    }
                }
            }
            if(isSafe){
                counter ++;
            }
        }
        System.out.println(counter);
    }


    public void part2(){
        List<List<Integer>> reports = getReports();

        // get the numbers that are safe without modification:
        Set<Integer> safeNumbers = getIntegers(reports);

        //solution:
        int counter = 0;

        outerLoop:
        for(int i = 0; i < reports.size(); i ++){
            if(safeNumbers.contains(i)){
                counter ++;
                continue ;
            }

            middleLoop:
            for (int k = 0; k < reports.get(i).size(); k++) {
                boolean isSafe = false;
                boolean isIncreasing = false;
                List<Integer> copiedList = new ArrayList<>(reports.get(i));
                reports.get(i).remove(k);

                if (reports.get(i).get(0) - reports.get(i).get(1) == 0){
                    reports.remove(i);
                    reports.add(i, copiedList);
                    continue;
                }

                if (reports.get(i).get(0) - reports.get(i).get(1) < 0){
                    isIncreasing = true;
                }

                for (int j = 0; j < reports.get(i).size() - 1; j++) {
                    int temp = reports.get(i).get(j) - reports.get(i).get(j + 1);
                    if (isIncreasing) {
                        if (temp == -1 || temp == -2 || temp == -3) {
                            isSafe = true;
                        } else {
                            reports.remove(i);
                            reports.add(i, copiedList);
                            continue middleLoop;
                        }
                    } else {
                        if (temp == 1 || temp == 2 || temp == 3) {
                            isSafe = true;
                        } else {
                            reports.remove(i);
                            reports.add(i, copiedList);
                            continue middleLoop;
                        }
                    }
                }
                if(isSafe){
                    counter ++;
                    continue outerLoop;
                }
            }
        }
        System.out.println(counter);
    }

    private static Set<Integer> getIntegers(List<List<Integer>> reports) {
        Set<Integer> safeNumbers = new HashSet<>();

        outerLoop:
        for(int i = 0; i < reports.size(); i ++){
            boolean isSafe = false;
            boolean isIncreasing = false;

            if (reports.get(i).get(0) - reports.get(i).get(1) == 0){
                continue;
            }

            if (reports.get(i).get(0) - reports.get(i).get(1) < 0){
                isIncreasing = true;
            }

            for (int j = 0; j < reports.get(i).size() - 1; j++) {
                int temp = reports.get(i).get(j) - reports.get(i).get(j + 1);
                if (isIncreasing) {
                    if (temp == -1 || temp == -2 || temp == -3) {
                        isSafe = true;
                    } else {
                        continue outerLoop;
                    }
                } else {
                    if (temp == 1 || temp == 2 || temp == 3) {
                        isSafe = true;
                    } else {
                        continue outerLoop;
                    }
                }
            }
            if(isSafe){
                safeNumbers.add(i);
            }
        }
        return safeNumbers;
    }
}