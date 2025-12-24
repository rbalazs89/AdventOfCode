package year2016.day18;
import main.ReadLines;

import java.util.List;

public class Day18 {
    private final ReadLines reader = new ReadLines(2016, 18, 1);
    private List<String> fileLines; // lines from txt file

    private char[][] room; // grid represents the room: '^' is a trap, '.' is safe tile room[row][col]
    private final int roomMaxRow = 40; // this value is from task description
    private static final int part2MaxRow = 400000; // this value is from task description
    private int maxCol;

    private void readData(){
        // READ INPUT
        fileLines = reader.readFile();
    }

    private void processData(){
        readData();
        String firstLine = fileLines.getFirst();
        maxCol = firstLine.length();
        room = new char[roomMaxRow][firstLine.length()];
        for (int i = 0; i < firstLine.length(); i++) {
            room[0][i] = firstLine.charAt(i);
        }
    }

    public void part1(){
        processData();

        // apply algorithm line by line
        for (int j = 1; j < roomMaxRow; j++) {
            for (int i = 0; i < maxCol; i++) {
                if(decideCurrentTile(i, j)){
                    room[j][i] = '.';
                } else {
                    room[j][i] = '^';
                }
            }
        }
        System.out.println(countSafeTiles(roomMaxRow));
    }

    // returns true if tile is safe, returns false if tile is trap
    // y means rows, x means column
    private boolean decideCurrentTile(int x, int y){
        // the method assumes the input is valid
        if(y == 0){
            System.out.println("y cannot be lower than 1");
            return false;
        }

        // determine left center and right tiles above
        // false means tile is trap, true means tile is safe
        boolean left, center, right;
        if(x - 1 >= 0){
            left = room[y - 1][x - 1] == '.';
        } else {
            left = true;
        }
        center = room[y - 1][x] == '.';
        if(x + 1 < room[0].length){
            right = room[y - 1][x + 1] == '.';
        } else {
            right = true;
        }

        // left and center are traps, but right is not
        if(!left && !center && right){
            return false;
        }

        // center and right are traps but left is not
        if(left && !center && !right){
            return false;
        }

        // only left is trap
        if(!left && right && center){
            return false;
        }

        // only right is trap
        if( left && center && !right){
            return false;
        }

        // in all other cases the tile is safe
        return true;
    }

    public void part2(){
        processData();
        // it is safe to use the same room object for part 2, just need to expand columns:
        String firstLine = fileLines.getFirst();
        room = new char[part2MaxRow][firstLine.length()];
        for (int i = 0; i < firstLine.length(); i++) {
            room[0][i] = firstLine.charAt(i);
        }

        // apply algorithm line by line
        for (int j = 1; j < part2MaxRow; j++) {
            for (int i = 0; i < maxCol; i++) {
                if(decideCurrentTile(i, j)){
                    room[j][i] = '.';
                } else {
                    room[j][i] = '^';
                }
            }
        }
        System.out.println(countSafeTiles(part2MaxRow));
    }

    // visual representation of the room
    void drawRoom(){
        for (int i = 0; i < roomMaxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                System.out.print(room[i][j] + " ");
            }
            System.out.println();
        }
    }

    // gives the result
    private int countSafeTiles(int maxRow){
        int counter = 0;
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                if(room[i][j] == '.'){
                    counter ++;
                }
            }
        }
        return counter;
    }
}
