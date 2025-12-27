package year2017.day21;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day21 {
    private final ReadLines reader = new ReadLines(2017, 21, 2);

    // enhancements:
    private ArrayList<EnhancePattern> twoDimensionEnhancements = new ArrayList<>();
    private ArrayList<EnhancePattern> threeDimensionEnhancements = new ArrayList<>();

    // puzzle:
    private static final int PART_ONE_COUNTER = 5; // specified by task description
    private static final int PART_TWO_COUNTER = 18; // specified by task description

    // specified by task description
    private char[][] getStartingGrid(){
        return new char[][]{{'.','#','.'},{'.','.','#'},{'#','#','#'}};
    }

    private void preparePattern(){
        twoDimensionEnhancements.clear();
        threeDimensionEnhancements.clear();

        List<String> fileLines = reader.readFile();
        for (String line : fileLines) {
            line = line.replaceAll(" ","");
            String[] parts = line.split("=>");

            // get enhancements that make 2x2 -> 3x3
            if(parts[0].length() == 5){
                String[] pattern1 = parts[0].split("/");
                String[] pattern2 = parts[1].split("/");
                char[][] in = new char[2][2];
                char[][] out = new char[3][3];

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        in[i][j] = pattern1[i].charAt(j);
                    }
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        out[i][j] = pattern2[i].charAt(j);
                    }
                }
                twoDimensionEnhancements.add(new EnhancePattern(in,out));

            } else {
                // get enhancements that make 3xx -> 4x4
                String[] pattern1 = parts[0].split("/");
                String[] pattern2 = parts[1].split("/");
                char[][] in = new char[3][3];
                char[][] out = new char[4][4];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        in[i][j] = pattern1[i].charAt(j);
                    }
                }

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        out[i][j] = pattern2[i].charAt(j);
                    }
                }
                threeDimensionEnhancements.add(new EnhancePattern(in,out));
            }
        }
        fileLines.getFirst();
    }

    public void part1(){
        preparePattern();
        // give step of grid size changes -> 3x3 -> 4x4 -> 6x6 -> 9x9 -> 12x12 -> 18x18
        char[][] grid = getStartingGrid();

        int counter = 0;
        // is divisible by 2:
        while (counter < PART_ONE_COUNTER) {
            counter ++;
            if (grid.length % 2 == 0) {
                int steps = grid.length / 2;
                char[][] mutatedGrid = getNewGrid(steps + grid.length);
                for (int i = 0; i < steps; i++) {
                    for (int j = 0; j < steps; j++) {

                        //get grid piece starting at (j * 2, i * 2)
                        char[][] gridPiece = getNDimGridPiece(grid, i, j, 2);

                        // check if this grid piece applies to some enhancements
                        for (int k = 0; k < twoDimensionEnhancements.size(); k++) {
                            EnhancePattern current = twoDimensionEnhancements.get(k);
                            if (current.isMatching(gridPiece)) {
                                gridPiece = current.implementEnhancement();
                                break;
                            }
                        }

                        //insert grid piece into mutatedGrid
                        insertIntoMutatedGrid(mutatedGrid, gridPiece, i, j, 3);
                    }
                }
                grid = mutatedGrid;
            }

            // is divisible by 3 but not by 2:
            else {
                int steps = grid.length / 3;
                char[][] mutatedGrid = getNewGrid(steps + grid.length);
                for (int i = 0; i < steps; i++) {
                    for (int j = 0; j < steps; j++) {

                        //get grid piece starting at (j * 3, i * 3)
                        char[][] gridPiece = getNDimGridPiece(grid, i, j, 3);

                        // check if this grid piece applies to some enhancements
                        for (int k = 0; k < threeDimensionEnhancements.size(); k++) {
                            EnhancePattern current = threeDimensionEnhancements.get(k);
                            if (current.isMatching(gridPiece)) {
                                gridPiece = current.implementEnhancement();
                                break;
                            }
                        }

                        //insert grid piece into mutatedGrid
                        insertIntoMutatedGrid(mutatedGrid, gridPiece, i, j, 4);
                    }
                }
                grid = mutatedGrid;
            }
        }
        System.out.println(countGrid(grid));
    }

    // n is 2 or 3
    private char[][] getNDimGridPiece(char[][] grid, int y, int x, int n){
        char[][] newGridPiece = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newGridPiece[i][j] = grid[i + (y * n)][j + (x * n)];
            }
        }
        return newGridPiece;
    }

    //n is 3 or 4
    private void insertIntoMutatedGrid(char[][] grid, char[][] gridPiece, int y, int x, int n){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[n * y + i][n * x + j] = gridPiece[i][j];
            }
        }
    }

    char[][] getNewGrid(int size){
        return new char[size][size];
    }

    public void part2(){
        preparePattern();
        // give step of grid size changes -> 3x3 -> 4x4 -> 6x6 -> 9x9 -> 12x12 -> 18x18
        char[][] grid = getStartingGrid();

        int counter = 0;
        // is divisible by 2:
        while (counter < PART_TWO_COUNTER) {
            counter ++;
            if (grid.length % 2 == 0) {
                int steps = grid.length / 2;
                char[][] mutatedGrid = getNewGrid(steps + grid.length);
                for (int i = 0; i < steps; i++) {
                    for (int j = 0; j < steps; j++) {

                        //get grid piece starting at (j * 2, i * 2)
                        char[][] gridPiece = getNDimGridPiece(grid, i, j, 2);

                        // check if this grid piece applies to some enhancements
                        for (int k = 0; k < twoDimensionEnhancements.size(); k++) {
                            EnhancePattern current = twoDimensionEnhancements.get(k);
                            if (current.isMatching(gridPiece)) {
                                gridPiece = current.implementEnhancement();
                                break;
                            }
                        }

                        //insert grid piece into mutatedGrid
                        insertIntoMutatedGrid(mutatedGrid, gridPiece, i, j, 3);
                    }
                }
                grid = mutatedGrid;
            }

            // is divisible by 3 but not by 2:
            else {
                int steps = grid.length / 3;
                char[][] mutatedGrid = getNewGrid(steps + grid.length);
                for (int i = 0; i < steps; i++) {
                    for (int j = 0; j < steps; j++) {

                        //get grid piece starting at (j * 3, i * 3)
                        char[][] gridPiece = getNDimGridPiece(grid, i, j, 3);

                        // check if this grid piece applies to some enhancements
                        for (int k = 0; k < threeDimensionEnhancements.size(); k++) {
                            EnhancePattern current = threeDimensionEnhancements.get(k);
                            if (current.isMatching(gridPiece)) {
                                gridPiece = current.implementEnhancement();
                                break;
                            }
                        }

                        //insert grid piece into mutatedGrid
                        insertIntoMutatedGrid(mutatedGrid, gridPiece, i, j, 4);
                    }
                }
                grid = mutatedGrid;
            }
        }
        System.out.println(countGrid(grid));
    }

    /**
     * DEBUG METHOD:
     */
    private void drawGrid(char[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private int countGrid(char[][] grid){
        int counter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '#'){
                    counter ++;
                }
            }
        }
        return counter;
    }
}