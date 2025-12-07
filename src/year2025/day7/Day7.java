package year2025.day7;

import main.ReadLines;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {

    List<String> fileLines;
    char[][] grid;
    int maxX = 0;
    int maxY = 0;
    int[] start = new int[2];
    Set<String> savedPositions = new HashSet<>();

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        makeStartingGrid();
        makeFirstStep();
        drawGrid();
        makeOneStep();
        drawGrid();
        System.out.println(savedPositions.size());
    }

    public void part2(){

    }

    public void makeStartingGrid(){
        readData();
        maxX = fileLines.get(0).length();
        maxY = fileLines.size();

        grid = new char[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                grid[i][j] = fileLines.get(i).charAt(j);
                if(grid[i][j] == 'S'){
                    start[0] = i;
                    start[1] = j;
                }
            }
        }
    }

    public void makeFirstStep(){
        grid[start[0] + 1][start[1]] = '|';
    }

    public void makeOneStep(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(grid[i][j] == '|'){
                    if(i + 1 < maxY){
                        if(grid[i + 1][j] == '.'){
                            grid[i + 1][j] = '|';
                        } else if (grid[i + 1][j] == '^'){
                            String position = (i + 1) + "," + (j);
                            if(savedPositions.add(position)){
                                if(j + 1 < maxX){
                                    grid[i + 1][j + 1] = '|';
                                }
                                if(j - 1 >= 0){
                                    grid[i + 1][j - 1] = '|';
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void drawGrid(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
