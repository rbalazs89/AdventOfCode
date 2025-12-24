package year2017.day16;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day16 {
    // setup:
    private final ReadLines reader = new ReadLines(2017, 16, 2);
    private List<String> fileLines; // Lines from txt file

    //puzzle:
    private final ArrayList<Instruction> danceInstructions = new ArrayList<>();
    private static final int maxProgram = 16; // specified by task description
    private static final int wildDanceMultiplier = 1000000000; // specified by task description

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();

        danceInstructions.clear();

        String[] fileParts = fileLines.getFirst().split(",");
        for (int i = 0; i < fileParts.length; i++) {

            // s1,x3/4,pe/b <--- example instruction
            Instruction instruction;
            String current = fileParts[i].substring(1);
            char c = fileParts[i].charAt(0);
            if( c == 's'){
                instruction = new Instruction(c, Integer.valueOf(current), null, null, null);
            } else if (c == 'x'){
                String[] parts = current.split("/");
                instruction = new Instruction(c, Integer.valueOf(parts[0]), null, Integer.valueOf(parts[1]), null);
            } else if (c == 'p'){
                String[] parts = current.split("/");
                instruction = new Instruction(c, null, parts[0].charAt(0), null, parts[1].charAt(0));
            } else {
                throw new IllegalStateException(" Dance instruction not recognized");
            }
            danceInstructions.add(instruction);
        }
    }

    public void part1(){
        // get instructions
        prepare();

        // Fill the original dance floor with characters
        char[] programs = fillDanceFloor();

        // Apply instructions to the dance floor:
        for (int i = 0; i < danceInstructions.size(); i++) {
            programs = applyInstruction(danceInstructions.get(i), programs);
        }

        // Print result:
        System.out.println(programs);
    }

    public void part2(){
        prepare();

        char[] programs = fillDanceFloor();

        // arbitrary number to have cycle stabilize:
        int stabilize = 100;
        String savedDanceFloor = "";

        // save cycle and rounds done variables from the loop
        int cycle = 0;
        int roundsDone = 0;

        // run dances until stable cycle is found
        for (int j = 0; j < stabilize * 100; j++) {
            for (int i = 0; i < danceInstructions.size(); i++) {
                programs = applyInstruction(danceInstructions.get(i), programs);
            }
            if(j == stabilize){
                savedDanceFloor = Arrays.toString(programs);
            }
            if(j > stabilize){
                if(savedDanceFloor.equals(Arrays.toString(programs))){
                    cycle = j - stabilize;
                    roundsDone = j;
                    break;
                }
            }
        }

        // Calculate remaining rounds, to skip the huge cycles given by task description
        int remainingRounds = (wildDanceMultiplier - roundsDone) % cycle;

        // Apply the remaining rounds:
        for (int j = 0; j < remainingRounds - 1; j++) {
            for (int i = 0; i < danceInstructions.size(); i++) {
                programs = applyInstruction(danceInstructions.get(i), programs);
            }
        }

        // Print out result
        for (int i = 0; i < programs.length; i++) {
            System.out.print(programs[i]);
        }
        System.out.println();
    }

    // fill dance floor 'a' -> 'p'
    private char[] fillDanceFloor(){
        char[] c = new char[maxProgram];
        for (int i = 0; i < maxProgram; i++) {
            c[i] = (char)(97 + i);
        }
        return c;
    }

    // Apply an instruction to dance floor
    private char[] applyInstruction(Instruction instruction, char[] danceFloor){
        switch (instruction.type()){
            case 's':{
                danceFloor = spinDance(danceFloor, instruction.firstArgumentInt());
                break;
            }
            case 'x':{
                swapProgram(danceFloor, instruction.firstArgumentInt(), instruction.secondArgumentInt());
                break;
            }
            case 'p' : {
                int firstPosition = findProgram(danceFloor, instruction.firstArgumentChar());
                int secondPosition = findProgram(danceFloor, instruction.secondArgumentChar());
                swapProgram(danceFloor, firstPosition, secondPosition);
                break;
            }
            default: throw new IllegalStateException("Dance instruction not recognized");
        }
        return danceFloor;
    }

    private int findProgram(char[] floor, char c){
        for (int i = 0; i < floor.length; i++) {
            if(floor[i] == c){
                return i;
            }
        }
        throw new IllegalStateException("Program not found on the dance floor");
    }

    // Helper method to swap character
    private void swapProgram(char[] floor, int one, int two){
        char temp = floor[one];
        floor[one] = floor[two];
        floor[two] = temp;
    }

    // Applies the spin instruction and returns new char array of the result
    private char[] spinDance(char[] floor, int n){
        if(n == maxProgram){
            return floor;
        }
        char[] partTwo = Arrays.copyOf(floor, maxProgram - n);
        char[] newFloor = new char[maxProgram];

        for (int i = 0; i < n; i++) {
            newFloor[i] = floor[maxProgram - n + i];
        }

        for (int i = 0; i < partTwo.length; i++) {
            newFloor[n + i] = partTwo[i];
        }

        return newFloor;
    }
}
