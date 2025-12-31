package year2024.day7;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 {
    private final ReadLines reader = new ReadLines(2024, 7, 2);
    private List<List<String>> getOperations() {
        List<String> lines = reader.readFile();
        List<List<String>> input = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(":");
            ArrayList<String> row = new ArrayList<>();
            row.add(parts[0].trim());
            String[] values = parts[1].trim().split("\\s+");
            row.addAll(Arrays.asList(values));
            input.add(row);
        }
        return input;
    }

    public void part1(){
        List<List<String>> operations = getOperations();
        Long result = 0L;
        for (int i = 0; i < operations.size(); i++) {
            result = result + processOneElementPart1(operations.get(i));
        }
        System.out.println(result);
    }

    public void part2(){
        List<List<String>> operations = getOperations();
        Long result = 0L;
        for (int i = 0; i < operations.size(); i++) {
            result = result + processOneElementPart2(operations.get(i));
        }
        System.out.println(result);
    }

    private static Long processOneElementPart1(List<String> oneLineInput){
        int numCombinations = (int) Math.pow(2, oneLineInput.size() - 2);
        String[] combinations = new String[numCombinations];
        String item1 = "+";
        String item2 = "*";

        // Generate combinations
        for (int i = 0; i < numCombinations; i++) {
            StringBuilder combination = new StringBuilder();
            for (int j = 0; j < oneLineInput.size() - 2; j++) {
                int bitValue = (int) Math.pow(2, j);
                int check = i / bitValue;
                if (check % 2 == 0) {
                    combination.append(item1);
                } else {
                    combination.append(item2);
                }
            }
            combinations[i] = combination.toString();
        }

        // generate number from combinations:
        for (String combination : combinations) {
            long tempResult = Long.parseLong(oneLineInput.get(1));
            for (int j = 0; j < combination.length(); j++) {
                if (combination.charAt(j) == '+') {
                    tempResult = tempResult + Long.parseLong(oneLineInput.get(j + 2));
                } else {
                    tempResult = tempResult * Long.parseLong(oneLineInput.get(j + 2));
                }
            }
            if (tempResult == Long.parseLong(oneLineInput.get(0))) {
                return tempResult;
            }
        }
        return 0L;
    }

    private static Long processOneElementPart2(List<String> oneLineInput){
        int numCombinations = (int) Math.pow(3, oneLineInput.size() - 2);
        String[] combinations = new String[numCombinations];
        String item1 = "+";
        String item2 = "*";
        String item3 = "|";

        // Generate combinations
        for (int i = 0; i < numCombinations; i++) {
            StringBuilder combination = new StringBuilder();
            int value = i; // Use a temporary variable for base-3 conversion
            for (int j = 0; j < oneLineInput.size() - 2; j++) {
                int remainder = value % 3; // Get the current base-3 digit
                value /= 3; // Move to the next base-3 digit

                if (remainder == 0) {
                    combination.append(item1);
                } else if (remainder == 1) {
                    combination.append(item2);
                } else {
                    combination.append(item3);
                }
            }
            combinations[i] = combination.toString();
        }

        // generate number from combinations:
        for(int i = 0; i < combinations.length; i ++){
            long tempResult = getALong(oneLineInput, combinations, i);
            if(tempResult == Long.parseLong(oneLineInput.getFirst())){
                return tempResult;
            }
        }
        return 0L;
    }

    private static long getALong(List<String> oneLineInput, String[] combinations, int i) {
        long tempResult = Long.parseLong(oneLineInput.get(1));
        for (int j = 0; j < combinations[i].length(); j++) {
            if(combinations[i].charAt(j) == '+'){
                tempResult = tempResult + Long.parseLong(oneLineInput.get(j + 2));
            } else if (combinations[i].charAt(j) == '*'){
                tempResult = tempResult * Long.parseLong(oneLineInput.get(j + 2));
            } else if (combinations[i].charAt(j) == '|'){
                tempResult = Long.parseLong((tempResult)  + oneLineInput.get(j + 2));
            }
        }
        return tempResult;
    }
}
