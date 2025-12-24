package year2016.day22;

import main.ReadLines;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 {
    /**
     * Part 2 of this puzzle is too hard for me to code for a universal solution
     * Manual movements inside the grid are tracked and represented in a txt file.
     * It is clear from visual representation that some servers hold too much data compared to others, there are adjacent to each other,
     * their data can never be moved, these are treated as walls.
     * In this setup data can only be moved to a single server that currently hold 0 data. There is only one such server.
     * Essentially it becomes some kind of modified sliding puzzle,
     * I remember I did one of these in the game The Neverhood when I was 8.
     */
    //setup:
    private final ReadLines reader = new ReadLines(2016, 22, 2);
    private List<String> fileLines; // lines from txt file

    // puzzle:
    private final static int START_LINE = 2; // input file start at the 3rd line
    private Storage[][] grid; // stored as grid[y][x] assigned by prepare method
    private int maxX, maxY = 0; // assigned by prepare method
    private int stepCounter = 0;

    // represents the server location that currently holds no data
    private int currentX;
    private int currentY;


    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare() {
        // get maxX and maxY
        maxY = 0;
        maxX = 0;
        readData();
        int fileStart = 2;
        Pattern p = Pattern.compile("\\d+");
        for (int i = START_LINE; i < fileLines.size(); i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);
            while (m.find()) {
                numbers.add(Integer.parseInt(m.group()));
            }
            maxX = Math.max(maxX, numbers.get(0));
            maxY = Math.max(maxY, numbers.get(1));
        }

        //adjust length
        maxX++;
        maxY++;
        grid = new Storage[maxY][maxX];

        for (int i = fileStart; i < fileLines.size(); i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);
            while (m.find()) {
                numbers.add(Integer.parseInt(m.group()));
            }
            grid[numbers.get(1)][numbers.get(0)] = new Storage(numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3), numbers.get(4));
        }

        //after printing visual representation its clear that some servers can never be moved, treat these as walls:
        //walls are not used in any calculation logic, only representation
        wallify(grid);
    }

    public void part1() {
        prepare();
        int counter = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Storage current = grid[i][j];
                if (current.used == 0) {
                    continue;
                }

                for (int k = 0; k < maxY; k++) {
                    for (int l = 0; l < maxX; l++) {

                        // safe check if the same storages are compared
                        if (k == i & l == j) {
                            continue;
                        }

                        // compare used and available data for part 1
                        Storage toCompare = grid[k][l];
                        if (current.used <= toCompare.available) {
                            counter++;
                        }
                    }
                }
            }
        }
        System.out.println(counter);
    }

    public void part2() {
        prepare();

        // starting point
        currentY = 22;
        currentX = 26;
        grid[0][maxX - 1].VIPData = true;

        //avoid walls
        for (int i = 0; i < 15; i++) {
            makeMove("up");
        }
        for (int i = 0; i < 2; i++) {
            makeMove("left");
        }
        for (int i = 0; i < 7; i++) {
            makeMove("up");
        }
        for (int i = 0; i < 6; i++) {
            makeMove("right");
        }
        // at this point, "0 server" arrived just before the VIP data after avoiding walls:

        // start moving it from top right to top left
        for (int i = 0; i < 30; i++) {
            makeMove("right");
            makeMove("down");
            makeMove("left");
            makeMove("left");
            makeMove("up");
        }

        // make last slide:
        makeMove("right");
        System.out.println(stepCounter);
        drawGridToFile();
    }

    /**
     * DEBUG METHOD
     * Helps to visualize grid
     * Saves the current grid into a txt file instead of printing it.
     */
    private void drawGridToFile() {
        int lengthPerItem = 8;

        // Primary path
        File file = new File("src/main/java/year2016/day22/grid_debug.txt");
        // Ensure parent directory exists, if not -> fall back to root
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            System.out.println("Directory not found, saving grid_debug.txt to project root instead.");
            file = new File("grid_debug.txt");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Header row
            writer.write("    ");
            for (int i = 0; i < maxX; i++) {
                String original = String.valueOf(i);
                writer.write(original + " ".repeat(Math.max(0, lengthPerItem - original.length())));
            }
            writer.newLine();

            // Grid rows
            for (int i = 0; i < maxY; i++) {
                if (i <= 9) {
                    writer.write(i + ":  ");
                } else {
                    writer.write(i + ": ");
                }
                for (int j = 0; j < maxX; j++) {
                    String toPrint = "";
                    if (grid[i][j].VIPData) {
                        toPrint = "*";
                    }
                    if (grid[i][j].isWall) {
                        toPrint = "#";
                    } else {
                        toPrint += grid[i][j].used + "/" + grid[i][j].size;
                        if (grid[i][j].VIPData) {
                            toPrint += "*";
                        }
                    }
                    writer.write(toPrint + " ".repeat(Math.max(0, lengthPerItem - toPrint.length())));
                }
                writer.newLine();
            }
            System.out.println("Grid saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeMove(String moveDirection){
        stepCounter++;
        switch (moveDirection){
            case "up": {
                if (currentY - 1 < 0) {
                    throw new IllegalStateException("invalid move, out of bounds");
                }
                if (grid[currentY - 1][currentX].available < grid[currentY][currentX].used) {
                    throw new IllegalStateException("invalid move, server cant hold data");
                }

                if (grid[currentY - 1][currentX].VIPData) {
                    grid[currentY - 1][currentX].VIPData = false;
                    grid[currentY][currentX].VIPData = true;
                }

                grid[currentY][currentX].used = grid[currentY - 1][currentX].used;
                grid[currentY - 1][currentX].used = 0;
                currentY--;
                break;
            }

            case "down": {
                if (currentY + 1 >= maxY) {
                    throw new IllegalStateException("invalid move, out of bounds");
                }
                if (grid[currentY + 1][currentX].available < grid[currentY][currentX].used) {
                    throw new IllegalStateException("invalid move, server cant hold data");
                }

                if (grid[currentY + 1][currentX].VIPData) {
                    grid[currentY + 1][currentX].VIPData = false;
                    grid[currentY][currentX].VIPData = true;
                }

                grid[currentY][currentX].used = grid[currentY + 1][currentX].used;
                grid[currentY + 1][currentX].used = 0;
                currentY++;
                break;
            }

            case "left": {
                if (currentX - 1 < 0) {
                    throw new IllegalStateException("invalid move, out of bounds");
                }
                if (grid[currentY][currentX - 1].available < grid[currentY][currentX].used) {
                    throw new IllegalStateException("invalid move, server cant hold data");
                }

                if (grid[currentY][currentX - 1].VIPData) {
                    grid[currentY][currentX - 1].VIPData = false;
                    grid[currentY][currentX].VIPData = true;
                }

                grid[currentY][currentX].used = grid[currentY][currentX - 1].used;
                grid[currentY][currentX - 1].used = 0;
                currentX--;
                break;
            }

            case "right": {
                if (currentX + 1 >= maxX) {
                    throw new IllegalStateException("invalid move, out of bounds");
                }
                if (grid[currentY][currentX + 1].available < grid[currentY][currentX].used) {
                    throw new IllegalStateException("invalid move, server cant hold data");
                }

                if (grid[currentY][currentX + 1].VIPData) {
                    grid[currentY][currentX + 1].VIPData = false;
                    grid[currentY][currentX].VIPData = true;
                }

                grid[currentY][currentX].used = grid[currentY][currentX + 1].used;
                grid[currentY][currentX + 1].used = 0;
                currentX++;
                break;
            }
            default:
                throw new IllegalStateException("move not recognized");
        }
    }

    private void wallify(Storage[][] server){
        int wallRow = 6;
        for (int i = 25; i < maxX; i++) {
            server[wallRow][i].isWall = true;
        }
    }
}