package year2018.day5;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Collections;

public class Day5 {
    private final ReadLines reader = new ReadLines(2018, 5, 2);
    private static final int DELTA = Math.abs((int)('a') - (int)'A');

    public void part1(){
        String input = reader.readFile().getFirst(); // one line input
        System.out.println(getReactedPolymerLength(input));
    }

    public void part2(){
        String originalString = reader.readFile().getFirst();
        ArrayList<Integer> lengths = new ArrayList<>();

        for (int i = 'a'; i <= 'z'; i++) {
            lengths.add(getReactedPolymerLength(removeUnitFromString((char)i, originalString)));
        }

        Collections.sort(lengths);
        System.out.println(lengths.getFirst());
    }

    // gives back the reacted polymer's length
    private int getReactedPolymerLength(String input){
        StringBuilder polymer = new StringBuilder(input);

        for (int i = 0; i < polymer.length() - 1;) {
            if(Math.abs(polymer.charAt(i) - polymer.charAt(i + 1)) == DELTA){
                polymer.delete(i, i + 2);
                i = Math.max(i - 1, 0);
            } else {
                i++;
            }
        }
        return polymer.length();
    }

    // removes all both lower and uppercase instances of a char from the string
    private String removeUnitFromString(char c, String input){
        StringBuilder polymer = new StringBuilder(input);
        int unitOne = Character.toLowerCase(c);
        int unitTwo = Character.toUpperCase(c);

        for (int i = 0; i < polymer.length();) {
            if(polymer.charAt(i) == unitOne || polymer.charAt(i) == unitTwo){
                polymer.delete(i, i + 1);
            } else {
                i++;
            }
        }
        return polymer.toString();
    }
}
