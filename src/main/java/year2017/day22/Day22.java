package year2017.day22;

import main.ReadLines;

import java.util.List;

public class Day22 {
    private final ReadLines reader = new ReadLines(2017, 22, 2);

    // Puzzle:
    private static final int BURST_COUNT_PART_ONE = 10000; // specified by task description
    private static final int BURST_COUNT_PART_TWO = 10000000; // specified by task description
    private static final char CLEAN = '.';
    private static final char INFECTED = '#';
    private static final char WEAKENED = 'W';
    private static final char FLAGGED = 'F';
    private int infectionsCausedPart1 = 0;
    private int infectionsCausedPart2 = 0;

    private char[][] prepare() {
        List<String> filesLines = reader.readFile();

        int enlargeBy = 500; // Safe for 10M bursts, cluster radius << 500

        int originalHeight = filesLines.size();
        int originalWidth = filesLines.getFirst().length();

        int maxY = originalHeight + 2 * enlargeBy;  // total rows
        int maxX = originalWidth + 2 * enlargeBy;   // total columns

        char[][] grid = new char[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                char character;
                if (y >= enlargeBy && y < originalHeight + enlargeBy &&
                        x >= enlargeBy && x < originalWidth + enlargeBy) {

                    character = filesLines.get(y - enlargeBy).charAt(x - enlargeBy);
                } else {
                   character = '.';
                }
                grid[y][x] = character;
            }
        }
        return grid;
    }

    public void part1() {
        // prepare start:
        char[][] grid = prepare();

        // carrier starts in the middle
        Carrier carrier = new Carrier(grid[0].length / 2, grid.length / 2, Direction.UP);

        // run algorithm
        for (int i = 0; i < BURST_COUNT_PART_ONE; i++) {
            partOneAlgorithm(carrier, grid); // <- modifies part1Counter
            carrier.moveForward();
        }
        System.out.println(infectionsCausedPart1);
    }

    public void part2() {
        char[][] grid = prepare();

        // carrier starts in the middle
        Carrier carrier = new Carrier(grid[0].length / 2, grid.length / 2, Direction.UP);

        for (int i = 0; i < BURST_COUNT_PART_TWO; i++) {
            partTwoAlgorithm(carrier, grid); // <- modifies part2Counter
            carrier.moveForward();
        }
        System.out.println(infectionsCausedPart2);
    }

    private void partOneAlgorithm(Carrier c, char[][] grid){
        // clean:
        if(grid[c.getY()][c.getX()] == '.'){
            c.turnLeft();
            grid[c.getY()][c.getX()] = '#';
            infectionsCausedPart1++; // infection counter

        // infected:
        } else if (grid[c.getY()][c.getX()] == '#'){
            c.turnRight();
            grid[c.getY()][c.getX()] = '.';
        }
    }


    private void partTwoAlgorithm(Carrier c, char[][] grid){
        // clean:
        if(grid[c.getY()][c.getX()] == CLEAN){
            grid[c.getY()][c.getX()] = WEAKENED;
            c.turnLeft();

            // Weakened:
        } else if(grid[c.getY()][c.getX()] == WEAKENED){
            infectionsCausedPart2++; // infection counter
            grid[c.getY()][c.getX()] = INFECTED;
            // no turn

            // Infected:
        } else if (grid[c.getY()][c.getX()] == INFECTED){
            grid[c.getY()][c.getX()] = FLAGGED;
            c.turnRight();

            // Flagged:
        } else if (grid[c.getY()][c.getX()] == FLAGGED){
            grid[c.getY()][c.getX()] = CLEAN;
            c.reverse();
        }
    }
}