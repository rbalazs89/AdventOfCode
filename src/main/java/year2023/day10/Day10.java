package year2023.day10;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day10 {
    // S starting direction and type must be typed manually
    private final ReadLines reader = new ReadLines(2023, 10, 2);
    static ArrayList<Tile> tiles = new ArrayList<>();
    static ArrayList<Tile> walkedTiles = new ArrayList<>();
    static int startingX;
    static int startingY;
    static int STileType;
    static int lineLength;
    static int startingDirection;
    static int width;
    static int length;

    private void prepare(){
        tiles.clear();
        // 1: top and bottom |
        // 2: right and left -
        // 3: J
        // 4: F
        // 5: L
        // 6: 7
        List<String> input = reader.readFile();
        length = input.getFirst().length();
        width = input.size();

        lineLength = input.getFirst().length();
        for(int i = 0; i < input.size(); i ++){
            for(int j = 0; j < input.get(i).length(); j ++) {
                Tile tile = new Tile(0, j, i);
                char currentChar = input.get(i).charAt(j);
                switch (currentChar) {
                    case '|' -> tile.type = 1;
                    case '-' -> tile.type = 2;
                    case 'J' -> tile.type = 3;
                    case 'F' -> tile.type = 4;
                    case 'L' -> tile.type = 5;
                    case '7' -> tile.type = 6;
                    case 'S' -> tile.type = 0;
                    case '.' -> tile.type = 7;
                }
                tiles.add(tile);
            }
        }

        for(int i = 0; i < tiles.size(); i ++){
            if(tiles.get(i).type == 0){
                startingX = tiles.get(i).x;
                startingY = tiles.get(i).y;
                boolean topPath = false;
                boolean rightPath = false;
                boolean leftPath = false;
                boolean downPath = false;
                if(tiles.get(i - width).type == 1 || tiles.get(i - width).type == 4 || tiles.get(i - width).type == 6){
                    topPath = true;
                }
                if(tiles.get(i + 1).type == 2 || tiles.get(i + 1).type == 3 || tiles.get(i + 1).type == 6){
                    rightPath = true;
                }
                if(tiles.get(i + width).type == 1 || tiles.get(i + width).type == 3 || tiles.get(i + width).type == 5){
                    downPath = true;
                }
                if(tiles.get(i - 1).type == 2 || tiles.get(i - 1).type == 4 || tiles.get(i - 1).type == 5){
                    leftPath = true;
                }

                if(downPath){
                    startingDirection = 3;
                }
                if(topPath){
                    startingDirection = 1;
                }
                if(rightPath){
                    startingDirection = 2;
                }

                if(topPath && rightPath){
                    tiles.get(i).type = 5;
                }
                if(topPath && downPath){
                    tiles.get(i).type = 1;
                }

                if(topPath && leftPath){
                    tiles.get(i).type = 3;
                }

                if(rightPath && downPath){
                    tiles.get(i).type = 4;
                }

                if(rightPath && leftPath){
                    tiles.get(i).type = 2;
                }

                if(downPath && leftPath){
                    tiles.get(i).type = 6;
                }
                break;
            }
        }
    }

    public void part1(){
        prepare();

        int steps = 0;
        int currentX = startingX;
        int currentY = startingY;
        int directionTo = startingDirection;

        while(steps >= 0) {
            if (directionTo == 1) {
                currentY--;
            } else if (directionTo == 2) {
                currentX++;
            } else if (directionTo == 3) {
                currentY++;
            } else if (directionTo == 4) {
                currentX--;
            }

            Tile currentTile = tiles.get(currentY * lineLength + currentX);

            directionTo = getTempInt(directionTo, currentTile);

            steps ++ ;
            if(currentX == startingX && currentY == startingY){
                break;
            }
        }
        System.out.println(steps / 2);
    }

    public void part2(){
        int steps = 0;
        int currentX = startingX;
        int currentY = startingY;
        int directionTo = startingDirection;

        while(steps >= 0) {
            if (directionTo == 1) {
                currentY--;
            } else if (directionTo == 2) {
                currentX++;
            } else if (directionTo == 3) {
                currentY++;
            } else if (directionTo == 4) {
                currentX--;
            }

            Tile currentTile = tiles.get(currentY * lineLength + currentX);
            walkedTiles.add(currentTile);

            directionTo = getTempInt(directionTo, currentTile);

            steps ++ ;
            if(currentX == startingX && currentY == startingY){
                break;
            }
        }
        Tile tile = new Tile(STileType, startingX, startingY);
        walkedTiles.add(tile);

        //calculating area based on coordinates:
        long area = 0L;
        for (int i = 0; i < walkedTiles.size() - 1; i++) {
            area = area + ((long)walkedTiles.get(i).x - (long)walkedTiles.get(i + 1).x) * ((long)walkedTiles.get(i).y + (long)walkedTiles.get(i + 1).y);
        }
        area = area / 2;

        //calculating fence area:
        long fenceArea = 14126;

        //adjustment:
        area = area - ((fenceArea - 4)/2) + fenceArea - 1;
        area = area - fenceArea;

        //another method to calculate inside area, this gives the same result too :)
        boolean inside = false;
        boolean topLeftType = false;
        boolean bottomLeftType = false;
        int solution = 0;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                Tile myTile = getTile(j,i);

                if(inside){
                    if(myTile == null){
                        solution ++;
                        continue;
                    }
                }
                if (myTile != null) {

                    if(myTile.type == 1) {
                        inside = !inside;
                        continue;
                    }
                    if(myTile.type == 4) {
                        topLeftType = true;
                        continue;
                    }
                    if(myTile.type == 5) {
                        bottomLeftType = true;
                        continue;
                    }

                    //top left type
                    if(topLeftType && !inside){
                        if(myTile.type == 3) {
                            topLeftType = false;
                            inside = true;
                            continue;
                        }
                    }
                    if(topLeftType && inside){
                        if(myTile.type == 3) {
                            inside = false;
                            topLeftType = false;
                            continue;
                        }
                    }
                    if(topLeftType && !inside){
                        if(myTile.type == 6) {
                            topLeftType = false;
                            continue;
                        }
                    }
                    if(topLeftType && inside){
                        if(myTile.type == 6) {
                            topLeftType = false;
                            continue;
                        }
                    }
                    //bottom left type
                    if(bottomLeftType && !inside){
                        if(myTile.type == 3) {
                            bottomLeftType = false;
                            continue;
                        }
                    }
                    if(bottomLeftType && inside){
                        if(myTile.type == 3) {
                            bottomLeftType = false;
                            continue;
                        }
                    }
                    if(bottomLeftType && !inside){
                        if(myTile.type == 6) {
                            bottomLeftType = false;
                            inside = true;
                            continue;
                        }
                    }
                    if(bottomLeftType && inside){
                        if(myTile.type == 6) {
                            bottomLeftType = false;
                            inside = false;
                        }
                    }
                }
            }
            inside = false;
            topLeftType = false;
            bottomLeftType = false;
        }

        System.out.println(solution); // method one
        System.out.println(area); // method two
    }

    private static int getTempInt(int directionTo, Tile currentTile) {
        int tempInt = 0;
        if(directionTo == 1){
            if(currentTile.type == 1){
                tempInt = 1;
            }
            if(currentTile.type == 4){
                tempInt = 2;
            }
            if(currentTile.type == 6){
                tempInt = 4;
            }
        }
        if(directionTo == 2){
            if(currentTile.type == 2){
                tempInt = 2;
            }
            if(currentTile.type == 3){
                tempInt = 1;
            }
            if(currentTile.type == 6){
                tempInt = 3;
            }
        }
        if(directionTo == 3){
            if(currentTile.type == 1){
                tempInt = 3;
            }
            if(currentTile.type == 3){
                tempInt = 4;
            }
            if(currentTile.type == 5){
                tempInt = 2;
            }
        }
        if(directionTo == 4){
            if(currentTile.type == 2){
                tempInt = 4;
            }
            if(currentTile.type == 4){
                tempInt = 3;
            }
            if(currentTile.type == 5){
                tempInt = 1;
            }
        }
        return tempInt;
    }

    private Tile getTile(int x, int y){
        for (int i = 0; i < walkedTiles.size(); i++) {
            if(walkedTiles.get(i).x == x && walkedTiles.get(i).y == y){
                return walkedTiles.get(i);
            }
        }
        return null;
    }
}