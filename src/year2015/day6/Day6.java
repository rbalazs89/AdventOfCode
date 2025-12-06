package year2015.day6;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day6 {

    List<String> fileLines;
    List<Instruction> instructions = new ArrayList<>();
    int[][] grid = new int[1000][1000];

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void createInstructionsFromInput(){
        for (int i = 0; i < fileLines.size(); i++) {
            int type;
            int[] start = new int[2];
            int[] end = new int[2];

            if(fileLines.get(i).startsWith("turn on")){
                type = 1;
            } else if (fileLines.get(i).startsWith("turn off")){
                type = 2;
            } else {
                type = 3;
            }

            String line = fileLines.get(i);
            line = line.replace("turn on ", "")
                    .replace("turn off ", "")
                    .replace("toggle ", "");

            String[] parts = line.split(" through ");

            String[] startString = parts[0].split(",");
            String[] endString = parts[1].split(",");

            start[0] = Integer.parseInt(startString[0]);
            start[1] = Integer.parseInt(startString[1]);

            end[0] = Integer.parseInt(endString[0]);
            end[1] = Integer.parseInt(endString[1]);

            instructions.add(new Instruction(type, start, end));
        }
    }

    public void part1(){
        readData();
        createInstructionsFromInput();

        for (int i = 0; i < instructions.size(); i++) {
            applyOneInstruction(instructions.get(i));
        }

        int result = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if(grid[i][j] == 1){
                    result++;
                }
            }
        }

        System.out.println(result);
    }

    void applyOneInstruction(Instruction instruction){
        if(instruction.type == 1){
            for (int i = instruction.start[0]; i <= instruction.end[0]; i++) {
                for (int j = instruction.start[1]; j <= instruction.end[1]; j++) {
                    grid[i][j] = 1;
                }
            }
        }

        if(instruction.type == 2){
            for (int i = instruction.start[0]; i <= instruction.end[0]; i++) {
                for (int j = instruction.start[1]; j <= instruction.end[1]; j++) {
                    grid[i][j] = 0;
                }
            }
        }

        if(instruction.type == 3){
            for (int i = instruction.start[0]; i <= instruction.end[0]; i++) {
                for (int j = instruction.start[1]; j <= instruction.end[1]; j++) {
                    if(grid[i][j] == 0){
                        grid[i][j] = 1;
                    } else {
                        grid[i][j] = 0;
                    }
                }
            }
        }
    }

    void applyOneInstructionPartTwo(Instruction instruction){
        if(instruction.type == 1){
            for (int i = instruction.start[0]; i <= instruction.end[0]; i++) {
                for (int j = instruction.start[1]; j <= instruction.end[1]; j++) {
                    grid[i][j] += 1;
                }
            }
        }

        if(instruction.type == 2){
            for (int i = instruction.start[0]; i <= instruction.end[0]; i++) {
                for (int j = instruction.start[1]; j <= instruction.end[1]; j++) {
                    grid[i][j] -= 1;
                    grid[i][j] = Math.max(grid[i][j], 0);
                }
            }
        }

        if(instruction.type == 3){
            for (int i = instruction.start[0]; i <= instruction.end[0]; i++) {
                for (int j = instruction.start[1]; j <= instruction.end[1]; j++) {
                    grid[i][j] += 2;
                }
            }
        }
    }

    public void part2(){
        grid = new int[1000][1000];

        for (int i = 0; i < instructions.size(); i++) {
            applyOneInstructionPartTwo(instructions.get(i));
        }

        int result = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                result = result + grid[i][j];
            }
        }

        System.out.println(result);
    }
}