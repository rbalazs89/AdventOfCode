package year2016.day23;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day23 {
    private final ArrayList<Register> registers = new ArrayList<>();
    private final ArrayList<Instruction> instructions = new ArrayList<>();
    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 23, 2);
    private static final int startingValueRegisterA = 7; // specified in task description
    private static final int startingValueRegisterApart2 = 12; // specified in task description

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        registers.clear();
        instructions.clear();
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Pattern p = Pattern.compile(" [a-z]");
            Matcher m = p.matcher(line);
            if(m.find()){
                addRegister(m.group().substring(1,2));
            }
        }

        for (int i = 0; i < fileLines.size(); i++) {
            Instruction instruction = new Instruction();
            String[] parts = fileLines.get(i).split(" ");
            if (parts[0].equals("cpy")) {
                instruction.type = "cpy";
                if (Character.isDigit(parts[1].charAt(parts[1].length() - 1))) {
                    instruction.firstArgumentInteger = Integer.parseInt(parts[1]);
                } else {
                    instruction.firstArgumentRegister = getRegister(parts[1]);
                }
                instruction.secondArgumentRegister = getRegister(parts[2]);

            } else if (parts[0].equals("inc")) {
                instruction.type = "inc";
                instruction.firstArgumentRegister = getRegister(parts[1]);

            } else if (parts[0].equals("dec")) {
                instruction.type = "dec";
                instruction.firstArgumentRegister = getRegister(parts[1]);

            } else if (parts[0].equals("jnz")) {
                instruction.type = "jnz";
                if (Character.isDigit(parts[1].charAt(parts[1].length() - 1))) {
                    instruction.firstArgumentInteger = Integer.parseInt(parts[1]);
                } else {
                    instruction.firstArgumentRegister = getRegister(parts[1]);
                }

                if (Character.isDigit(parts[2].charAt(parts[2].length() - 1))) {
                    instruction.secondArgumentInteger = Integer.parseInt(parts[2]);
                } else {
                    instruction.secondArgumentRegister = getRegister(parts[2]);
                }

            } else if (parts[0].equals("tgl")){
                instruction.type = "tgl";
                if (Character.isDigit(parts[1].charAt(parts[1].length() - 1))) {
                    instruction.firstArgumentInteger = Integer.parseInt(parts[1]);
                } else {
                    instruction.firstArgumentRegister = getRegister(parts[1]);
                }
            } else {
                System.out.println("problem");
            }
            instructions.add(instruction);
        }
    }

    public void part1(){
        prepare();
        getRegister("a").value = startingValueRegisterA;

        for (int i = 0; i < instructions.size(); i++) {
            int value = processInstruction(instructions.get(i), i);
            i = i + value;
        }

        System.out.println(getRegister("a").value);
    }

    public void part2(){
        prepare();
        getRegister("a").value = startingValueRegisterApart2;

        for (int i = 0; i < instructions.size(); i++) {
            int value = processInstruction(instructions.get(i), i);
            i = i + value;
        }

        System.out.println(getRegister("a").value);
    }

    private void addRegister(String name){
        for (int i = 0; i < registers.size(); i++) {
            if(registers.get(i).name.equals(name)){
                return;
            }
        }
        Register reg = new Register();
        reg.name = name;
        registers.add(reg);
    }

    private Register getRegister(String name){
        for (int i = 0; i < registers.size(); i++) {
            if(registers.get(i).name.equals(name)){
                return registers.get(i);
            }
        }
        throw new IllegalStateException(
                "Register " + name + "not found."
        );
    }

    private int processInstruction(Instruction instruction, int index){
        if(!instruction.valid){
            return 0;
        }
        if(instruction.type.equals("cpy")){
            int valueToCopy;

            if(instruction.firstArgumentRegister == null){
                valueToCopy = instruction.firstArgumentInteger;
            } else {
                valueToCopy = instruction.firstArgumentRegister.value;
            }
            instruction.secondArgumentRegister.value = valueToCopy;
            return 0;

        } else if (instruction.type.equals("inc")){
            instruction.firstArgumentRegister.value ++;
            return 0;

        } else if (instruction.type.equals("dec")){
            instruction.firstArgumentRegister.value --;
            return 0;

        } else if (instruction.type.equals("jnz")){
            int integerToCompare;
            if(instruction.firstArgumentRegister == null){
                integerToCompare = instruction.firstArgumentInteger;
            } else {
                integerToCompare = instruction.firstArgumentRegister.value;
            }

            int jumpValue;
            if(instruction.secondArgumentRegister == null){
                jumpValue = instruction.secondArgumentInteger;
            } else {
                jumpValue = instruction.secondArgumentRegister.value;
            }

            if(integerToCompare != 0){
                return jumpValue - 1;
            } else {
                return 0;
            }

        } else if (instruction.type.equals("tgl")){

            int toModify = index + instruction.firstArgumentRegister.value;
            if(toModify >= instructions.size()){
                return 0;
            }

            Instruction instructionToModify = instructions.get(toModify);

            if(instructionToModify.type.equals("inc")){
                instructionToModify.type = "dec";
            }
            else if(instructionToModify.type.equals("dec")){
                instructionToModify.type = "inc";
            }
            else if (instructionToModify.type.equals("tgl")){
                instructionToModify.type = "inc";
            }
            else if(instructionToModify.type.equals("jnz")){
                instructionToModify.type = "cpy";
                if(instructionToModify.secondArgumentInteger != null){
                    instructionToModify.valid = false;
                    return 0;
                }
            }
            else if(instructionToModify.type.equals("cpy")) {
                instructionToModify.type = "jnz";
                instructionToModify.valid = true;
            }
            return 0;
        } else {
            throw new IllegalStateException(
                    "Instruction processing went wrong"
            );
        }
    }
}
