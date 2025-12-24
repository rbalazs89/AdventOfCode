package year2016.day21;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {
    private final ReadLines reader = new ReadLines(2016, 21, 2);
    private List<String> fileLines; // lines from txt file

    private static final String startingPassword = "abcdefgh"; // starting password from task description
    private static final String endingPassword = "fbgdceah"; // ending password for part 2 to unscramble
    private boolean prepared = false;
    ArrayList<Instruction> instructions = new ArrayList<>();

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare() {
        if (prepared) {
            return;
        }

        readData();

        Pattern p = Pattern.compile("\\d");

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);
            Instruction instruction = new Instruction();
            String[] parts = fileLines.get(i).split(" ");

            if(line.contains("swap") && line.contains("position")){
                instruction.type = 1;
                while(m.find()){
                    if(instruction.pos1 == -1){
                        instruction.pos1 = Integer.parseInt(m.group());
                    } else {
                        instruction.pos2 = Integer.parseInt(m.group());
                    }
                }
            }
            else if (line.contains("swap") && line.contains("letter")){
                instruction.type = 2;
                for (String part : parts) {
                    if (part.length() == 1) {
                        if (instruction.letter1 == '\u0000') {
                            instruction.letter1 = part.charAt(0);
                        } else {
                            instruction.letter2 = part.charAt(0);
                            break;
                        }
                    }
                }
            }
            else if (line.contains("rotate") && line.contains("step")){
                instruction.type = 3;
                if(line.contains("right")){
                    instruction.direction = "right";

                } else {
                    instruction.direction = "left";
                }
                while(m.find()) {
                    instruction.rotate = Integer.parseInt(m.group());
                }
            }
            else if (line.contains("rotate") && line.contains("position")){
                instruction.type = 4;
                for (int j = 0; j < parts.length; j++) {
                    if(parts[j].length() == 1){
                        instruction.letter1 = parts[j].charAt(0);
                        break;
                    }
                }

            }
            else if (line.contains("reverse")){
                instruction.type = 5;
                while(m.find()){
                    if(instruction.pos1 == -1){
                        instruction.pos1 = Integer.parseInt(m.group());
                    } else {
                        instruction.pos2 = Integer.parseInt(m.group());
                    }
                }
            }
            else if (line.contains("move")){
                instruction.type = 6;
                while(m.find()){
                    if(instruction.pos1 == -1){
                        instruction.pos1 = Integer.parseInt(m.group());
                    } else {
                        instruction.pos2 = Integer.parseInt(m.group());
                    }
                }
            }
            instructions.add(instruction);
        }

        prepared = true;
    }

    public void part1(){
        prepare();
        String currentPassword = startingPassword;
        for (int i = 0; i < instructions.size(); i++) {
            currentPassword = doInstruction(currentPassword, instructions.get(i));
        }
        System.out.println(currentPassword);
    }

    private String doInstruction(String input, Instruction instruction){
        switch (instruction.type){
            case 1:
                input = reverse(input, instruction.pos1, instruction.pos2);
                break;

            case 2:
                int pos1 = input.indexOf(instruction.letter1);
                int pos2 = input.indexOf(instruction.letter2);
                input = reverse(input, pos1, pos2);
                break;

            case 3:
                if(instruction.direction.equals("right")){
                    for (int i = 0; i < instruction.rotate; i++) {
                        input = rotateRight(input);
                    }
                } else {
                    for (int i = 0; i < instruction.rotate; i++) {
                        input = rotateLeft(input);
                    }
                }
                break;

            case 4:
                int index = input.indexOf(instruction.letter1);
                if(index >= 4){
                    index ++;
                }
                int rotate = index + 1;
                for (int i = 0; i < rotate; i++) {
                    input = rotateRight(input);
                }
                break;

            case 5:
                int left = Math.min(instruction.pos1,instruction.pos2);
                int right = Math.max(instruction.pos2, instruction.pos1);
                while (left < right){
                    input = reverse(input, left, right);
                    right --;
                    left ++;
                }
                break;

            case 6:
                input = moveChar(input, instruction.pos1, instruction.pos2);
                break;

        }
        return input;
    }

    public void part2(){
        prepare();
        String currentPassword = endingPassword;
        for (int i = instructions.size() - 1; i >= 0; i--) {
            currentPassword = doReverseInstruction(currentPassword, instructions.get(i));
        }

        System.out.println(currentPassword);

    }

    private String doReverseInstruction(String input, Instruction instruction){
        switch (instruction.type){
            case 1:
                // same as original
                input = reverse(input, instruction.pos1, instruction.pos2);
                break;

            case 2:
                // same as original
                int pos1 = input.indexOf(instruction.letter1);
                int pos2 = input.indexOf(instruction.letter2);
                input = reverse(input, pos1, pos2);
                break;

            case 3:
                // right and left swapped compared to original
                if(instruction.direction.equals("right")){
                    for (int i = 0; i < instruction.rotate; i++) {
                        input = rotateLeft(input);
                    }
                } else {
                    for (int i = 0; i < instruction.rotate; i++) {
                        input = rotateRight(input);
                    }
                }
                break;

            case 4:
                // special handling
                input = do4thInstructionReverse(input, instruction.letter1);
                break;

            case 5:
                // same as original
                int left = Math.min(instruction.pos1,instruction.pos2);
                int right = Math.max(instruction.pos2, instruction.pos1);
                while (left < right){
                    input = reverse(input, left, right);
                    right --;
                    left ++;
                }
                break;

            case 6:
                // switch pos1 and pos2, other than that, same as original
                input = moveChar(input, instruction.pos2, instruction.pos1);
                break;

        }
        return input;
    }

    private String rotateRight(String input){
        int length = input.length();
        char hold = input.charAt(length - 1);
        for (int i = length - 1; i > 0; i --) {
            char temp = input.charAt(i - 1);
            input = input.substring(0, i) + temp + input.substring(i + 1);
        }
        input = hold + input.substring(1);
        return input;
    }

    private String rotateLeft(String input){
        int length = input.length();
        char hold = input.charAt(0);
        for (int i = 0; i < length - 1; i++) {
            char temp = input.charAt(i + 1);
            input = input.substring(0, i) + temp + input.substring(i + 1);
        }
        input = input.substring(0, input.length() - 1) + hold;
        return input;
    }

    private String reverse(String input, int p1, int p2){
        char hold1 = input.charAt(p1);
        char hold2 = input.charAt(p2);
        input = input.substring(0, p1) + hold2 + input.substring(p1 + 1);
        input = input.substring(0, p2) + hold1 + input.substring(p2 + 1);
        return input;
    }

    private String moveChar(String input, int p1, int p2){
        if(p1 < p2){
            input = input.substring(0, p1) + input.substring(p1 + 1, p2 + 1) + input.charAt(p1) + input.substring(p2 + 1);
        } else {
            input = input.substring(0, p2) + input.charAt(p1) + input.substring(p2, p1) + input.substring(p1 + 1);
        }
        return input;
    }

    private String do4thInstruction(String input, char letter){
        int index = input.indexOf(letter);
        if(index >= 4){
            index ++;
        }
        int rotate = index + 1;
        for (int i = 0; i < rotate; i++) {
            input = rotateRight(input);
        }
        return input;
    }

    private String doLeftRotateByNTimes(String input, int n){
        for (int i = 0; i < n; i++) {
            input = rotateLeft(input);
        }
        return input;
    }

    private String do4thInstructionReverse(String input, char letter){
        String firstString = input;
        for (int i = 0; i < input.length(); i++) {
            firstString = doLeftRotateByNTimes(firstString, i);
            firstString = do4thInstruction(firstString, letter);
            if(firstString.equals(input)){
                return doLeftRotateByNTimes(firstString, i);
            }
            firstString = input;
        }
        throw new IllegalStateException("No reverse found");
    }
}
