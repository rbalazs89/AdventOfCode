package year2024.day4;

import main.ReadLines;

import java.util.List;

public class Day4 {
    private final ReadLines reader = new ReadLines(2024, 4, 2);
    private String[][] input;
    private int maxX;
    private int maxY;

    private void prepare(){
        List<String> lines = reader.readFile();
        maxY = lines.size();
        maxX = lines.getFirst().length();

        input = new String[maxY][maxX];
        for(int y = 0; y < maxY; y ++){
            for (int x = 0; x < maxX; x++) {
                input[y][x] = lines.get(y).substring(x, x + 1);
            }
        }
    }

    public void part1(){
        prepare();

        int result = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if(searchUp(x,y)){
                    result++;
                } if (searchUpRight(x,y)){
                    result++;
                } if (searchRight(x,y)){
                    result++;
                } if (searchDownRight(x,y)){
                    result++;
                } if (searchDown(x,y)){
                    result++;
                } if (searchDownLeft(x,y)){
                    result++;
                } if (searchLeft(x,y)){
                    result++;
                } if (searchUpLeft(x,y)){
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();

        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(searchX(j, i)) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    private boolean searchX(int x, int y){
        if(!input[y][x].equals("A")){
            return false;
        }
        if(x - 1 < 0 || x + 1 >= maxX || y - 1 < 0 || y + 1 >= maxY){
            return false;
        }
        if(input[y - 1][x - 1].equals("M") && input[y - 1][x + 1].equals("M")){
            if(input[y + 1][x - 1].equals("S") && input[y + 1][x + 1].equals("S")){
                return true;
            }
        }
        if(input[y + 1][x + 1].equals("M") && input[y - 1][x + 1].equals("M")){
            if(input[y - 1][x - 1].equals("S") && input[y + 1][x - 1].equals("S")){
                return true;
            }
        }

        if(input[y - 1][x - 1].equals("S") && input[y - 1][x + 1].equals("S")){
            if(input[y + 1][x - 1].equals("M") && input[y + 1][x + 1].equals("M")){
                return true;
            }
        }
        if(input[y + 1][x + 1].equals("S") && input[y - 1][x + 1].equals("S")){
            return input[y - 1][x - 1].equals("M") && input[y + 1][x - 1].equals("M");
        }

        return false;
    }
    private boolean searchUp(int x, int y){
        if (y - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y - 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        y = y - 1;
        return input[y][x].equals("S");
    }

    private boolean searchUpRight(int x, int y){
        if (y - 3 < 0 || x + 3 >= maxX) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y - 1;
        x = x + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y - 1;
        x = x + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        y = y - 1;
        x = x + 1;
        return input[y][x].equals("S");
    }

    private boolean searchRight(int x, int y){
        if (x + 3 >= maxX) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x + 1;
        return input[y][x].equals("S");
    }

    private boolean searchDownRight(int x, int y){
        if (x + 3 >= maxX || y + 3 >= maxY) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x + 1;
        y = y + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x + 1;
        y = y + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x + 1;
        y = y + 1;
        return input[y][x].equals("S");
    }

    private boolean searchDown(int x, int y){
        if (y + 3 >= maxY) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        y = y + 1;
        return input[y][x].equals("S");
    }

    private boolean searchDownLeft(int x, int y){
        if (y + 3 >= maxY || x - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y + 1;
        x = x - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y + 1;
        x = x - 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        y = y + 1;
        x = x - 1;
        return input[y][x].equals("S");
    }

    private boolean searchLeft(int x, int y){
        if ( x - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x - 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x - 1;
        return input[y][x].equals("S");
    }

    private boolean searchUpLeft(int x, int y){
        if (x - 3 < 0 || y - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x - 1;
        y = y - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x - 1;
        y = y - 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x - 1;
        y = y - 1;
        return input[y][x].equals("S");
    }
}