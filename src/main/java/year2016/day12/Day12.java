package year2016.day12;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {
    private final ArrayList<Register> registers = new ArrayList<>();
    private final ArrayList<Instruction> instructions = new ArrayList<>();

    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 12, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void processData(){
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
            switch (parts[0]) {
                case "cpy" -> {
                    instruction.type = "cpy";
                    if (Character.isDigit(parts[1].charAt(0))) {
                        instruction.value = Integer.parseInt(parts[1]);
                    } else {
                        instruction.copyThis = getRegister(parts[1]);
                    }
                    instruction.reg = getRegister(parts[2]);
                }
                case "inc" -> {
                    instruction.type = "inc";
                    instruction.reg = getRegister(parts[1]);
                }
                case "dec" -> {
                    instruction.type = "dec";
                    instruction.reg = getRegister(parts[1]);
                }
                case "jnz" -> {
                    instruction.type = "jnz";
                    instruction.value = Integer.parseInt(parts[2]);
                    if (Character.isDigit(parts[1].charAt(0))) {
                        instruction.compareValue = Integer.parseInt(parts[1]);
                    } else {
                        instruction.copyThis = getRegister(parts[1]);
                    }
                }
                default -> System.out.println("problem");
            }
            instructions.add(instruction);
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

        System.out.println("return null: " + name);
        return null;
    }

    private int processInstruction(Instruction instruction){
        switch (instruction.type) {
            case "cpy" -> {
                Register current = instruction.reg;
                if (instruction.value == -1) {
                    current.value = instruction.copyThis.value;
                } else {
                    current.value = instruction.value;
                }
                return 0;
            }
            case "inc" -> {
                instruction.reg.value++;
                return 0;
            }
            case "dec" -> {
                instruction.reg.value--;
                return 0;
            }
            case "jnz" -> {

                int compareThis;
                if (instruction.compareValue != -1) {
                    compareThis = instruction.compareValue;
                } else {
                    compareThis = instruction.copyThis.value;
                }
                if (compareThis != 0) {
                    return instruction.value - 1;
                } else {
                    return 0;
                }
            }
            default -> {
                System.out.println("instruction processing problem");
                return 1;
            }
        }
    }

    public void part1(){
        processData();
        for (int i = 0; i < instructions.size(); i++) {
            int value = processInstruction(instructions.get(i));
            i = i + value;
        }

        for (int i = 0; i < registers.size(); i++) {
            if(registers.get(i).name.equals("a")){
                System.out.println(registers.get(i).value);
                break;
            }
        }
    }

    public void part2(){
        processData();

        for (int i = 0; i < registers.size(); i++) {
            if(registers.get(i).name.equals("a")){
                registers.get(i).value = 0;
            }
            if(registers.get(i).name.equals("c")){
                registers.get(i).value = 1;
            }
        }

        for (int i = 0; i < instructions.size(); i++) {
            int value = processInstruction(instructions.get(i));
            i = i + value;
        }

        for (int i = 0; i < registers.size(); i++) {
            if(registers.get(i).name.equals("a")){
                System.out.println(registers.get(i).value);
                break;
            }
        }

    }

}
