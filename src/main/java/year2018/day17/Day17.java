package year2018.day17;

import main.ReadLines;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 {
    private final ReadLines reader = new ReadLines(2018, 17, 2);

    // specified by task description:
    private static final int SOURCE_Y = 0;
    private static final int SOURCE_X = 500;

    private char[][] prepareGrid(){
        List<String> lines = reader.readFile();

        // Process data from input txt file:
        Pattern pattern = Pattern.compile("\\d+");
        List<Integer> n = new ArrayList<>(); // all lines have 3 numbers

        ArrayList<int[]> reservoirData = new ArrayList<>(); // stored in x1, x2, y1, y2 format, y2 >= y1 and x2 >= x1
        int maxY = 0;
        int maxX = 0;
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            while(m.find()){
                n.add(Integer.parseInt(m.group()));
            }

            if(line.startsWith("x")){
                reservoirData.add(new int[]{n.get(0), n.get(0), n.get(1), n.get(2)});
                maxY = Math.max(maxY, n.get(2));
                maxX = Math.max(maxX, n.get(0));
            } else {
                reservoirData.add(new int[]{n.get(1), n.get(2), n.get(0), n.get(0)});
                maxY = Math.max(maxY, n.get(0));
                maxX = Math.max(maxX, n.get(2));
            }
            n.clear();
        }

        // Build up the array representing the reservoir
        char[][] reservoir = new char[maxY + 1][maxX + 1]; // grid needs to include the last element
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                reservoir[y][x] = '.';
            }
        }

        for (int i = 0; i < reservoirData.size(); i++) {
            int[] c = reservoirData.get(i);
            int x1 = c[0];
            int x2 = c[1];
            int y1 = c[2];
            int y2 = c[3];

            for (int y = y1; y <= y2 ; y++) {
                for (int x = x1; x <= x2; x++) {
                    reservoir[y][x] = '#';
                }
            }
        }

        return reservoir;
    }

    public void part1(){
        char[][] r = prepareGrid(); // represents the reservoir
        drawReservoir(r);
        r[SOURCE_Y][SOURCE_X] = '|';

        int maxY = r.length; // value is 1 more, than maxY in prepare method
        System.out.println(maxY);


        // mark start:
        int x = SOURCE_X;
        int y = SOURCE_Y;

        Queue<Point> q = new LinkedList<>();
        Point p = new Point(SOURCE_Y, SOURCE_X);
        q.add(p);

        while(q.isEmpty()){
            if (y + 1 == maxY){
                return;
            }
            if(r[y + 1][x] == '.'){
                overWrite(r,y,x,'|');
                y ++;

            } else if (r[y + 1][x] == '#'){
                //if(r[y][x - 1] == '.';
            }
        }


        // flowing down:




    }

    public void part2(){

    }

    /**
     * DEBUG METHOD:
     * draws the reservoir in a txt file
     */
    private void drawReservoir(char[][] reservoir) {
        // Primary path
        File file = new File("src/main/java/year2018/day17/grid_debug.txt");

        // Ensure parent directory exists, if not -> fall back to root
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            System.out.println("Directory not found, saving grid_debug.txt to project root instead.");
            file = new File("grid_debug.txt");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int y = 0; y < reservoir.length; y++) {
                for (int x = 0; x < reservoir[0].length; x++) {
                    writer.write(reservoir[y][x]);
                }
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void overWrite(char[][] grid, int y, int x, char symbol){
        if(grid[y][x] == '#'){
            grid[y][x] = 'X';
            drawReservoir(grid);
            throw new IllegalStateException("trying to overwrite a wall");
        } else {
            grid[y][x] = symbol;
        }
    }


    private class Point {
        int x;
        int y;

        private Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
}
