package year2023.day16;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day16 {
    private final ReadLines reader = new ReadLines(2023, 16, 2);
    private final ArrayList<Tile> table = new ArrayList<>();
    private int length;
    private int width;
    private final ArrayList<Integer> solutions = new ArrayList<>();

    private void prepare(){
        List<String> input = reader.readFile();
        table.clear();
        width = input.getFirst().length();
        length = input.size();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                Tile tile = new Tile(i, j, input.get(j).substring(i, i+1));
                table.add(tile);
            }
        }
    }

    public void part1(){
        prepare();
        for (int j = 0; j < width; j++) {
            prepare();
            travellingLight(j,0,"down");
            int solution = 0;
            for (int i = 0; i < table.size(); i++) {
                if(table.get(i).energized){
                    solution ++;
                }
            }
            solutions.add(solution);
        }

        for (int j = 0; j < width; j++) {
            prepare();
            travellingLight(j,length - 1,"up");
            int solution = 0;
            for (int i = 0; i < table.size(); i++) {
                if(table.get(i).energized){
                    solution ++;
                }
            }
            solutions.add(solution);
        }

        for (int j = 0; j < length; j++) {
            prepare();
            travellingLight(width - 1,j,"left");
            int solution = 0;
            for (int i = 0; i < table.size(); i++) {
                if(table.get(i).energized){
                    solution ++;
                }
            }
            solutions.add(solution);
        }

        for (int j = 0; j < length; j++) {
            prepare();
            travellingLight(0,j,"right");
            int solution = 0;
            for (int i = 0; i < table.size(); i++) {
                if(table.get(i).energized){
                    solution ++;
                }
            }
            solutions.add(solution);
        }
        Collections.sort(solutions);
        System.out.println(solutions.size());
        System.out.println(solutions.getLast());
    }

    public void part2(){
        // solved in part 1
    }

    private boolean continueToTravel(int x, int y, String direction){
        if (x < 0 || x > width || y < 0 || y > length) {
            return false;
        }

        Tile currentTile = getTile(x, y);
        if (currentTile == null) { // this should never happen
            return false;
        }

        return switch (direction) {
            case "left" -> !currentTile.left;
            case "right" -> !currentTile.right;
            case "up" -> !currentTile.up;
            case "down" -> !currentTile.down;
            default -> true;
        };
    }

    private void travellingLight(int startingX, int startingY, String direction){
        int currentX = startingX;
        int currentY = startingY;
        String currentDirection = direction;

        while(continueToTravel(currentX, currentY, currentDirection)){

            getTile(currentX, currentY).energized = true;

            //set current tile travelled to true:
            if(currentDirection.equals("left")){
                getTile(currentX, currentY).left = true;
            }
            if(currentDirection.equals("right")){
                getTile(currentX, currentY).right = true;
            }
            if(currentDirection.equals("up")){
                getTile(currentX, currentY).up = true;
            }
            if(currentDirection.equals("down")){
                getTile(currentX, currentY).down = true;
            }

            //travel further logic
            String currentTileType = getTile(currentX, currentY).type;

            switch (currentDirection) {
                case "left" -> {
                    switch (currentTileType) {
                        case ".", "-" -> {
                            currentDirection = "left";
                            currentX--;
                        }
                        case "\\" -> {
                            currentDirection = "up";
                            currentY--;
                        }
                        case "/" -> {
                            currentDirection = "down";
                            currentY++;
                        }
                        case "|" -> {
                            travellingLight(currentX, currentY - 1, "up");
                            travellingLight(currentX, currentY + 1, "down");
                        }
                    }
                }
                case "right" -> {
                    if (currentTileType.equals(".") || currentTileType.equals("-")) {
                        currentDirection = "right";
                        currentX++;
                    } else if (getTile(currentX, currentY).type.equals("\\")) {
                        currentDirection = "down";
                        currentY++;
                    } else if (currentTileType.equals("/")) {
                        currentDirection = "up";
                        currentY--;
                    } else if (currentTileType.equals("|")) {
                        travellingLight(currentX, currentY - 1, "up");
                        travellingLight(currentX, currentY + 1, "down");
                    }
                }
                case "up" -> {
                    switch (currentTileType) {
                        case ".", "|" -> {
                            currentDirection = "up";
                            currentY--;
                        }
                        case "\\" -> {
                            currentDirection = "left";
                            currentX--;
                        }
                        case "/" -> {
                            currentDirection = "right";
                            currentX++;
                        }
                        case "-" -> {
                            travellingLight(currentX - 1, currentY, "left");
                            travellingLight(currentX + 1, currentY, "right");
                        }
                    }
                }
                case "down" -> {
                    switch (currentTileType) {
                        case ".", "|" -> {
                            currentDirection = "down";
                            currentY++;
                        }
                        case "\\" -> {
                            currentDirection = "right";
                            currentX++;
                        }
                        case "/" -> {
                            currentDirection = "left";
                            currentX--;
                        }
                        case "-" -> {
                            travellingLight(currentX - 1, currentY, "left");
                            travellingLight(currentX + 1, currentY, "right");
                        }
                    }
                }
            }
        }
    }

    private Tile getTile(int x, int y){
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).x == x && table.get(i).y == y){
                return table.get(i);
            }
        }
        throw new IllegalStateException("tile not found");
    }
}