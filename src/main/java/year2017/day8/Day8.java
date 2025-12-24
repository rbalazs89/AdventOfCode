package year2017.day8;

import main.ReadLines;
import java.util.ArrayList;
import java.util.List;

public class Day8 {
    // setup:
    private final ReadLines reader = new ReadLines(2017, 8, 2);
    private List<String> fileLines; // lines from txt file, updated once

    // puzzle:
    private final ArrayList<Register> registers = new ArrayList<>();
    private final ArrayList<Instruction> instructions = new ArrayList<>();

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        registers.clear();
        instructions.clear();

        // example line: c inc -20 if c == 10
        for (int i = 0; i < fileLines.size(); i++) {
            String[] parts = fileLines.get(i).split(" ");

            // creates all instructions
            instructions.add(new Instruction(
                    getOrCreateRegister(parts[0]),   // affected register
                    parts[1],                       // instruction type
                    Integer.parseInt(parts[2]),     // affecting number
                    getOrCreateRegister(parts[4]),    // compared register
                    parts[5],                       // comparison type
                    Integer.parseInt(parts[6])));   // compared number
        }
    }

    public void part1(){
        prepare();

        // run all instructions on registers
        for (int i = 0; i < instructions.size(); i++) {
            applyInstruction(instructions.get(i));
        }

        // get highest value after all instructions ran
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < registers.size(); i++) {
            maxValue = Math.max(maxValue, registers.get(i).getValue());
        }
        System.out.println(maxValue);

    }

    public void part2(){
        prepare();
        clearRegisters();

        // run all instructions while always checking for the highest value
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < instructions.size(); i++) {
            Instruction current = instructions.get(i);
            applyInstruction(current);
            maxValue = Math.max(maxValue, current.affectedRegister().getValue());
        }

        System.out.println(maxValue);
    }

    private void applyInstruction(Instruction instruction){
        // determine if instruction effect true
        boolean effect;
        switch (instruction.comparisonType()){
            case "==" :
                effect = instruction.comparedRegister().getValue() == instruction.comparisonNumber();
                break;

            case "<" :
                effect = instruction.comparedRegister().getValue() < instruction.comparisonNumber();
                break;

            case "<=" :
                effect = instruction.comparedRegister().getValue() <= instruction.comparisonNumber();
                break;

            case ">" :
                effect = instruction.comparedRegister().getValue() > instruction.comparisonNumber();
                break;

            case ">=" :
                effect = instruction.comparedRegister().getValue() >= instruction.comparisonNumber();
                break;

            case "!=" :
                effect = instruction.comparedRegister().getValue() != instruction.comparisonNumber();
                break;

            default:
                throw new IllegalStateException(instruction.comparisonType() + " comparison type not processed");
        }
        if(!effect){
            return;
        }

        // apply effect if true
        switch (instruction.instructionType()){
            case "inc" :
                instruction.affectedRegister().setValue(
                        instruction.affectedRegister().getValue() + instruction.affectingNumber());
                break;

            case "dec":
                instruction.affectedRegister().setValue(
                        instruction.affectedRegister().getValue() - instruction.affectingNumber());
                break;
            default:
                throw new IllegalStateException(instruction.instructionType() + " instruction type not processed");
        }
    }


    // adds register if not exists yet and returns it
    // returns if already exists
    private Register getOrCreateRegister(String name){
        for (int i = 0; i < registers.size(); i++) {
            Register current = registers.get(i);
            if(current.getName().equals(name)){
                return current;
            }
        }

        Register reg = new Register(name);
        registers.add(reg);
        return reg;
    }

    // sets all registers to original value
    private void clearRegisters(){
        for (int i = 0; i < registers.size(); i++) {
            registers.get(i).setValue(Register.startingValue);
        }
    }
}
