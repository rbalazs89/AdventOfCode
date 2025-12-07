package year2025.day7;

import main.ReadLines;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {
    // part 2 2012790981 too low

    List<String> fileLines;
    char[][] grid;
    int maxX = 0;
    int maxY = 0;
    int[] start = new int[2];
    Set<String> savedPositions = new HashSet<>();

    GridElement[][] grid2;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        makeStartingGrid();
        makeFirstStep();
        drawGrid();
        makeBeam();
        drawGrid();
        System.out.println("part 1 result: " + savedPositions.size());
    }

    public void part2(){
        makeGridPart2();
        makeBeamPart2();
        processColliders();
        part2Result();
        //drawGrid2();
        //System.out.println(grid2[8][4].noMoreCollision + " " + grid2[8][4].incomingBeamN);
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

    public void makeGridPart2(){
        readData();
        maxX = fileLines.get(0).length();
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

    public void drawGrid(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void processColliders(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(grid2[i][j].type == '^'){
                    processOneCollider(i, j);
                }
            }
        }
    }

    public void processOneCollider(int y, int x){
        GridElement e = grid2[y][x];

        int counterLeft = y;
        int counterRight = y;
        for (int i = y; i < maxY; i++) {
            if(grid2[i][x + 1].type != '^'){
                counterRight ++;
            }
            if(grid2[i][x - 1].type != '^'){
                counterLeft ++;
            }
        }
        if(counterLeft == maxY){
            e.noMoreCollision ++;
        }

        if(counterRight == maxY){
            e.noMoreCollision ++;
        }
        e.incomingBeamN = grid2[y - 1][x].overlapCounter;
        grid2[y][x] = e;
    }

    public void part2Result(){
        long result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                GridElement e = grid2[i][j];
                if(e.type == '^'){
                    result = result + e.incomingBeamN * e.noMoreCollision;
                }
            }
        }
        System.out.println("part 2 result: " + result);
    }

    public void drawGrid2(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(grid2[i][j].type);
            }
            System.out.println();
        }
    }
}
