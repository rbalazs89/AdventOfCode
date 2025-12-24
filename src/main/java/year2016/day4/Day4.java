package year2016.day4;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    private List<String> fileLines;
    private final ArrayList<Letter> letters = new ArrayList<>();

    private final ReadLines reader = new ReadLines(2016, 4, 2);
    private void readData(){
        // READ INPUT
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();

        int sum = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            String current = fileLines.get(i);

            for (int j = 0; j < current.length(); j++) {
                char currentChar = current.charAt(j);
                if(Character.isDigit(currentChar)){
                    break;
                }
                if(currentChar != '-'){
                    addMethod(currentChar); // adds to letters or increases frequency
                }
            }

            Collections.sort(letters, new Comparator<Letter>() {
                @Override
                public int compare(Letter l1, Letter l2) {
                    // Compare l2.freq with l1.freq to sort in descending order
                    // (higher frequency comes first)
                    if(l2.freq > l1.freq){
                        return 1;
                    } else if (l2.freq < l1.freq){
                        return -1;
                    } else {
                        if(l2.c > l1.c){
                            return -1;
                        }
                        else if (l2.c < l1.c){
                            return 1;
                        }
                        else {
                            return 0;
                        }
                    }
                }
            });

            int index = current.indexOf("[");
            String fiveLetters = current.substring(index + 1, current.length() - 1);
            StringBuilder constructed = new StringBuilder();
            for (int j = 0; j < letters.size(); j++) {
                constructed.append(letters.get(j).c);
                if(j == 4){
                    break;
                }
            }
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(current);
            int sectorID = 0;
            while (m.find()){
                sectorID = Integer.parseInt(m.group());
            }

            if(fiveLetters.contentEquals(constructed)){
                sum = sum + sectorID;
            }
            letters.clear();
        }
        System.out.println(sum);
    }

    private void addMethod(char c){
        for (int i = 0; i < letters.size(); i++) {
            if(letters.get(i).c == c){
                letters.get(i).freq ++;
                return;
            }
        }
        Letter letter = new Letter();
        letter.c = c;
        letter.freq = 1;
        letters.add(letter);
    }

    public void part2(){
        for (int i = 0; i < fileLines.size(); i++) {
            String current = fileLines.get(i);

            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(current);
            int key = 0;
            while (m.find()){
                key = Integer.parseInt(m.group());
            }
            String sectorID = String.valueOf(key);

            int index = 0;
            for (int j = 0; j < current.length(); j++) {
                char currentC = current.charAt(j);
                if(Character.isDigit(currentC)){
                    index = j;
                    break;
                }
            }
            current = current.substring(0, index);
            key = key % 26;

            // a -> 97
            // z -> 97 + 26 = 122
            StringBuilder construct = new StringBuilder();
            for (int j = 0; j < current.length(); j++) {
                if(current.charAt(j) != '-'){
                    char currentChar = current.charAt(j);
                    currentChar = (char)(currentChar - 97 + key);
                    currentChar = (char)(currentChar % 26);
                    currentChar = (char)(currentChar + 97);
                    construct.append(currentChar);
                } else {
                    construct.append(current.charAt(j));
                }
            }
            if(construct.toString().contains("northpole")){
                System.out.println(sectorID);
                break;
            }
        }
    }
}
