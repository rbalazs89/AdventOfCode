package year2017.day18;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day18 {
    // setup:
    private final ReadLines reader = new ReadLines(2017, 18, 2);
    private List<String> fileLines; // Lines from txt file

    // part2:
    private Program program0;
    private Program program1;

    private void readData() {
        fileLines = reader.readFile();
    }

    private Program prepareInstructionsAndRegisters(){
        ArrayList<Instruction> instructions = new ArrayList<>();
        ArrayList<Register> registers = new ArrayList<>();
        Program program = new Program(false, registers, instructions);

        program.instructions = instructions;
        program.registers = registers;

        readData();

        // catch registers first:
        for (int i = 0; i < fileLines.size(); i++) {
            String name = fileLines.get(i).split(" ")[1];
            if(!Character.isDigit(name.charAt(0)) && !name.startsWith("-")){
                addRegister(program, name);
            }
        }

        // add instructions
        for (int i = 0; i < fileLines.size(); i++) {
            String[] parts = fileLines.get(i).split(" ");
            String name = parts[0];
            Long longOne = null;
            Register registerOne = null;
            Long longTwo = null;
            Register registerTwo = null;

            if(Character.isDigit(parts[1].charAt(0)) || parts[1].startsWith("-")){
                longOne = Long.parseLong(parts[1]);
            } else {
                registerOne = getRegisterByName(program, parts[1]);
            }

            if(parts.length != 3){
                instructions.add(new Instruction(longOne, registerOne, longTwo, registerTwo, name));
                continue;
            }

            if(Character.isDigit(parts[2].charAt(0)) || parts[2].startsWith("-")){
                longTwo = Long.parseLong(parts[2]);
            } else {
                registerTwo = getRegisterByName(program, parts[2]);
            }
            instructions.add(new Instruction(longOne, registerOne, longTwo, registerTwo, name));
        }
        return program;
    }


    public void part1(){
        Program program = prepareInstructionsAndRegisters();
        ArrayList<Instruction> instructions = program.instructions;

        for (int i = 0; i < instructions.size(); i++) {
            i = i + (int)(applyInstructionPart1(program, i));
            if(program.sentCounter > 0){
                break;
            }
        }
        System.out.println(program.part1Result);
    }

    public void part2(){
        // set up according to task description:
        program0 = prepareInstructionsAndRegisters();
        program0.IDNumber = 0;

        program1 = prepareInstructionsAndRegisters();
        program1.IDNumber = 1;
        Register register = getRegisterByName(program1, "p");
        register.setValue(1L);

        int counter = 0; // ensures more loop is executed if both programs are waiting
        while(counter < 3){
            if(program1.isWaiting && program0.isWaiting){
                counter ++;
            } else {
                counter = 0;
            }

            applyInstructionPart2(program1, program1.executionIndex); // can set both programs waiting status
            applyInstructionPart2(program0, program0.executionIndex); // can set both programs waiting status
        }
        System.out.println(program1.sentCounter);
    }

    private void applyInstructionPart2(Program program, int index){
        if(program.isWaiting){
            return;
        }
        Instruction instruction = program.instructions.get(index);
        switch (instruction.type) {
            case "set": {
                Long number;
                if(instruction.regAttributeTwo != null){
                    number = instruction.regAttributeTwo.getValue();
                } else {
                    number = instruction.longAttributeTwo;
                }
                instruction.regAttributeOne.setValue(number);
                program.executionIndex++;
                break;
            }

            case "add":{
                Long number;
                if(instruction.regAttributeTwo != null){
                    number = instruction.regAttributeTwo.getValue();
                } else {
                    number = instruction.longAttributeTwo;
                }

                instruction.regAttributeOne.setValue(instruction.regAttributeOne.getValue() + number);
                program.executionIndex++;
                break;
            }

            case "mul":{
                Long firstNumber = instruction.regAttributeOne.getValue();
                Long secondNumber;
                if(instruction.regAttributeTwo != null){
                    secondNumber = instruction.regAttributeTwo.getValue();
                } else {
                    secondNumber = instruction.longAttributeTwo;
                }
                instruction.regAttributeOne.setValue(firstNumber * secondNumber);
                program.executionIndex ++;
                break;
            }

            case "mod":{
                Long firstNumber = instruction.regAttributeOne.getValue();
                Long secondNumber;
                if(instruction.regAttributeTwo != null){
                    secondNumber = instruction.regAttributeTwo.getValue();
                } else {
                    secondNumber = instruction.longAttributeTwo;
                }
                instruction.regAttributeOne.setValue(firstNumber % secondNumber);
                program.executionIndex ++;
                break;
            }

            case "snd": {
                    if(program.IDNumber == 1){

                        // program 1 sends to program 0:
                        program.sendValue(program0, instruction.regAttributeOne.getValue());
                        program0.isWaiting = false;
                    } else {

                        // program 0 sends to program 1:
                        program.sendValue(program1, instruction.regAttributeOne.getValue());
                        program1.isWaiting = false;
                    program.executionIndex ++;
                    break;
                }
                program.executionIndex ++;
                break;
            }

            case "rcv": {
                if (program.receivedQueue.isEmpty()) {
                    program.isWaiting = true;
                } else {
                    Long received = program.receivedQueue.poll();
                    instruction.regAttributeOne.setValue(received);
                    program.executionIndex++;
                }
                break;
            }

            case "jgz": {
                Long firstNumber;
                if(instruction.regAttributeOne != null){
                    firstNumber = instruction.regAttributeOne.getValue();
                } else {
                    firstNumber = instruction.longAttributeOne;
                }

                Long secondNumber;
                if(instruction.regAttributeTwo != null){
                    secondNumber = instruction.regAttributeTwo.getValue();
                } else {
                    secondNumber = instruction.longAttributeTwo;
                }

                if(firstNumber > 0){
                    program.executionIndex = program.executionIndex + secondNumber.intValue();
                    break;
                }
                program.executionIndex ++;
                break;
            }
            default: throw new IllegalStateException("Instruction not recognized, name:" + instruction.type);
        }
    }

    private long applyInstructionPart1(Program program, int index){
        Instruction instruction = program.instructions.get(index);
        switch (instruction.type) {
            case "set": {
                Long number;
                if(instruction.regAttributeTwo != null){
                    number = instruction.regAttributeTwo.getValue();
                } else {
                    number = instruction.longAttributeTwo;
                }
                instruction.regAttributeOne.setValue(number);
                return 0;
            }

            case "add":{
                instruction.regAttributeOne.setValue(instruction.regAttributeOne.getValue() + instruction.longAttributeTwo);
                return 0;
            }

            case "mul":{
                Long firstNumber = instruction.regAttributeOne.getValue();
                Long secondNumber;
                if(instruction.regAttributeTwo != null){
                    secondNumber = instruction.regAttributeTwo.getValue();
                } else {
                    secondNumber = instruction.longAttributeTwo;
                }
                instruction.regAttributeOne.setValue(firstNumber * secondNumber);
                return 0;
            }

            case "mod":{
                Long firstNumber = instruction.regAttributeOne.getValue();
                Long secondNumber;
                if(instruction.regAttributeTwo != null){
                    secondNumber = instruction.regAttributeTwo.getValue();
                } else {
                    secondNumber = instruction.longAttributeTwo;
                }
                instruction.regAttributeOne.setValue(firstNumber % secondNumber);
                return 0;
            }

            // sets class variable:
            case "snd": {
                if (instruction.regAttributeOne.getValue() != 0) {
                    program.part1Result = instruction.regAttributeOne.getValue();
                    return 0;
                }
                return 0;
            }

            case "rcv": {
                if (instruction.regAttributeOne.getValue() != 0){
                    program.sentCounter ++;
                    return 0;
                }
                return 0;
            }

            case "jgz": {
                Long firstNumber;
                if(instruction.regAttributeOne != null){
                    firstNumber = instruction.regAttributeOne.getValue();
                } else {
                    firstNumber = instruction.longAttributeOne;
                }

                Long secondNumber;
                if(instruction.regAttributeTwo != null){
                    secondNumber = instruction.regAttributeTwo.getValue();
                } else {
                    secondNumber = instruction.longAttributeTwo;
                }

                if(firstNumber > 0){
                    return secondNumber - 1; // one will be automatically added from the loop
                }
                return 0;
            }
            default: throw new IllegalStateException("Instruction not recognized, name:" + instruction.type);
        }
    }

    private void addRegister(Program program, String name){
        for (int i = 0; i < program.registers.size(); i++) {
            if(program.registers.get(i).getName().equals(name)){
                return;
            }
        }
        program.registers.add(new Register(name));
    }

    private Register getRegisterByName(Program program, String name){
        for (int i = 0; i < program.registers.size(); i++) {
            Register current = program.registers.get(i);
            if(current.getName().equals(name)){
                return current;
            }
        }
        throw new IllegalStateException("Register with name: " + name + " not recognized.");
    }
}