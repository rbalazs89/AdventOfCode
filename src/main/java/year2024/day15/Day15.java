package year2024.day15;

import main.ReadLines;

import java.util.*;

public class Day15 {
    private static int maxY;
    private static int maxX;
    private static String[][] field;
    private static int robotX;
    private static int robotY;
    private static final ArrayList<String> commands = new ArrayList<String>();
    private final ReadLines reader = new ReadLines(2024, 15, 2);

    private void prepare(){
        commands.clear();
        List<String> input = reader.readFile();
        maxX = input.getFirst().length();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).isEmpty()){
                maxY = i;
            }
        }

        field = new String[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                field[i][j] = input.get(i).substring(j,j + 1);
                if(input.get(i).charAt(j) == '@'){
                    robotX = j;
                    robotY = i;
                }
            }
        }

        for (int i = maxY + 1; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                commands.add(input.get(i).substring(j, j + 1));
            }
        }
    }

    private void preparePart2(){
        commands.clear();
        List<String> input = reader.readFile();
        maxX = input.getFirst().length();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).isEmpty()){
                maxY = i;
            }
        }
        for (int i = maxY + 1; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                commands.add(input.get(i).substring(j, j + 1));
            }
        }

        field = new String[maxY][maxX * 2];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(input.get(i).charAt(j) == '.'){
                    field[i][2 * j] = ".";
                    field[i][2 * j + 1] = ".";
                }
                else if(input.get(i).charAt(j) == '#'){
                    field[i][2 * j] = "#";
                    field[i][2 * j + 1] = "#";
                }
                else if(input.get(i).charAt(j) == 'O'){
                    field[i][2 * j] = "[";
                    field[i][2 * j + 1] = "]";
                }
                else if(input.get(i).charAt(j) == '@'){
                    robotX = 2 * j;
                    robotY = i;
                    field[i][2 * j] = "@";
                    field[i][2 * j + 1] = ".";
                }
            }
        }
        maxX = maxX * 2;
    }

    public void part1(){
        prepare();
        for (int i = 0; i < commands.size(); i++) {
            oneMoveBoxes(robotX, robotY, commands.get(i));
        }

        // calculate result:
        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(field[i][j].equals("O")){
                    result = result + i * 100 + j;
                }
            }
        }
        System.out.println(result);
    }

    public void part2(){
        preparePart2();
        for (int i = 0; i < commands.size(); i++) {
            oneMoveBoxes2(robotX, robotY, commands.get(i));
        }

        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(field[i][j].equals("[")){
                    result = result + i * 100 + j;
                }
            }
        }
        System.out.println(result);
    }

    private static void oneMoveBoxes2(int x, int y, String direction){
        ArrayList<int[]> boxesCoordinates = new ArrayList<>();
        ArrayList<int[]> boxesCoordinatesSaved = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        boxesCoordinatesSaved.add(new int[]{y, x});
        visited.add(y + "," + x);
        boxesCoordinates.add(new int[]{y, x});

        switch (direction) {
            case "^" -> {
                while (!boxesCoordinates.isEmpty()) {
                    int[] current = boxesCoordinates.removeFirst(); //get and remove the first box

                    int currY = current[0];
                    int currX = current[1];

                    if (field[currY - 1][currX].equals("[")) {
                        if (!visited.contains((currY - 1) + "," + currX)) {
                            boxesCoordinates.add(new int[]{currY - 1, currX});
                            boxesCoordinatesSaved.add(new int[]{currY - 1, currX});
                            visited.add((currY - 1) + "," + currX);
                        }
                        if (!visited.contains((currY - 1) + "," + (currX + 1))) {
                            boxesCoordinates.add(new int[]{currY - 1, currX + 1});
                            boxesCoordinatesSaved.add(new int[]{currY - 1, currX + 1});
                            visited.add((currY - 1) + "," + (currX + 1));
                        }
                    } else if (field[currY - 1][currX].equals("]")) {
                        // Right part of a box
                        if (!visited.contains((currY - 1) + "," + currX)) {
                            boxesCoordinates.add(new int[]{currY - 1, currX});
                            boxesCoordinatesSaved.add(new int[]{currY - 1, currX});
                            visited.add((currY - 1) + "," + currX);
                        }
                        if (!visited.contains((currY - 1) + "," + (currX - 1))) {
                            boxesCoordinates.add(new int[]{currY - 1, currX - 1});
                            boxesCoordinatesSaved.add(new int[]{currY - 1, currX - 1});
                            visited.add((currY - 1) + "," + (currX - 1));
                        }
                    }
                }
                boolean canMove = true;
                for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                    int currX = boxesCoordinatesSaved.get(i)[1];
                    int currY = boxesCoordinatesSaved.get(i)[0];
                    if (!(field[currY - 1][currX].equals(".") || visited.contains((currY - 1) + "," + (currX)))) {
                        canMove = false;
                        break;
                    }
                }
                if (canMove) {
                    boxesCoordinatesSaved.sort(Comparator.comparingInt(a -> a[0]));

                    robotY--;
                    for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                        int[] current = boxesCoordinatesSaved.get(i);
                        field[current[0] - 1][current[1]] = field[current[0]][current[1]];
                        field[current[0]][current[1]] = ".";
                    }
                }
            }
            case "v" -> {
                while (!boxesCoordinates.isEmpty()) {
                    int[] current = boxesCoordinates.removeFirst(); //get and remove the first box

                    int currY = current[0];
                    int currX = current[1];

                    if (field[currY + 1][currX].equals("[")) {
                        if (!visited.contains((currY + 1) + "," + currX)) {
                            boxesCoordinates.add(new int[]{currY + 1, currX});
                            boxesCoordinatesSaved.add(new int[]{currY + 1, currX});
                            visited.add((currY + 1) + "," + currX);
                        }
                        if (!visited.contains((currY + 1) + "," + (currX + 1))) {
                            boxesCoordinates.add(new int[]{currY + 1, currX + 1});
                            boxesCoordinatesSaved.add(new int[]{currY + 1, currX + 1});
                            visited.add((currY + 1) + "," + (currX + 1));
                        }
                    } else if (field[currY + 1][currX].equals("]")) {
                        // right part of a box
                        if (!visited.contains((currY + 1) + "," + currX)) {
                            boxesCoordinates.add(new int[]{currY + 1, currX});
                            boxesCoordinatesSaved.add(new int[]{currY + 1, currX});
                            visited.add((currY + 1) + "," + currX);
                        }
                        if (!visited.contains((currY + 1) + "," + (currX - 1))) {
                            boxesCoordinates.add(new int[]{currY + 1, currX - 1});
                            boxesCoordinatesSaved.add(new int[]{currY + 1, currX - 1});
                            visited.add((currY + 1) + "," + (currX - 1));
                        }
                    }
                }
                boolean canMove = true;
                for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                    int currX = boxesCoordinatesSaved.get(i)[1];
                    int currY = boxesCoordinatesSaved.get(i)[0];
                    if (!(field[currY + 1][currX].equals(".") || visited.contains((currY + 1) + "," + (currX)))) {
                        canMove = false;
                        break;
                    }
                }
                if (canMove) {
                    boxesCoordinatesSaved.sort((a, b) -> Integer.compare(b[0], a[0]));

                    robotY++;
                    for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                        int[] current = boxesCoordinatesSaved.get(i);
                        field[current[0] + 1][current[1]] = field[current[0]][current[1]];
                        field[current[0]][current[1]] = ".";
                    }
                }
            }
            case "<" -> {

                int areaToMove = 0;
                for (int i = x - 1; i >= 0; i--) {
                    areaToMove++;
                    if (field[y][i].equals("#") || field[y][i].equals(".")) {
                        break;
                    }
                }

                for (int i = x - areaToMove; i < x - 1; i++) {

                    if (field[y][i].equals(".")) {
                        field[y][i] = "[";
                        field[y][i + 1] = "]";
                        field[y][i + 2] = ".";
                    }
                    i = i + 1;
                }

                if (field[robotY][robotX - 1].equals(".")) {
                    field[robotY][robotX] = ".";
                    robotX--;
                    field[robotY][robotX] = "@";
                }
            }
            case ">" -> {
                int areaToMove = 0;
                for (int i = x + 1; i < maxX; i++) {
                    areaToMove++;
                    if (field[y][i].equals("#") || field[y][i].equals(".")) {
                        break;
                    }
                }

                for (int i = x + areaToMove; i > x + 1; i--) {
                    if (field[y][i].equals(".")) {
                        field[y][i] = "]";
                        field[y][i - 1] = "[";
                        field[y][i - 2] = ".";
                    }
                    i = i - 1;
                }

                if (field[robotY][robotX + 1].equals(".")) {
                    field[robotY][robotX] = ".";
                    robotX++;
                    field[robotY][robotX] = "@";
                }
            }
        }
    }

    private static void oneMoveBoxes(int x, int y, String direction) {
        int toMove = 0;
        //decide move direction:
        switch (direction) {
            case "^" -> {
                //see how many steps until find wall or empty space
                for (int i = y - 1; i >= 0; i--) {
                    toMove++;
                    if (field[i][x].equals("#") || field[i][x].equals(".")) {
                        break;
                    }
                }

                //move boxes if there is empty space:
                for (int i = y - toMove; i < y; i++) {
                    if (field[i][x].equals("O") && field[i - 1][x].equals(".")) {
                        field[i][x] = ".";
                        field[i - 1][x] = "O";
                    }
                }
                if (field[robotY - 1][robotX].equals(".")) {
                    field[robotY][robotX] = ".";
                    robotY--;
                    field[robotY][robotX] = "@";
                }
                return;
            }
            case ">" -> {
                for (int i = x + 1; i < maxX; i++) {
                    toMove++;
                    if (field[y][i].equals("#") || field[y][i].equals(".")) {
                        break;
                    }
                }
                for (int i = x + toMove; i >= x; i--) {
                    if (field[y][i].equals("O") && field[y][i + 1].equals(".")) {
                        field[y][i] = ".";
                        field[y][i + 1] = "O";
                    }
                }
                if (field[robotY][robotX + 1].equals(".")) {
                    field[robotY][robotX] = ".";
                    robotX++;
                    field[robotY][robotX] = "@";
                }
            }
            case "v" -> {
                for (int i = y + 1; i < maxY; i++) {
                    toMove++;
                    if (field[i][x].equals("#") || field[i][x].equals(".")) {
                        break;
                    }
                }
                for (int i = y + toMove; i > y; i--) {
                    if (field[i][x].equals("O") && field[i + 1][x].equals(".")) {
                        field[i][x] = ".";
                        field[i + 1][x] = "O";
                    }
                }

                if (field[robotY + 1][robotX].equals(".")) {
                    field[robotY][robotX] = ".";
                    robotY++;
                    field[robotY][robotX] = "@";
                }
            }
            case "<" -> {
                for (int i = x - 1; i >= 0; i--) {
                    toMove++;
                    if (field[y][i].equals("#") || field[y][i].equals(".")) {
                        break;
                    }
                }
                for (int i = x - toMove; i < x; i++) {
                    if (field[y][i + 1].equals("O") && field[y][i].equals(".")) {
                        field[y][i + 1] = ".";
                        field[y][i] = "O";
                    }
                }
                if (field[robotY][robotX - 1].equals(".")) {
                    field[robotY][robotX] = ".";
                    robotX--;
                    field[robotY][robotX] = "@";
                }
            }
        }
    }
}