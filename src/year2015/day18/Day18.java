package year2015.day18;

import main.ReadLines;

import java.util.List;

public class Day18 {
    List<String> fileLines;
    int inputFileIndex = 2;
    char[][] grid;
    char[][] grid2;
    int maxX;
    int maxY;
    int steps = 100; // 6 for example input, 100 for real input
    /**
     A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
     A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
     */

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }

    public void processData(){
        maxX = fileLines.get(0).length();
        maxY = fileLines.size();
        grid = new char[maxY][maxX];
        grid2 = new char[maxY][maxX];
        for (int i = 0; i < fileLines.size(); i++) { // y
            for (int j = 0; j < fileLines.get(i).length(); j++) { // x
                grid[i][j] = fileLines.get(i).charAt(j);
            }
        }
    }

    public void part1(){
        readData();
        processData();

        for (int k = 0; k < steps; k++) {
            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    grid2[i][j] = changeOneLight(i, j);
                }
            }

            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    grid[i][j] = grid2[i][j];
                }
            }
        }
        getResults();

        //drawGrid();
    }

    public void getResults(){
        int results = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(grid[i][j] == '#'){
                    results ++;
                }
            }
        }
        System.out.println(results);
    }

    public void part2(){
        readData();
        processData();

        for (int k = 0; k < steps; k++) {
            grid[0][0] = '#';
            grid[maxY - 1][0] = '#';
            grid[0][maxX - 1] = '#';
            grid[maxY - 1][maxX - 1] = '#';
            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    grid2[i][j] = changeOneLight(i, j);
                }
            }
            grid[0][0] = '#';
            grid[maxY - 1][0] = '#';
            grid[0][maxX - 1] = '#';
            grid[maxY - 1][maxX - 1] = '#';

            grid2[0][0] = '#';
            grid2[maxY - 1][0] = '#';
            grid2[0][maxX - 1] = '#';
            grid2[maxY - 1][maxX - 1] = '#';
            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    grid[i][j] = grid2[i][j];
                }
            }
        }
        getResults();

        //drawGrid();
    }

    public char changeOneLight(int y, int x){
        int onAdj = 0;
        // top left
        if(y >= 1 && x >= 1){
            if(grid[y - 1][x - 1] == '#'){
                onAdj ++;
            }
        }

        // top
        if(y >= 1){
            if(grid[y - 1][x] == '#'){
                onAdj ++;
            }
        }

        // top right
        if(y >= 1 && x < maxX - 1){
            if(grid[y - 1][x + 1] == '#'){
                onAdj ++;
            }
        }

        // right
        if(x < maxX - 1){
            if(grid[y][x + 1] == '#'){
                onAdj ++;
            }
        }

        // bottom right
        if(x < maxX - 1 && y < maxY - 1){
            if(grid[y + 1][x + 1] == '#'){
                onAdj ++;
            }
        }

        //bottom
        if(y < maxY - 1){
            if(grid[y + 1][x] == '#'){
                onAdj ++;
            }
        }

        //bottom left
        if(y < maxY - 1 && x >= 1){
            if(grid[y + 1][x - 1] == '#'){
                onAdj ++;
            }
        }

        //left
        if(x >= 1){
            if(grid[y][x - 1] == '#'){
                onAdj ++;
            }
        }
        if(grid[y][x] == '#') {
            if (onAdj == 2 || onAdj == 3) {
                return '#';
            } else {
                return '.';
            }
        } else {
            if(onAdj == 3){
                return '#';
            } else {
                return '.';
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
