package year2016.day25;

import main.ReadLines;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 {
    // setup:
    private final ReadLines reader = new ReadLines(2016, 25, 2);
    private List<String> fileLines; // lines from txt file
    private boolean prepared = false;

    private final ArrayList<Register> registers = new ArrayList<>();
    private final ArrayList<Instruction> instructions = new ArrayList<>();
    private boolean signalOutput = false;
    private int showThisSignal;
    private final ArrayList<Integer> signals = new ArrayList<>();

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        if(!prepared){
            readData();
        }
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
            switch (parts[0]) {
                case "cpy" -> {
                    instruction.type = "cpy";
                    if (Character.isDigit(parts[1].charAt(parts[1].length() - 1))) {
                        instruction.firstArgumentInteger = Integer.parseInt(parts[1]);
                    } else {
                        instruction.firstArgumentRegister = getRegister(parts[1]);
                    }
                    instruction.secondArgumentRegister = getRegister(parts[2]);
                }
                case "inc" -> {
                    instruction.type = "inc";
                    instruction.firstArgumentRegister = getRegister(parts[1]);
                }
                case "dec" -> {
                    instruction.type = "dec";
                    instruction.firstArgumentRegister = getRegister(parts[1]);
                }
                case "jnz" -> {
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
                }
                case "tgl" -> {
                    instruction.type = "tgl";
                    if (Character.isDigit(parts[1].charAt(parts[1].length() - 1))) {
                        instruction.firstArgumentInteger = Integer.parseInt(parts[1]);
                    } else {
                        instruction.firstArgumentRegister = getRegister(parts[1]);
                    }
                }
                case "out" -> {
                    instruction.type = "out";
                    if (Character.isDigit(parts[1].charAt(parts[1].length() - 1))) {
                        instruction.firstArgumentInteger = Integer.parseInt(parts[1]);
                    } else {
                        instruction.firstArgumentRegister = getRegister(parts[1]);
                    }
                }
                default -> throw new IllegalStateException("Instruction preparing went wrong");
            }
            instructions.add(instruction);
        }
        prepared = true;
    }

    public void part1(){
        for (int j = 0; j < Integer.MAX_VALUE; j++) {
            prepare();
            getRegister("a").value = j;
            for (int i = 0; i < instructions.size(); i++) {
                int value = processInstruction(instructions.get(i), i);
                i = i + value;
                if(signalOutput){
                    signals.add(showThisSignal);
                }
                if(signals.size() > 100){
                    break;
                }
            }
            if(investigateSignals()){
                System.out.println(j);
                break;
            }
        }
    }

    private boolean investigateSignals(){
        boolean isTheFirstScenarioGood = true;
        boolean isTheSecondScenarioGood = true;

        for (int i = 0; i < signals.size(); i++) {
            if(i % 2 != signals.get(i) % 2) {
                isTheFirstScenarioGood = false;
                break;
            }
        }

        for (int i = 0; i < signals.size(); i++) {
            if(i % 2 == signals.get(i) % 2) {
                isTheSecondScenarioGood = false;
            }
        }
        signals.clear();
        return isTheFirstScenarioGood || isTheSecondScenarioGood;
    }

    public void part2(){
        // nothing to do here, no part 2 for day 25
    }

    private int processInstruction(Instruction instruction, int index){
        if(!instruction.valid){
            return 0;
        }
        signalOutput = false;
        switch (instruction.type) {
            case "cpy" -> {
                int valueToCopy;

                if (instruction.firstArgumentRegister == null) {
                    valueToCopy = instruction.firstArgumentInteger;
                } else {
                    valueToCopy = instruction.firstArgumentRegister.value;
                }
                instruction.secondArgumentRegister.value = valueToCopy;
                return 0;
            }
            case "inc" -> {
                instruction.firstArgumentRegister.value++;
                return 0;
            }
            case "dec" -> {
                instruction.firstArgumentRegister.value--;
                return 0;
            }
            case "jnz" -> {
                int integerToCompare;
                if (instruction.firstArgumentRegister == null) {
                    integerToCompare = instruction.firstArgumentInteger;
                } else {
                    integerToCompare = instruction.firstArgumentRegister.value;
                }

                int jumpValue;
                if (instruction.secondArgumentRegister == null) {
                    jumpValue = instruction.secondArgumentInteger;
                } else {
                    jumpValue = instruction.secondArgumentRegister.value;
                }

                if (integerToCompare != 0) {
                    return jumpValue - 1;
                } else {
                    return 0;
                }
            }
            case "tgl" -> {

                int toModify = index + instruction.firstArgumentRegister.value;
                if (toModify >= instructions.size()) {
                    return 0;
                }

                Instruction instructionToModify = instructions.get(toModify);

                if (instructionToModify.type.equals("inc")) {
                    instructionToModify.type = "dec";
                } else if (instructionToModify.type.equals("dec")) {
                    instructionToModify.type = "inc";
                } else if (instructionToModify.type.equals("tgl")) {
                    instructionToModify.type = "inc";
                } else if (instructionToModify.type.equals("out")) {
                    instructionToModify.type = "inc";
                } else if (instructionToModify.type.equals("jnz")) {
                    instructionToModify.type = "cpy";
                    if (instructionToModify.secondArgumentInteger != null) {
                        instructionToModify.valid = false;
                        return 0;
                    }
                } else if (instructionToModify.type.equals("cpy")) {
                    instructionToModify.type = "jnz";
                    instructionToModify.valid = true;
                }
                return 0;
            }
            case "out" -> {
                signalOutput = true;
                if (instruction.firstArgumentInteger == null) {
                    showThisSignal = instruction.firstArgumentRegister.value;
                } else {
                    showThisSignal = instruction.firstArgumentInteger;
                }
                return 0;
            }
            default -> throw new IllegalStateException(
                    "Instruction processing went wrong"
            );
        }
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
}
