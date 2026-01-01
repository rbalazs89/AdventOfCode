package year2024.day14;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day14 {
    private final ReadLines reader = new ReadLines(2024, 14, 2);
    private static final int maxX = 101; //specified by task description, 11 for sample input
    private static final int maxY = 103; // specified by task description, 7 for example input
    private static final ArrayList<Robot> robots = new ArrayList<>();
    private static final int[][] field = new int[maxY][maxX];

    private void prepare(){
        robots.clear();
        List<String> input = reader.readFile();

        robots.clear();
        // Parse the input and populate the field
        for (String line : input) {
            // Example line: p=0,4 v=3,-3
            String[] parts = line.split(" ");
            String[] position = parts[0].substring(2).split(",");
            String[] velocity = parts[1].substring(2).split(",");

            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            int velX = Integer.parseInt(velocity[0]);
            int velY = Integer.parseInt(velocity[1]);

            // Ensure the position is within bounds
            if (x >= 0 && x < maxX && y >= 0 && y < maxY) {
                Robot robot = new Robot();
                robot.X = x;
                robot.Y = y;
                robot.velX = velX;
                robot.velY = velY;
                robots.add(robot);
            } else {
                System.err.println("Robot position out of bounds: " + line);
            }
        }
    }

    public void part1(){
        prepare();
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < robots.size(); i++) {
                Robot current = robots.get(i);

                current.X = current.X + current.velX;
                if(current.X >= maxX){
                    current.X = current.X % maxX;
                } else if (current.X < 0){
                    current.X = current.X + maxX;
                }

                current.Y = current.Y + current.velY;
                if(current.Y >= maxY){
                    current.Y = current.Y % maxY;
                } else if (current.Y < 0){
                    current.Y  = current.Y + maxY;
                }
            }
        }

        int[] quadrants = new int[5];
        for (int i = 0; i < robots.size(); i++) {
            quadrants[getQuadrant(robots.get(i))] ++;
        }

        int result = 1;
        for (int i = 1; i < quadrants.length; i++) {
            if(quadrants[i] != 0){
                result = result * quadrants[i];
            }
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();
        int maxCloseRobots = 0;
        int maxTime = -1;
        for (int j = 0; j < 10000; j++) {
            for (int i = 0; i < robots.size(); i++) {
                Robot current = robots.get(i);

                current.X = current.X + current.velX;
                if(current.X >= maxX){
                    current.X = current.X % maxX;
                } else if (current.X < 0){
                    current.X = current.X + maxX;
                }

                current.Y = current.Y + current.velY;
                if(current.Y >= maxY){
                    current.Y = current.Y % maxY;
                } else if (current.Y < 0){
                    current.Y  = current.Y + maxY;
                }
            }
            int closeRobots = 0;
            for (int i = 0; i < robots.size(); i++) {
                if(checkIfCloseToOtherRobot(robots.get(i))){
                    closeRobots ++;
                }
            }
            if(closeRobots > maxCloseRobots){
                maxTime = j;
                maxCloseRobots = closeRobots;
            }
        }
        System.out.println(maxTime + 1);
    }

    private static boolean checkIfCloseToOtherRobot(Robot robot) {
        int x = robot.X;
        int y = robot.Y;
        int neighbours = 0;
        for (int i = 0; i < robots.size(); i++) {
            Robot current = robots.get(i);
            if(current.X + 1 == x && current.Y == y){
                neighbours++;
            }
            if(current.X - 1 == x && current.Y == y){
                neighbours++;
            }
            if(current.X == x && current.Y - 1 == y){
                neighbours++;
            }
            if(current.X + 1 == x && current.Y + 1 == y){
                neighbours++;
            }
        }
        return neighbours >= 3;
    }

    private static int getQuadrant(Robot robot){
        if(robot.X == maxX/2 || robot.Y == maxY/2){
            return 0;
        }
        if(robot.X < maxX/2 && robot.Y < maxY/2){
            return 1;
        }
        if(robot.X > maxX/2 && robot.Y < maxY/2){
            return 2;
        }
        if(robot.X < maxX/2){
            return 3;
        }
        return 4;
    }

    /**
     * DEBUG METHOD:
     */
    private static void printOutField(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                field[i][j] = 0;
            }
        }
        for (int i = 0; i < robots.size(); i++) {
            field[robots.get(i).Y][robots.get(i).X] ++;
        }
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static class Robot {
        private int X = 0;
        private int Y = 0;
        private int velX = 0;
        private int velY = 0;
    }
}
