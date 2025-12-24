package year2017.day10;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    // setup:
    private final ReadLines reader = new ReadLines(2017, 10, 2);
    private List<String> fileLines; // lines from txt file
    private boolean prepared = false;

    // puzzle:
    private static final int ROPE_SIZE = 256; // hash size from task description
    private int[] rope;
    private ArrayList<Integer> knots;

    // part2: constants specified by task description
    private static final int ROUNDS = 64;
    private static final int BLOCK_SIZE = 16;
    private static final int[] SUFFIX = {17, 31, 73, 47, 23};

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare() {
        readData();
        prepareKnotInstructions();
        prepareRope();
        prepared = true;
    }

    private void prepareKnotInstructions(){
        knots = new ArrayList<>();
        Pattern p = Pattern.compile("\\d+");
        String line = fileLines.getFirst();
        Matcher m = p.matcher(line);
        while(m.find()){
            knots.add(Integer.valueOf(m.group()));
        }
    }

    private void prepareRope(){
        rope = new int[ROPE_SIZE];
        for (int i = 0; i < ROPE_SIZE; i++) {
            rope[i] = i;
        }
    }

    public void part1(){
        prepare();

        int currentPos = 0;
        int skipSize = 0;

        // apply knot instructions
        for (int knot : knots) {
            tieKnot(currentPos, knot); // modifies field variable rope
            currentPos += knot + skipSize;
            skipSize ++;
            currentPos = currentPos % ROPE_SIZE;
        }
        System.out.println(rope[0] * rope[1]);
    }

    public void part2(){
        prepare();

        String input = fileLines.getFirst();

        // convert each character to its ascii code
        List<Integer> lengths = new ArrayList<>();
        for (char c : input.toCharArray()) {
            lengths.add((int) c);
        }

        // add the standard suffix
        for (int s : SUFFIX) {
            lengths.add(s);
        }

        int currentPos = 0;
        int skipSize = 0;

        // apply knot instructions repeat times, same as part 1
        for (int round = 0; round < ROUNDS; round++) {
            for (int length : lengths) {
                tieKnot(currentPos, length);
                currentPos += length + skipSize;
                currentPos = currentPos % ROPE_SIZE;
                skipSize++;
            }
        }

        // convert to block according to task description, 16 x 16 items, then apply XOR on each element of each block
        List<Integer> blocks = new ArrayList<>();
        for (int i = 0; i < ROPE_SIZE; i += BLOCK_SIZE) {
            int xor = 0;
            for (int j = 0; j < BLOCK_SIZE; j++) {
                xor ^= rope[i + j];
            }
            blocks.add(xor);
        }

        // apply format
        StringBuilder sb = new StringBuilder();
        for (int num : blocks) {
            sb.append(String.format("%02x", num));
        }
        System.out.println(sb);

    }

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

    /**
     * debug only
     * prints the current state of the rope for visualization purposes.
     * not used in the final solution.
     */
    void printCurrentState(int currentPosition){
        if(!prepared){
            System.out.println("Nothing to draw, prepare task first");
            return;
        }

        for (int i = 0; i < rope.length; i++) {
            if(i != currentPosition){
                System.out.print(rope[i] + " ");
            } else {
                System.out.print("[" + rope[i] + "] ");
            }

        }
        System.out.println();
    }
}
