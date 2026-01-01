package year2024.day21;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day21 {
    private static final ReadLines reader = new ReadLines(2024, 21, 2);
    private static final HashMap<String, Long> myMap = new HashMap<>();
    private static final HashMap<String, Long> nextMap = new HashMap<>();
    private static final ArrayList<String> instructions = new ArrayList<>();
    private static final ArrayList<Integer> numbers = new ArrayList<>();

    /**
     numpad:
     +---+---+---+
     | 7 | 8 | 9 |
     +---+---+---+
     | 4 | 5 | 6 |
     +---+---+---+
     | 1 | 2 | 3 |
     +---+---+---+
     | 0 | A |
     +---+---+

     keypad:
     +---+---+
     | ^ | A |
     +---+---+---+
     | < | v | > |
     +---+---+---+
     */

    private void prepare(){
        myMap.clear();
        nextMap.clear();
        instructions.clear();
        numbers.clear();

        List<String> input = reader.readFile();

        for (int i = 0; i < input.size(); i++) {
            instructions.add(input.get(i));

            StringBuilder numberString = new StringBuilder();
            for (int j = 0; j < input.get(i).length(); j++) {
                if(input.get(i).charAt(j) != 'A'){
                    if(!(input.get(i).charAt(j) == 'O' && j == 0)){
                        numberString.append(input.get(i).charAt(j));
                    }
                }
            }
            numbers.add(Integer.valueOf(numberString.toString()));
        }
    }

    public void part1(){
        prepare();
        int result = 0;
        for (int i = 0; i < instructions.size(); i++) {
            String firstString = numPad(instructions.get(i));
            String secondString = keyPad(firstString);
            String thirdString = keyPad(secondString);
            result = result + thirdString.length() * numbers.get(i);
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();

        long result = 0;
        int chainedKeyPads = 25;
        for (int j = 0; j < instructions.size(); j++) {
            String instruction = instructions.get(j);

            // numpad:
            String currentString = numPad(instruction);
            String[] instructionArray = makeArray(currentString);

            for (String s : instructionArray) {
                currentString = s;
                if (myMap.get(currentString) == null) {
                    myMap.put(currentString, 1L);
                } else {
                    myMap.put(currentString, myMap.get(currentString) + 1L);
                }
            }

            // keypad:
            int counter = 0;
            while(counter < chainedKeyPads){
                counter++;

                // put new values into "nextMap"
                nextMap.clear();
                for(HashMap.Entry<String, Long> entry : myMap.entrySet()){
                    sliceUpString(entry.getKey(), entry.getValue());
                }

                myMap.clear();
                // process next values into myMap
                for(HashMap.Entry<String, Long> entry : nextMap.entrySet()){
                    processStringIntoHashMap(entry.getKey(), entry.getValue());
                }
            }

            // get length:;
            for(HashMap.Entry<String, Long> entry : myMap.entrySet()){
                result = result + entry.getKey().length() * entry.getValue() * numbers.get(j);
            }
            myMap.clear();
        }
        System.out.println(result);
    }

    private static void sliceUpString(String s, long value){
        String[] myArray = makeArray(s);
        for (String currentString : myArray) {
            nextMap.merge(currentString, value, Long::sum);
        }
    }

    private static String[] makeArray(String s){
        ArrayList<String> helper = new ArrayList<>();
        while(!s.isEmpty()){
            for (int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == 'A'){
                    helper.add(s.substring(0,i + 1));
                    s = s.substring(i + 1);
                    break;
                }
            }
        }
        String[] result = new String[helper.size()];
        for (int i = 0; i < helper.size(); i++) {
            result[i] = helper.get(i);
        }
        return result;
    }

    private static void processStringIntoHashMap(String s, long value){
        if(myMap.get(s) == null){
            myMap.put(keyPad(s),value);
        } else {
            myMap.put(keyPad(s), myMap.get(s) + value);
        }
    }

    private static String numPad(String s) {
        int currentPosition = 0;
        StringBuilder toKeyPad = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int endPosition = getNumPadPos(s.charAt(i));
            toKeyPad.append(getInstructionsNumPad2(currentPosition, endPosition)).append("A");
            currentPosition = endPosition;
        }
        return toKeyPad.toString();
    }

    private static String keyPad(String s){
        char currentChar = 'A';
        StringBuilder fromKeyPadToKeyPad = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char endChar = s.charAt(i);
            fromKeyPadToKeyPad.append(getInstructionsKeyPad2(currentChar, endChar)).append("A");
            currentChar = endChar;
        }
        return fromKeyPadToKeyPad.toString();
    }

    private static int getNumPadPos(Character c){
        if(c == 'A'){
            return 0;
        } else if( c == '0'){
            return -1;
        } else return Integer.parseInt(String.valueOf(c));
    }

    private static String getInstructionsKeyPad(char current, char end){
        StringBuilder code = new StringBuilder();
        int currentRow = getKeyPadRow(current);
        int currentCol = getKeyPadCol(current);

        int endRow = getKeyPadRow(end);
        int endCol = getKeyPadCol(end);

        while (currentRow < endRow) {
            code.append("v"); // move down
            currentRow++;
        }

        while (currentCol > endCol) {
            code.append("<"); // move left
            currentCol--;
        }

        while (currentCol < endCol) {
            code.append(">"); // move right
            currentCol++;
        }

        while (currentRow > endRow) {
            code.append("^"); // move up
            currentRow --;
        }

        return code.toString();
    }

    private static String getInstructionsNumPad(int current, int end) {

        int currentRow = (int)(Math.ceil(current / 3.0));
        int currentCol = (current + 2) % 3;

        int endRow = (int)(Math.ceil(end / 3.0));
        int endCol = (end + 2) % 3;

        StringBuilder code = new StringBuilder();

        while (currentRow < endRow) {
            code.append("^"); // move up
            currentRow ++;
        }

        while (currentCol > endCol) {
            code.append("<"); // move left
            currentCol--;
        }

        while (currentCol < endCol) {
            code.append(">"); // move right
            currentCol++;
        }
        while (currentRow > endRow) {
            code.append("v"); // move down
            currentRow--;
        }

        return code.toString();
    }

    private static int getKeyPadRow(char c){
        if(c == '^' || c == 'A'){
            return 0;
        } else return 1;
    }

    private static int getKeyPadCol(char c){
        if(c == '<'){
            return 0;
        } else if (c == '^' || c == 'v'){
            return 1;
        } else return 2;
    }

    private static String getInstructionsKeyPad2(char current, char end){
        String s = getInstructionsKeyPad(current, end);
        if(current != '<' && end != '<'){
            switch (s) {
                case ">^" -> {
                    return "^>";
                }
                case "v<" -> {
                    return "<v";
                }
                case "^<" -> {
                    return "<^";
                }
                case ">v" -> {
                    return "v>";
                }
            }
        }
        return s;
    }

    private static String getInstructionsNumPad2(int current, int end){
        int currentRow = (int)(Math.ceil(current / 3.0));
        int currentCol = (current + 2) % 3;

        int endRow = (int)(Math.ceil(end / 3.0));
        int endCol = (end + 2) % 3;

        StringBuilder code = new StringBuilder();
        if((currentRow == 0 && endCol == 0) || (currentCol == 0 && endRow == 0)){
            return getInstructionsNumPad(current, end);
        } else {
            while (currentCol > endCol) {
                code.append("<"); // move left
                currentCol--;
            }

            while (currentRow > endRow) {
                code.append("v"); // move down
                currentRow--;
            }

            while (currentRow < endRow) {
                code.append("^"); // move up
                currentRow ++;
            }

            while (currentCol < endCol) {
                code.append(">"); // move right
                currentCol++;
            }
        }
        return code.toString();
    }
}