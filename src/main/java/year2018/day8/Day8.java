package year2018.day8;

import main.ReadLines;

public class Day8 {
    private final ReadLines reader = new ReadLines(2018, 7, 2);

    private int[] getTree(){
        String line = reader.readFile().getFirst(); // this puzzle has one line input
        String[] parts = line.split(" ");
        int[] tree = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            tree[i] = Integer.parseInt(parts[i]);
        }

        return tree;
    }

    public void part1(){
        int[] tree = getTree();

    }

    public void part2(){

    }
}
