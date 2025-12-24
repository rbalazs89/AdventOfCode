package year2017.day3;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day3 {
    //setup:
    private final ReadLines reader = new ReadLines(2017, 3, 2);
    private List<String> fileLines; // lines from txt file

    //puzzle:
    private int puzzleNumber;
    private final int maxLength = 15; // arbitrary number to start the puzzle from middle
    private final int[][] grid = new int[maxLength][maxLength];

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        puzzleNumber = Integer.parseInt(fileLines.getFirst());
    }

    public void part1() {
        prepare();

        if (puzzleNumber == 1) {
            System.out.println(0);
            return;
        }

        // find which ring (rectangle) the number is in
        int ring = 0;
        int maxInRing = 1;
        while (maxInRing < puzzleNumber) {
            ring++;
            maxInRing = (2 * ring + 1) * (2 * ring + 1);
        }

        if (puzzleNumber == maxInRing) {
            System.out.println(2 * ring);
            return;
        }

        int sideLength = 2 * ring + 1;

        // bottom right corner is maxInRing
        // start from the middle of the right side and go counter-clockwise
        int startOfRing = (2 * ring - 1) * (2 * ring - 1) + 1;
        int offset = puzzleNumber - startOfRing;

        // each side has (sideLength - 1) positions, go around in a rectangle form
        int positionsPerSide = sideLength - 1;
        int side = offset / positionsPerSide;
        int positionOnSide = offset % positionsPerSide;
        int x, y;

        // calculate position based on which side we're on
        switch (side) {
            case 0: // right side, moving up
                x = ring;
                y = positionOnSide - (ring - 1);
                break;
            case 1: // top side, moving left
                x = (ring - 1) - positionOnSide;
                y = ring;
                break;
            case 2: // left side, moving down
                x = -ring;
                y = (ring - 1) - positionOnSide;
                break;
            case 3: // bottom side, moving right
                x = positionOnSide - (ring - 1);
                y = -ring;
                break;
            default:
                x = 0;
                y = 0;
        }
        int result = Math.abs(x) + Math.abs(y);
        System.out.println(result);
    }


    public void part2(){
        prepare();
        startGrid();

        // set up steps:
        ArrayList<int[]> steps = new ArrayList<>();
        // start at top, goes counter-clockwise
        steps.add(new int[]{0, -1}); // top
        steps.add(new int[]{-1, -1}); // top left
        steps.add(new int[]{-1, 0}); // left
        steps.add(new int[]{-1, 1}); // left bottom
        steps.add(new int[]{0, 1}); // bottom
        steps.add(new int[]{1, 1}); // bottom right
        steps.add(new int[]{1, 0}); // right
        steps.add(new int[]{1, -1}); // right top

        int x = maxLength / 2;
        int y = maxLength / 2;

        // arbitrary modification, starts at 9th element 25, prefilled grid
        x++;
        y++;

        // start loop
        while(true){
            int[] nextStep = getNextCoordinates(x,y, steps); // find next step
            // add next step
            x = x + nextStep[0];
            y = y + nextStep[1];

            // update grid of the current position
            int current = updateValueOnGrid(x, y, steps);
            if(current > puzzleNumber){
                System.out.println(current);
                break;
            }
            grid[x][y] = current;
        }
    }

    private int updateValueOnGrid(int x, int y, ArrayList<int[]> steps){
        int value = 0;
        for (int i = 0; i < steps.size(); i++) {
            value += grid[x + steps.get(i)[0]][y + steps.get(i)[1]];
        }
        return value;
    }

    private int[] getNextCoordinates(int x, int y, ArrayList<int[]> steps){
        int points = 0;

        // go top after
        points = 0;
        for (int i = 0; i < steps.size(); i++) {
            int value = grid[x + steps.get(i)[0]][y + steps.get(i)[1]];
            if((i == 0 || i == 6) && value == 0){
                points ++;
            }
            if(i == 2 && value != 0){
                points ++;
            }
        }
        if(points  == 3){
            return new int[]{0, -1};
        }

        // go left
        points = 0;
        for (int i = 0; i < steps.size(); i++) {
            int value = grid[x + steps.get(i)[0]][y + steps.get(i)[1]];
            if((i == 0 || i == 2) && value == 0){
                points ++;
            }
            if(i == 4 && value != 0){
                points ++;
            }
        }
        if(points  == 3){
            return new int[]{-1, 0};
        }

        //go bottom
        points = 0;
        for (int i = 0; i < steps.size(); i++) {
            int value = grid[x + steps.get(i)[0]][y + steps.get(i)[1]];
            if((i == 2 || i == 4) && value == 0){
                points ++;
            }
            if(i == 6 && value != 0){
                points ++;
            }
        }
        if(points  == 3){
            return new int[]{0, 1};
        }

        //go right
        points = 0;
        for (int i = 0; i < steps.size(); i++) {
            int value = grid[x + steps.get(i)[0]][y + steps.get(i)[1]];
            if((i == 6 || i == 4 ) && value == 0){
                points ++;
            }
            if(i == 0 && value != 0){
                points ++;
            }
        }
        if(points  == 3){
            return new int[]{1, 0};
        }
        throw new IllegalStateException("direction choosing method failed");
    }

    private void startGrid(){
        // manually fill the first 10 numbers:
        int mid = maxLength / 2;
        grid[mid][mid] = 1;
        grid[mid + 1][mid] = 1;
        grid[mid + 1][mid - 1] = 2;
        grid[mid][mid - 1] = 4;
        grid[mid - 1][mid - 1] = 5;
        grid[mid - 1][mid] = 10;
        grid[mid - 1][mid + 1] = 11;
        grid[mid][mid + 1] = 23;
        grid[mid + 1][mid + 1] = 25;
    }

    /**
     * DEBUG METHOD
     * not used in the final version
     * visualize current grid
     */
    public void drawGrid(){
        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < maxLength; j++) {
                int length = 4 - String.valueOf(grid[j][i]).length();
                String filler = "";
                for (int k = 0; k < length; k++) {
                    filler += " ";
                }
                System.out.print(grid[j][i] + filler);
            }
            System.out.println();
        }
    }
}

