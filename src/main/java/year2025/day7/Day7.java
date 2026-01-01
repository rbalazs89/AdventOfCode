package year2025.day7;

import main.ReadLines;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {
    private final ReadLines reader = new ReadLines(2025, 7, 2);
    private List<String> fileLines;
    private char[][] grid;
    private int maxX = 0;
    private int maxY = 0;
    private final int[] start = new int[2];
    private final Set<String> savedPositions = new HashSet<>();
    private GridElement[][] grid2;

    private void readData(){
        // READ INPUT
        fileLines = reader.readFile();
    }

    public void part1(){
        makeStartingGrid();
        makeFirstStep();
        makeBeam();
        System.out.println(savedPositions.size());
    }

    public void part2(){
        makeGridPart2();
        makeBeamPart2();

        long result = 0;
        for (int i = 0; i < maxX; i++) {
            if(grid2[maxY - 1][i].type == '|'){
                result += grid2[maxY - 1][i].overlapCounter;
            }
        }
        System.out.println(result);

    }

    public void makeStartingGrid(){
        readData();
        maxX = fileLines.getFirst().length();
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

    public void makeGridPart2(){
        readData();
        maxX = fileLines.getFirst().length();
        maxY = fileLines.size();

        grid = new char[maxY][maxX];
        grid2 = new GridElement[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                GridElement e = new GridElement();
                e.x = j;
                e.y = i;
                e.overlapCounter = 0;
                e.type = fileLines.get(i).charAt(j);

                grid2[i][j] = e;

                if(grid[i][j] == 'S'){
                    start[0] = i;
                    start[1] = j;
                }
            }
        }
        grid2[start[0] + 1][start[1]].type = '|';
        grid2[start[0] + 1][start[1]].overlapCounter = 1;

    }

    public void makeBeam(){
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

    public void makeBeamPart2(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(grid2[i][j].type == '|'){
                    if(i + 1 < maxY){
                        if(grid2[i + 1][j].type == '.' || grid2[i + 1][j].type == '|'){
                            grid2[i + 1][j].type = '|';
                            grid2[i + 1][j].overlapCounter += grid2[i][j].overlapCounter;
                        } else if (grid2[i + 1][j].type == '^'){
                            grid2[i + 1][j + 1].type = '|';
                            grid2[i + 1][j + 1].overlapCounter += grid2[i][j].overlapCounter;
                            grid2[i + 1][j - 1].type = '|';
                            grid2[i + 1][j - 1].overlapCounter += grid2[i][j].overlapCounter;
                        }
                    }
                }
            }
        }
    }

    /**
     * DEBUG METHOD
     * shows the grid with beams
     */
    public void drawGrid(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * DEBUG METHOD
     * shows the grid with beams
     */
    public void drawGrid2(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(grid2[i][j].type);
            }
            System.out.println();
        }
    }
}
