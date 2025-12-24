package year2016.day1;

import main.ReadLines;

import java.util.*;

public class Day1 {

    private List<String> fileLines;
    private final ArrayList<String> instructions = new ArrayList<>();

    private final ReadLines reader = new ReadLines(2016, 1, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void processData(){
        readData();
        instructions.clear();
        String line = fileLines.getFirst();
        line = line.replaceAll(" ", "");
        String[] parts = line.split(",");
        instructions.addAll(Arrays.asList(parts));
    }

    public void part1(){
        processData();
        String currentDirection = "up";
        int currentX = 0;
        int currentY = 0;

        for (int i = 0; i < instructions.size(); i++) {
            String currentInstruction = instructions.get(i);

            String turn = currentInstruction.substring(0,1);
            int step = Integer.parseInt(currentInstruction.substring(1));
            currentDirection = changeDirection(currentDirection, turn);

            switch (currentDirection) {
                case "up" -> currentX += step;
                case "right" -> currentY += step;
                case "down" -> currentX -= step;
                case "left" -> currentY -= step;
                default -> System.out.println("problem");
            }
        }
        System.out.println(Math.abs(currentX) + Math.abs(currentY));
    }

    public String changeDirection(String current, String turn){
        switch (current) {
            case "up" -> {
                if (turn.equals("L")) {
                    return "left";
                } else if (turn.equals("R")) {
                    return "right";
                }
            }
            case "right" -> {
                if (turn.equals("L")) {
                    return "up";
                } else if (turn.equals("R")) {
                    return "down";
                }
            }
            case "down" -> {
                if (turn.equals("L")) {
                    return "right";
                } else if (turn.equals("R")) {
                    return "left";
                }
            }
            case "left" -> {
                if (turn.equals("L")) {
                    return "down";
                } else if (turn.equals("R")) {
                    return "up";
                }
            }
        }
        System.out.println("beep");
        return "-1";
    }

    public void part2(){
        processData();
        String currentDirection = "up";
        int currentX = 0;
        int currentY = 0;
        Set<String> mySet = new HashSet<>();
        mySet.add((currentX) + "," + (currentY));

        outerLoop:
        for (int i = 0; i < instructions.size(); i++) {

            String currentInstruction = instructions.get(i);

            String turn = currentInstruction.substring(0,1);
            int step = Integer.parseInt(currentInstruction.substring(1));
            currentDirection = changeDirection(currentDirection, turn);

            for (int j = 0; j < step; j++) {
                switch (currentDirection) {
                    case "up" -> currentX++;
                    case "right" -> currentY++;
                    case "down" -> currentX--;
                    case "left" -> currentY--;
                }
                if(!mySet.add((currentX) + "," + (currentY))){
                    System.out.println(Math.abs(currentX) + Math.abs(currentY));
                    break outerLoop;
                }
            }
        }
    }
}