package year2016.day8;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {
    private List<String> fileLines;
    private final char[][] grid = new char[50][50]; // grid[x][y]
    private final char[][] copiedGrid = new char[50][50];
    private final ArrayList<Instruction> instructions = new ArrayList<>();

    private final ReadLines reader = new ReadLines(2016, 8, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    private void processData(){
        readData();
        fillGrid();
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Instruction instruction = new Instruction();
            ArrayList<Integer> numbers = new ArrayList<>();
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(line);

            while(m.find()){
                numbers.add(Integer.parseInt(m.group()));
            }

            if(line.contains("rect")){
                instruction.typeRect = true;
                instruction.rectX = numbers.get(0);
                instruction.rectY = numbers.get(1);
            } else {
                instruction.typeRect = false;
                if(line.contains("row")){
                    instruction.y = numbers.get(0);
                }
                if(line.contains("column")){
                    instruction.x = numbers.get(0);
                }
                instruction.value = numbers.get(1);
            }
            instructions.add(instruction);
        }
    }

    public void part1(){
        processData();
        for (int i = 0; i < instructions.size(); i++) {
            applyInstruction(instructions.get(i));
            copyGrid();
        }
        countLit();
        draw();
    }

    private void applyInstruction(Instruction ins){

        // type 1
        if(ins.typeRect){
            for (int i = 0; i < 50; i++) { // y
                for (int j = 0; j < 50; j++) { // x
                    if(i < ins.rectY && j < ins.rectX){
                        grid[j][i] = '#';
                    }
                }
            }
        }
        // type 2:
        else {
            //shift row
            if(ins.y != -1){
                for (int j = 0; j < 50; j++) {
                    char c = copiedGrid[j][ins.y];
                    grid[(j + ins.value) % 50][ins.y] = c;
                }
            }
            // shift column
            else {
                for (int i = 0; i < 6; i++) {
                    char c = copiedGrid[ins.x][i];
                    grid[ins.x][(i + ins.value) % 6] = c;
                }
            }
        }
    }

    public void part2(){
        // no need for part 2, just read manually the solution from part 1 draw method
    }

    private void countLit(){
        int counter = 0;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if(grid[i][j] == '#'){
                    counter ++;
                }
            }
        }
        System.out.println(counter);
    }

    private void draw(){
        for (int i = 0; i < 6; i++) { // x
            for (int j = 0; j < 50; j++) { // y
                System.out.print(grid[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void copyGrid(){
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                copiedGrid[i][j] = grid[i][j];
            }
        }
    }

    private void fillGrid(){
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                grid[i][j] = '.';
                copiedGrid[i][j] = '.';
            }
        }
    }
}
