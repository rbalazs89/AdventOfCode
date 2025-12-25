package year2017.day19;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day19 {
    // setup:
    private final ReadLines reader = new ReadLines(2017, 19, 2);
    private List<String> fileLines; // Lines from txt file

    // puzzle:
    private char[][] grid; //grid[y][x]
    private int maxY;
    private int maxX;

    // debug:
    private static final boolean debug = false;

    // part1:
    private String part1Result = "";
    private int part2Result = 0;

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepareGrid(){
        readData();

        // grid needs both the leading and trailing spaces:
        maxX = 0;
        maxY = fileLines.size();
        for (int i = 0; i < maxY; i++) {
            maxX = Math.max(maxX, fileLines.get(i).length());
        }
        grid = new char[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if(x >= fileLines.get(y).length()){
                    grid[y][x] = ' ';
                } else {
                    grid[y][x] = fileLines.get(y).charAt(x);
                }
            }
        }
    }

    public void part1(){
        prepareGrid();

        Position p = new Position();

        p.y = 0; // specified by task description
        p.x = findStart();
        String direction = "D"; // starting direction is down

        ArrayList<int[]> visited = new ArrayList<>(); // debug

        while(!p.stop){
            doOneStep(direction, p); // guaranteed by previous direction find, that this won't go outside the grid
            addLetterIfFound(p); // appends the result string if letter found

            direction = findNextDirection(direction, p); // checks if next step is possible and flags p.stop = true
            if(debug){
                visited.add(new int[]{p.y, p.x});
            }
        }

        if(debug){
            for (int i = 0; i < visited.size(); i++) {
                grid[visited.get(i)[0]][visited.get(i)[1]] = 'X';
            }
        }
        incrementPart2Result();

        System.out.println(part1Result);
        System.out.println(part2Result);

    }

    private void doOneStep(String direction, Position p){
        incrementPart2Result();
        switch (direction){
            case "U": {p.y--;break;}
            case "R": {p.x++;break;}
            case "D": {p.y++;break;}
            case "L": {p.x--;break;}
        }
    }

    private String findNextDirection(String previousDirection, Position p){
        char c = grid[p.y][p.x];
        if(c == ' '){
            p.stop = true;
            return "END";
        }

        // for cognitive comfort char fields checked separately:
        if(isLetter(p)){
            switch (previousDirection){
                case "U":{
                    if(p.y - 1 >= 0){
                        if(grid[p.y - 1][p.x] != ' '){
                            return "U";
                        }
                    }
                    p.stop = true;
                    return "END";
                }
                case "R":{
                    if(p.x + 1 < maxX){
                        if(grid[p.y][p.x + 1] != ' '){
                            return "R";
                        }
                    }
                    p.stop = true;
                    return "END";
                }
                case "D":{
                    if(p.y + 1 < maxY){
                        if(grid[p.y + 1][p.x] != ' '){
                            return "D";
                        }
                    }
                    p.stop = true;
                    return "END";
                }
                case "L":{
                    if(p.x - 1 >= 0){
                        if(grid[p.y][p.x - 1] != ' '){
                            return "L";
                        }
                    }
                    p.stop = true;
                    return "END";
                }
                default: throw new IllegalStateException("Previous direction not recognized: " + previousDirection);
            }
        }

        // directions if current field is not a letter
        switch (previousDirection){
            case "U":{
                if(c == '|' || c == '-'){
                    if(p.y - 1 >= 0){
                        return "U";
                    }
                } else if (c == '+'){
                    if(p.x - 1 >= 0){
                        if(grid[p.y][p.x - 1] == '-' || isLetter(p.y, p.x - 1)){
                            return "L";
                        }
                    }
                    if(p.x + 1 < maxX){
                        if(grid[p.y][p.x + 1] == '-' || isLetter(p.y, p.x + 1)){
                            return "R";
                        }
                    }
                }
                p.stop = true;
                return "END";
            }
            case "R":{
                if(c == '-' || c == '|'){
                    if(p.x + 1 < maxX){
                        return "R";
                    }
                } else if (c == '+'){
                    if(p.y + 1 < maxY){
                        if(grid[p.y + 1][p.x] == '|' || isLetter(p.y + 1, p.x)){
                            return "D";
                        }
                    }
                    if(p.y - 1 >= 0){
                        if(grid[p.y - 1][p.x] == '|' || isLetter(p.y - 1, p.x)){
                            return "U";
                        }
                    }
                }
                p.stop = true;
                return "END";
            }
            case "D":{
                if(c == '|' || c == '-'){
                    if(p.y + 1 < maxY){
                        return "D";
                    }
                } else if (c == '+'){
                    if(p.x - 1 >= 0){
                        if(grid[p.y][p.x - 1] == '-' || isLetter(p.y, p.x - 1)){
                            return "L";
                        }
                    }
                    if(p.x + 1 < maxX){
                        if(grid[p.y][p.x + 1] == '-' || isLetter(p.y, p.x + 1)){
                            return "R";
                        }
                    }
                }
                p.stop = true;
                return "END";
            }
            case "L":{
                if(c == '-' || c == '|'){
                    if(p.x - 1 >= 0){
                        return "L";
                    }
                } else if (c == '+'){
                    if(p.y + 1 < maxY){
                        if(grid[p.y + 1][p.x] == '|' || isLetter(p.y + 1, p.x)){
                            return "D";
                        }
                    }
                    if(p.y - 1 >= 0){
                        if(grid[p.y - 1][p.x] == '|' || isLetter(p.y - 1, p.x)){
                            return "U";
                        }
                    }
                }
                p.stop = true;
                return "END";
            }
            default: throw new IllegalStateException("Previous direction not recognized: " + previousDirection);
        }
    }

    private int findStart(){
        for (int x = 0; x < maxX; x++) {
            if(grid[0][x] == '|'){
                return x;
            }
        }
        throw new IllegalStateException("Start not found.");
    }

    public void part2(){

    }
    /**
     * DEBUG METHOD
     * prints the grid
     */
    private void drawGrid(){
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void addLetterIfFound(Position p){
        if(isLetter(p)){
            part1Result += grid[p.y][p.x];
        }
    }

    private boolean isLetter(Position p){
        char c = grid[p.y][p.x];
        return c >= 'A' && c <= 'Z';
    }

    private boolean isLetter(int y, int x){
        char c = grid[y][x];
        return c >= 'A' && c <= 'Z';
    }

    private void incrementPart2Result(){
        part2Result ++;
    }
}
