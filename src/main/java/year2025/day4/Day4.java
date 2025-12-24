package year2025.day4;

import main.ReadLines;

import java.util.List;

public class Day4 {
    List<String> fileLines;
    int grid[][]; // 0 -> empty, 1 -> filled
    int maxX;
    int maxY;

    private final ReadLines reader = new ReadLines(2025, 4);
    int inputNumber = 2;

    public void readData(){
        // READ INPUT
        fileLines = reader.readFile(inputNumber);

        maxX = fileLines.get(0).length();
        maxY = fileLines.size();
        grid = new int[maxY][maxX];

        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(fileLines.get(i).substring(j, j+1).equals(".")){
                    grid[i][j] = 0;
                } else {
                    grid[i][j] = 1;
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
    }

    public void part1(){
        readData();
        //drawGrid();

        int result = 0;

        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(checkAdjacentFreeSpace(i, j) >= 5){
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    public int checkAdjacentFreeSpace(int y, int x){
        //this function checks how many adjacent rolls
        int result = 0;
        if(grid[y][x] == 0){
            return 0;
        }
        //Check top:
        if(y - 1 < 0){
            result++;
        }
        else if(grid[y - 1][x] == 0){
            result++;
        }

        //Check top right:
        if( y - 1 < 0 || x + 1 >= maxX){
            result++;
        } else if (grid[y - 1][x + 1] == 0){
            result++;
        }

        //Check right:
        if(x + 1 >= maxX){
            result++;
        } else if (grid[y][x + 1] ==  0){
            result++;
        }

        //Check right bottom:
        if(x + 1 >= maxX || y + 1 >= maxY){
            result++;
        } else if (grid[y + 1][x + 1] == 0){
            result++;
        }

        //Check bottom:
        if(y + 1 >= maxY){
            result++;
        } else if (grid[y + 1][x] == 0){
            result++;
        }

        //check bottom left
        if(y + 1 >= maxY || x - 1 < 0){
            result++;
        } else if (grid[y + 1][x - 1] == 0){
            result++;
        }

        //check left
        if(x - 1 < 0){
            result++;
        } else if (grid[y][x - 1] == 0){
            result++;
        }

        //check top left:
        if(x - 1 < 0 || y - 1 < 0){
            result++;
        } else if (grid[y - 1][x - 1] == 0){
            result++;
        }

        return result;
    }


    public void part2(){
        readData();

        int numberOfZero = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(grid[i][j] == 0){
                    numberOfZero++;
                }
            }
        }

        for (int k = 0; k < 1000; k++) {
            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    if(checkAdjacentFreeSpace(i, j) >= 5){
                        grid[i][j] = 0;
                    }
                }
            }
        }

        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(grid[i][j] == 0){
                    result ++;
                }
            }
        }

        System.out.println(result - numberOfZero);
    }

}
