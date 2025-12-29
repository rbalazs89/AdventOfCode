package year2018.day11;

import main.ReadLines;

public class Day11 {
    private final ReadLines reader = new ReadLines(2018, 11, 2);
    private static final int CELL_LENGTH = 300; // specified by task description, goes from 1 (included)...300 (included)

    private int getSerialNumber(){
        return Integer.parseInt(reader.readFile().getFirst());
    }

    public void part1(){
        int serialNumber = getSerialNumber();
        int[][] cell = getFuelCell(serialNumber);

        // get the maximum
        int max = Integer.MIN_VALUE;
        int xIndex = 0;
        int yIndex = 0;

        for (int y = 1; y < CELL_LENGTH; y++) {
            for (int x = 1; x < CELL_LENGTH ; x++) {
                int current = getThreeByThreeSum(cell, y, x);
                if(current > max){
                    max = current;
                    yIndex = y;
                    xIndex = x;
                }
            }
        }
        System.out.println(xIndex + "," + yIndex);
    }

    public void part2(){
        int serialNumber = getSerialNumber();
        int[][] cell = getFuelCell(serialNumber);

        int max = Integer.MIN_VALUE;
        int xIndex = 0;
        int yIndex = 0;
        int bestN = 0;

        for (int y = 1; y < CELL_LENGTH; y++) {
            for (int x = 1; x < CELL_LENGTH ; x++) {
                int limit = CELL_LENGTH - Math.max(x, y);
                for (int n = 1; n <= limit; n++) {
                    int current = getNByNSum(cell, y, x, n);
                    if(current > max){
                        max = current;
                        yIndex = y;
                        xIndex = x;
                        bestN = n;
                    }
                }
            }
        }
        System.out.println(xIndex + "," + yIndex + "," + bestN);
    }

    private int getPowerLevel(int serialNumber, int x, int y){
        int rackId = x + 10;
        int powerLevel = rackId * y;
        powerLevel = powerLevel + serialNumber;
        powerLevel = powerLevel * rackId;
        powerLevel = (powerLevel % 1000) / 100;
        powerLevel = powerLevel - 5;
        return powerLevel;
    }

    private int getNByNSum(int[][] cell, int top, int left, int n){
        if(top + n - 1 >= CELL_LENGTH || left + n - 1 >= CELL_LENGTH){
            return 0;
        }

        int sum = 0;
        int limitY = top + n - 1;
        int limitX = left + n - 1;
        for (int y = top; y <= limitY; y ++) {
            for (int x = left; x <= limitX; x++) {
                sum += cell[y][x];
            }
        }

        return sum;
    }

    private int getThreeByThreeSum(int[][] cell, int top, int left){
        if(top + 2 >= CELL_LENGTH || left + 2 >= CELL_LENGTH){
            return 0;
        }

        int sum = 0;
        int limitY = top + 2;
        int limitX = left + 2;
        for (int y = top; y <= limitY; y ++) {
            for (int x = left; x <= limitX; x++) {
                sum += cell[y][x];
            }
        }

        return sum;
    }

    private int[][] getFuelCell(int serialNumber){
        int[][] cell = new int[CELL_LENGTH + 1][CELL_LENGTH + 1];
        for (int y = 1; y <= CELL_LENGTH; y++) {
            for (int x = 1; x <= CELL_LENGTH; x++) {
                cell[y][x] = getPowerLevel(serialNumber, x, y);
            }
        }
        return cell;
    }
}
