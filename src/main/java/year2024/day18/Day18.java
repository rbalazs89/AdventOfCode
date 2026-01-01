package year2024.day18;

import main.ReadLines;

import java.util.*;

public class Day18 {
    private final ReadLines reader = new ReadLines(2024, 18, 2);
    //7 for example input, 71 for real input
    private static final int maxY = 71;
    private static final int maxX = 71;
    private static int maxWall = 0;

    private static int[][] wall;
    private static String[][] field;

    private void prepare(){
        List<String> input = reader.readFile();

        field = new String[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                String s = ".";
                field[i][j] = s;
            }
        }
        int size = 0;
        if(maxY == 7){
            size = 12;
        } else if (maxY == 71){
            size = 1024;
        }
        for (int i = 0; i < size; i++) {
            String[] coordinates = input.get(i).split(",");
            field[Integer.parseInt(coordinates[1])][Integer.parseInt(coordinates[0])] = "#";
        }

        wall = new int[2][input.size()];

        for (int i = 0; i < input.size(); i++) {
            String[] coordinates = input.get(i).split(",");
            wall[0][i] = Integer.parseInt(coordinates[1]);
            wall[1][i] = Integer.parseInt(coordinates[0]);
        }
        maxWall = input.size();
    }

    public void part1(){
        prepare();
        Set<String> visited = new HashSet<>();
        int startX = 0;
        int startY = 0;
        visited.add(startY + "," + startX);
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX});
        int counter = 0;
        while(!queue.isEmpty()){
            counter ++;
            for (int i = 0; i < queue.size(); i++) {
                int[] current = queue.poll();
                int x = current[1];
                int y = current[0];
                if(x == maxX - 1 && y == maxY - 1){
                    System.out.println(counter);
                    break;
                }

                x = current[1] + 1;
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }

                x = current[1] - 1;
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }

                x = current[1];
                y = current[0] + 1;
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }

                y = current[0] - 1;
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }
            }
        }
    }

    public void part2(){
        prepare();
        outerLoop:
        for (int j = 1024; j < maxWall; j++) {
            int wallX = wall[1][j];
            int wallY = wall[0][j];
            field[wallY][wallX] = "#";

            Set<String> visited = new HashSet<>();
            int startX = 0;
            int startY = 0;
            visited.add(startY + "," + startX);
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{startY, startX});
            while(!queue.isEmpty()){
                for (int i = 0; i < queue.size(); i++) {
                    int[] current = queue.poll();
                    int x = current[1];
                    int y = current[0];
                    if(x == maxX - 1 && y == maxY - 1){
                        break;
                    }

                    x = current[1] + 1;
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    x = current[1] - 1;
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    x = current[1];
                    y = current[0] + 1;
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    y = current[0] - 1;
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    if(queue.isEmpty() && !visited.contains("70,70")){
                        System.out.println(wallX + "," + wallY);
                        break outerLoop;
                    }
                }
            }
            //reprint field:
            for (int k = 0; k < maxY; k++) {
                for (int l = 0; l < maxX; l++) {
                    field[k][l] = ".";
                }
            }
            for (int k = 0; k <= j; k++) {
                field[wall[0][k]][wall[1][k]] = "#";
            }
        }
    }
}