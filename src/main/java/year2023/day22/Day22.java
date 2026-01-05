package year2023.day22;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day22 {
    // note for this puzzle: all bricks are one unit length in two of their directions
    private final ReadLines reader = new ReadLines(2023, 22, 2);
    private final List<Brick> originalBricks = new ArrayList<>();
    private boolean[][][] originalSpace; // occupied space is false, free space is true

    // starts from 0,0,0:
    private void prepare(){
        originalBricks.clear();
        List<String> lines = reader.readFile();

        // initialize bricks
        for(String line: lines){
            String[] coordinates = line.split("~");
            String[] startingCord = coordinates[0].split(",");
            String[] endingCord = coordinates[1].split(",");
            int[] start = new int[]{Integer.parseInt(startingCord[0]), Integer.parseInt(startingCord[1]), Integer.parseInt(startingCord[2])};
            int[] end = new int[]{Integer.parseInt(endingCord[0]), Integer.parseInt(endingCord[1]), Integer.parseInt(endingCord[2])};

            originalBricks.add(new Brick(start, end));
        }

        // make bounding box
        int boundingX = 0;
        int boundingY = 0;
        int boundingZ = 0;
        for (Brick b : originalBricks) {
            boundingX = Math.max(boundingX, b.end[0]);
            boundingY = Math.max(boundingY, b.end[1]);
            boundingZ = Math.max(boundingZ, b.end[2]);
        }

        // space array
        originalSpace = new boolean[boundingX + 1][boundingY + 1][boundingZ + 1];
        for (int z = 0; z < boundingZ + 1; z++) {
            for (int y = 0; y < boundingY + 1; y++) {
                for (int x = 0; x < boundingX + 1; x++) {
                    originalSpace[x][y][z] = z == 0;
                }
            }
        }
    }

    public void part1(){
        // get original bricks:
        prepare();
        synchSpaceWithBricks(originalBricks, originalSpace);
        for (int i = 0; i < originalBricks.size(); i++) {
            if(letBricksFall(originalBricks, originalSpace)){
                break;
            }
        }

        // make new list for part 1:
        List<Brick> bricks = deepCopyBricks(originalBricks);
        boolean[][][] space = deepCopySpace(originalSpace);

        int solution = 0;

        // delete space -> check if above brick fall -> set space back
        for (int i = 0; i < bricks.size(); i++) {
            Brick currentBrick = bricks.get(i);
            int tempX1 = currentBrick.start[0];
            int tempX2 = currentBrick.end[0];
            int tempY1 = currentBrick.start[1];
            int tempY2 = currentBrick.end[1];
            int tempZ1 = currentBrick.start[2];
            int tempZ2 = currentBrick.end[2];
            if(tempX1 == tempX2 && tempY1 == tempY2){
                for (int j = tempZ1; j < tempZ2 + 1; j++) {
                    space[tempX1][tempY1][j] = false;
                }
            }
            else if(tempX1 == tempX2 && tempZ1 == tempZ2){
                for (int j = tempY1; j < tempY2 + 1; j++) {
                    space[tempX1][j][tempZ1] = false;
                }
            }
            else if(tempY1 == tempY2 && tempZ1 == tempZ2){
                for (int j = tempX1; j < tempX2 + 1; j++) {
                    space[j][tempY1][tempZ1] = false;
                }
            }
            if(isItSafeToRemove(bricks, space)){
                solution++;
            }
            if(tempX1 == tempX2 && tempY1 == tempY2){
                for (int j = tempZ1; j < tempZ2 + 1; j++) {
                    space[tempX1][tempY1][j] = true;
                }
            }
            else if(tempX1 == tempX2 && tempZ1 == tempZ2){
                for (int j = tempY1; j < tempY2 + 1; j++) {
                    space[tempX1][j][tempZ1] = true;
                }
            }
            else if(tempY1 == tempY2 && tempZ1 == tempZ2){
                for (int j = tempX1; j < tempX2 + 1; j++) {
                    space[j][tempY1][tempZ1] = true;
                }
            }
        }
        System.out.println(solution);
    }
    
    public void part2(){
        // get original bricks:
        prepare();
        synchSpaceWithBricks(originalBricks, originalSpace);
        for (int i = 0; i < originalBricks.size(); i++) {
            letBricksFall(originalBricks, originalSpace);
        }

        // make new list for part 1:

        int totalBricksFallen = 0;
        for (int i = 0; i < originalBricks.size(); i++) {
            List<Brick> bricks = deepCopyBricks(originalBricks);
            boolean[][][] space = deepCopySpace(originalSpace);

            bricks.remove((int)i); // always remove i-th element
            synchSpaceWithBricks(bricks, space);

            for (int j = 0; j < originalBricks.size(); j++) {
                if(letBricksFall(bricks,space)){
                    break;
                }
            }

            totalBricksFallen += compareTwoStacksOfBricks(bricks, i, originalBricks);
        }
        System.out.println(totalBricksFallen);
    }

    private int compareTwoStacksOfBricks(List<Brick> fallenBrick, int ignoredIndex, List<Brick> originalBrick){
        int counter = 0;
        int fallenIndex;
        for (int i = 0; i < originalBrick.size(); i++) {
            fallenIndex = i;
            if(i > ignoredIndex){
                fallenIndex --;
            }
            if(i == ignoredIndex){
                continue;
            }

            if(fallenBrick.get(fallenIndex).start[2] != originalBrick.get(i).start[2]){
                counter ++;
            }
        }
        // return if there is a difference
        return counter;
    }

    private void synchSpaceWithBricks(List<Brick> bricks, boolean[][][] space){
        int maxX = space.length;
        int maxY = space[0].length;
        int maxZ = space[0][0].length;

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                for (int z = 0; z < maxZ; z++) {
                    space[x][y][z] = z == 0;
                }
            }
        }

        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            if(brick.start[0] == brick.end[0] && brick.start[1] == brick.end[1]){
                for (int z = brick.start[2]; z < brick.end[2] + 1; z++) {
                    space[brick.start[0]][brick.start[1]][z] = true;
                }
            }

            else if(brick.start[2] == brick.end[2] && brick.start[1] == brick.end[1]){
                for (int x = brick.start[0]; x < brick.end[0]+ 1; x++) {
                    space[x][brick.start[1]][brick.start[2]] = true;
                }
            }

            else if(brick.start[2] == brick.end[2] && brick.start[0] == brick.end[0]){
                for (int y = brick.start[1]; y < brick.end[1] + 1; y++) {
                    space[brick.start[0]][y][brick.start[2]] = true;
                }
            }
        }
    }

    private boolean letBricksFall(List<Brick> bricks, boolean[][][] space){
        boolean stabilized = true;
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            boolean checkCurrentBrickAgain = false;

            insideLoop:
            for (int x = brick.start[0]; x < brick.end[0] + 1; x++) {
                for (int y = brick.start[1]; y < brick.end[1] + 1; y++) {

                    // brick cant fall if any tile is occupied
                    if (space[x][y][brick.start[2] - 1]) {
                        break insideLoop;
                    }

                    // brick falls one unit
                    if (x == brick.end[0] && y == brick.end[1]) {
                        synchSpaceWithFallingBrick(brick, space);
                        brickFallsOneUnit(brick);
                        checkCurrentBrickAgain = true;
                    }
                }
            }
            if (checkCurrentBrickAgain) {
                stabilized = false;
                i--; // check again if it can fall further
            }
        }
        return stabilized;
    }

    // brick falls one unit
    private void synchSpaceWithFallingBrick(Brick brick, boolean[][][] space){
        for (int x = brick.start[0]; x < brick.end[0] + 1; x++) {
            for (int y = brick.start[1]; y < brick.end[1] + 1; y++) {
                space[x][y][brick.start[2] - 1] = true;
                space[x][y][brick.end[2]] = false;
            }
        }
    }

    private void brickFallsOneUnit(Brick brick){
        brick.start[2]--;
        brick.end[2]--;
    }

    private boolean isItSafeToRemove(List<Brick> bricks, boolean[][][] space){
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            insideLoop:
            for (int j = brick.start[0]; j < brick.end[0] + 1; j++) {
                for (int k = brick.start[1]; k < brick.end[1] + 1; k++) {
                    if (space[j][k][brick.start[2] - 1]) {
                        break insideLoop;
                    }
                    if (j == brick.end[0] && k == brick.end[1]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean[][][] deepCopySpace(boolean[][][] originalSpace) {
        int maxX = originalSpace.length;
        int maxY = originalSpace[0].length;
        int maxZ = originalSpace[0][0].length;

        boolean[][][] newSpace = new boolean[maxX][maxY][maxZ];

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                System.arraycopy(originalSpace[x][y], 0, newSpace[x][y], 0, maxZ);
            }
        }
        return newSpace;
    }

    private List<Brick> deepCopyBricks(List<Brick> originalBricks){
        List<Brick> newBricks = new ArrayList<>();
        for (int i = 0; i < originalBricks.size(); i++) {
            newBricks.add(originalBricks.get(i).deepCopyBrick());
        }
        return newBricks;
    }
}