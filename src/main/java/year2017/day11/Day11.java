package year2017.day11;

import main.ReadLines;

import java.util.*;

public class Day11 {
    // setup:
    private final ReadLines reader = new ReadLines(2017, 11, 2);
    private List<String> fileLines; // lines from txt file
    private static final Set<String> possibleDirections = Set.of("n", "ne", "se", "s", "sw", "nw");

    // puzzle:
    private String[] directions; // read only after prepare

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        directions = fileLines.getFirst().split(",");
        // check:
        for (String direction : directions) {
            if (!possibleDirections.contains(direction)) {
                throw new IllegalStateException("direction not recognized");
            }
        }
    }


    public void part1(){
        prepare();

        //get frequency map based on directions:
        HashMap<String, Integer> frequency = buildDirectionMap(directions);
        System.out.println(getRequiredSteps(frequency));
    }

    public void part2(){
        prepare();

        // investigate after each step, instead of letting it finish
        int maxValue = 0;
        HashMap<String, Integer> frequency = new HashMap<>();
        for (String current : directions) {
            if (frequency.containsKey(current)) {
                frequency.put(current, frequency.get(current) + 1);
            } else {
                frequency.put(current, 1);
            }
            maxValue = Math.max(maxValue, getRequiredSteps(frequency));
        }

        // print final result
        System.out.println(maxValue);
    }

    private int getRequiredSteps(HashMap<String, Integer> frequencyMap){
        // directions cancel each other out in a hexagon style map, just like left and right would in a real map
        int top = 0;
        int topLeft = 0;
        int topRight = 0;
        int bottom = 0;
        int bottomLeft = 0;
        int bottomRight = 0;

        if(frequencyMap.containsKey("n")){
            top = frequencyMap.get("n");
        }
        if(frequencyMap.containsKey("nw")){
            topLeft = frequencyMap.get("nw");
        }
        if(frequencyMap.containsKey("ne")){
            topRight = frequencyMap.get("ne");
        }
        if(frequencyMap.containsKey("s")){
            bottom = frequencyMap.get("s");
        }
        if(frequencyMap.containsKey("sw")){
            bottomLeft = frequencyMap.get("sw");
        }
        if(frequencyMap.containsKey("se")){
            bottomRight = frequencyMap.get("se");
        }

        int directionOne = top - bottom;
        int directionTwo = topLeft - bottomRight;
        int directionThree = topRight - bottomLeft;

        // ignore the smallest, because any two of these 3 directions can be replaced by one direction
        int[] combiningArray = {Math.abs(directionOne), Math.abs(directionTwo), Math.abs(directionThree)};
        Arrays.sort(combiningArray);
        return (combiningArray[1] + combiningArray[2]);
    }

    private HashMap<String, Integer> buildDirectionMap(String[] directionArray){
        HashMap<String, Integer> frequency = new HashMap<>();
        for (String current : directionArray) {
            if (frequency.containsKey(current)) {
                frequency.put(current, frequency.get(current) + 1);
            } else {
                frequency.put(current, 1);
            }
        }
        return  frequency;
    }
}
