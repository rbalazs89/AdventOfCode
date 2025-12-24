package year2017.day14;

import main.ReadLines;

import java.util.*;

public class Day14 {
    private final ReadLines reader = new ReadLines(2017, 14, 2);
    private List<String> fileLines; // lines from txt file

    // puzzle
    private static final int ROUNDS = 64;
    private static final int BLOCK_SIZE = 16;
    private static final int[] SUFFIX = {17, 31, 73, 47, 23};
    private static final int ROPE_SIZE = 256;
    private static final int GRID_SIZE = 128; // given by task description
    private int[] rope;

    // part 2:
    private final char[][] grid = new char[GRID_SIZE][GRID_SIZE]; // grid[y][x]

    private void readData() {
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();

        String key = fileLines.getFirst();

        int solution = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            String applyKnotOnThis = key + "-" + i;
            String result = hexToBinary(knotHash(applyKnotOnThis));
            for (int j = 0; j < GRID_SIZE; j++) {
                if (result.charAt(j) == '1') {
                    solution ++;
                }
            }
        }

        System.out.println(solution);
    }

    public void part2(){
        readData();

        String key = fileLines.getFirst();

        for (int i = 0; i < GRID_SIZE; i++) {
            String applyKnotOnThis = key + "-" + i;
            String result = hexToBinary(knotHash(applyKnotOnThis));
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = result.charAt(j);
            }
        }

        int clusters = 0;

        HashSet<String> duplicates = new HashSet<>();
        for (int i = 0; i < GRID_SIZE; i++) { // y
            for (int j = 0; j < GRID_SIZE; j++) { // x
                if(grid[i][j] == '0'){
                    continue;
                }
                if(duplicates.add(i + "," + j)){

                    // add first elements for queue
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{i,j});

                    while(!queue.isEmpty()){
                        int[] current = queue.poll();

                        int y = current[0];
                        int x = current[1];

                        // step up:
                        if(y - 1 >= 0){
                            if(grid[y - 1][x] == '1'){
                                if(duplicates.add((y - 1) + "," + (x))){
                                    queue.add(new int[]{y - 1, x});
                                }
                            }
                        }

                        // step right:
                        if(x + 1 < GRID_SIZE){
                            if(grid[y][x + 1] == '1'){
                                if(duplicates.add((y) + "," + (x + 1))){
                                    queue.add(new int[]{y, x + 1});
                                }
                            }
                        }

                        // step down:
                        if(y + 1 < GRID_SIZE){
                            if(grid[y + 1][x] == '1'){
                                if(duplicates.add((y + 1) + "," + (x))){
                                    queue.add(new int[]{y + 1, x});
                                }
                            }
                        }

                        //step left
                        if(x - 1 >= 0){
                            if(grid[y][x - 1] == '1'){
                                if(duplicates.add((y) + "," + (x - 1))){
                                    queue.add(new int[]{y, x - 1});
                                }
                            }
                        }
                        if(queue.isEmpty()){
                            clusters ++;
                        }
                    }
                }
            }
        }
        System.out.println(clusters);
    }

    private String hexToBinary(String hex) {
        StringBuilder binary = new StringBuilder();

        for (char c : hex.toCharArray()) {
            int value = Integer.parseInt(String.valueOf(c), 16);
            String bin = String.format("%4s", Integer.toBinaryString(value))
                    .replace(' ', '0');
            binary.append(bin);
        }

        return binary.toString();
    }

    // day 10 method
    public String knotHash(String input) {
        rope = new int[ROPE_SIZE];
        for (int i = 0; i < ROPE_SIZE; i++) {
            rope[i] = i;
        }

        List<Integer> lengths = new ArrayList<>();
        for (char c : input.toCharArray()) {
            lengths.add((int) c);
        }
        for (int s : SUFFIX) {
            lengths.add(s);
        }

        int currentPos = 0;
        int skipSize = 0;

        for (int round = 0; round < ROUNDS; round++) {
            for (int length : lengths) {
                tieKnot(currentPos, length);
                currentPos = (currentPos + length + skipSize) % ROPE_SIZE;
                skipSize++;
            }
        }

        StringBuilder hash = new StringBuilder();
        for (int i = 0; i < ROPE_SIZE; i += BLOCK_SIZE) {
            int xor = 0;
            for (int j = 0; j < BLOCK_SIZE; j++) {
                xor ^= rope[i + j];
            }
            hash.append(String.format("%02x", xor));
        }

        return hash.toString();
    }

    // same as day 10 method
    private void tieKnot(int currentPos, int length){
        // clear to follow approach:
        // prepare temporary reverse array first:
        int[] ropePiece = new int[length];
        int counter = 0;

        for (int i = currentPos; i < currentPos + length; i++) {
            ropePiece[length - 1 - counter] = rope[i % ROPE_SIZE];
            counter ++;
        }

        // switch the original rope piece with temporary array
        counter = 0;
        for (int i = currentPos; i < currentPos + length; i++) {
            rope[i % ROPE_SIZE] = ropePiece[counter];
            counter ++;
        }
    }
}
