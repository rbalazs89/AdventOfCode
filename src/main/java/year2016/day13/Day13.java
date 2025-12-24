package year2016.day13;

import main.ReadLines;

import java.util.*;

public class Day13 {
    private int favouriteNumber = 0;
    private char[][] grid;  //  grid[y][x]
    private final int maxGrid = 50; // arbitrary number

    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 13, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void processData(){
        readData();
        favouriteNumber = Integer.parseInt(fileLines.get(0));
        grid = new char[maxGrid][maxGrid];
    }

    public void part1(){
        processData();
        makeGrid();
        //drawGrid();

        // going around scouting:
        Set<String> visited = new HashSet<>();
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{1,1});
        visited.add("1,1");
        int depth = 0;

        whileLoop:
        while(!q.isEmpty()){
            int stepsOnThisDepth = q.size();
            for (int i = 0; i < stepsOnThisDepth; i++) {
                int[] current = q.poll();
                int y = current[0];
                int x = current[1];
                if(x == 31 && y == 39){ // goal for example
                    System.out.println(depth);
                    break whileLoop;
                }
                // check top:
                if(y - 1 >= 0){
                    if(grid[y - 1][x] == '.'){
                        if(visited.add((y - 1) + "," + x)){
                            q.add(new int[]{y - 1, x});
                        }
                    }
                }
                // check right:
                if(x + 1 < maxGrid){
                    if(grid[y][x + 1] == '.'){
                        if(visited.add(y + "," + (x + 1))){
                            q.add(new int[]{y, x + 1});
                        }
                    }
                }
                // check bottom:
                if(y + 1 < maxGrid){
                    if(grid[y + 1][x] == '.'){
                        if(visited.add((y + 1) + "," + x)){
                            q.add(new int[]{y + 1, x});
                        }
                    }
                }

                // check left:
                if(x - 1 >= 0){
                    if(grid[y][x - 1] == '.'){
                        if(visited.add(y + "," + (x - 1))){
                            q.add(new int []{y, x - 1});
                        }
                    }
                }
            }
            depth ++;
            if(depth == 50){
                System.out.println(visited.size());
            }
        }
    }

    public void part2(){
        // nothing to do here, done in part 1
    }

    boolean isOpenSpace(int x, int y){
        int number = ((x * x + 3 * x + 2 * y * x + y + y * y) + favouriteNumber);
        String myString = Integer.toBinaryString(number);
        int counter = 0;
        for (int i = 0; i < myString.length(); i++) {
            if(myString.charAt(i) == '1'){
                counter ++;
            }
        }
        if(counter % 2 == 0){
            return true; // walkable open space
        } else {
            return false; // coordinate is a wall
        }
    }

    public void makeGrid(){
        for (int i = 0; i < maxGrid; i++) { // y
            for (int j = 0; j < maxGrid; j++) { // x
                if(isOpenSpace(j,i)){
                    grid[i][j] = '.';
                } else {
                    grid[i][j] = '#';
                }
            }
        }
    }

    public void drawGrid(){
        //grid[4][7] = 'X';
        for (int i = 0; i < maxGrid; i++) { // y
            for (int j = 0; j < maxGrid; j++) { // x
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
