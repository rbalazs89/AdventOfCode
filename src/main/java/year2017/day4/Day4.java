package year2017.day4;

import main.ReadLines;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4 {
    //setup:
    private final ReadLines reader = new ReadLines(2017, 4, 2);
    private List<String> fileLines; // lines from txt file

    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();

        int counter = 0;

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            String[] parts = line.split(" ");
            Set<String> words = new HashSet<>();

            boolean isOkPasscode = true;
            for (String part : parts) {
                if (!words.add(part)) {
                    isOkPasscode = false;
                    break;
                }
            }
            if(isOkPasscode){
                counter ++;
            }
        }
        System.out.println(counter);
    }

    public void part2(){
        readData();

        int counter = 0;

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            String[] parts = line.split(" ");
            Set<String> words = new HashSet<>();
            boolean isOkPasscode = true;
            for (String part : parts) {
                String rearranged = sortLetters(part);
                if (!words.add(rearranged)) {
                    isOkPasscode = false;
                    break;
                }
            }
            if(isOkPasscode){
                counter ++;
            }
        }
        System.out.println(counter);
    }

    private String sortLetters(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
