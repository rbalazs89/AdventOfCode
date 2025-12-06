package year2015.day7;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {
    List<String> fileLines;
    List<Instruction> instructions = new ArrayList<>();
    List<Wire> wires = new ArrayList<>();
    int part1Result = 0;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void processInput() {
        Set<String> detectIfExists = new HashSet<>();

        for (int i = 0; i < fileLines.size(); i++) {

            Instruction instruction = new Instruction();

            String line = fileLines.get(i);
            String[] parts = line.split(" ");

            // AND instruction:
            if(line.contains("AND")){
                instruction.type = "AND";

                if(Character.isDigit(parts[2].charAt(0))){
                    System.out.println("maxi big problem");
                }

                if(Character.isDigit(parts[0].charAt(0))){
                    instruction.value = Integer.parseInt(parts[0]);
                }
                else if(detectIfExists.add(parts[0])){
                    Wire wire = new Wire();
                    wire.name = parts[0];
                    wires.add(wire);
                }
                if(detectIfExists.add(parts[2])){
                    Wire wire = new Wire();
                    wire.name = parts[2];
                    wires.add(wire);
                }
                if(detectIfExists.add(parts[4])){
                    Wire wire = new Wire();
                    wire.name = parts[4];
                    wires.add(wire);
                }

                if(!Character.isDigit(parts[0].charAt(0))){
                    instruction.in1 = findWire(parts[0]);
                }

                instruction.in2 = findWire(parts[2]);
                instruction.out = findWire(parts[4]);

                instructions.add(instruction);

            // OR instruction
            } else if (line.contains("OR")){
                instruction.type = "OR";

                if(Character.isDigit(parts[0].charAt(0))){
                    System.out.println("maxi big problem");
                }

                if(Character.isDigit(parts[2].charAt(0))){
                    System.out.println("maxi big problem");
                }

                if(detectIfExists.add(parts[0])){
                    Wire wire = new Wire();
                    wire.name = parts[0];
                    wires.add(wire);
                }

                if(detectIfExists.add(parts[2])){
                    Wire wire = new Wire();
                    wire.name = parts[2];
                    wires.add(wire);
                }

                if(detectIfExists.add(parts[4])){
                    Wire wire = new Wire();
                    wire.name = parts[4];
                    wires.add(wire);
                }

                instruction.in1 = findWire(parts[0]);
                instruction.in2 = findWire(parts[2]);
                instruction.out = findWire(parts[4]);

                instructions.add(instruction);

            // SIMPLE instruction
            } else if (parts.length == 3 && Character.isDigit(parts[0].charAt(0))){
                instruction.type = "SIMPLE";
                instruction.value = Integer.parseInt(parts[0]);

                if(detectIfExists.add(parts[2])){
                    Wire wire = new Wire();
                    wire.name = parts[2];
                    wires.add(wire);
                }

                instruction.out = findWire(parts[2]);

                instructions.add(instruction);

            // SIMPLEWIRE instruction
            } else if (parts.length == 3 && !Character.isDigit(parts[0].charAt(0))) {
                instruction.type = "SIMPLEWIRE";
                if(detectIfExists.add(parts[0])){
                    Wire wire = new Wire();
                    wire.name = parts[0];
                    wires.add(wire);
                }

                if(detectIfExists.add(parts[2])){
                    Wire wire = new Wire();
                    wire.name = parts[2];
                    wires.add(wire);
                }

                instruction.in1 = findWire(parts[0]);
                instruction.out = findWire(parts[2]);

                instructions.add(instruction);

            } else if (line.contains("NOT")){
                instruction.type = "NOT";

                if(detectIfExists.add(parts[1])){
                    Wire wire = new Wire();
                    wire.name = parts[1];
                    wires.add(wire);
                }

                if(detectIfExists.add(parts[3])){
                    Wire wire = new Wire();
                    wire.name = parts[3];
                    wires.add(wire);
                }

                instruction.in1 = findWire(parts[1]);
                instruction.out = findWire(parts[3]);

                instructions.add(instruction);

            // LSHIFT instruction
            } else if (line.contains("LSHIFT")){
                instruction.type = "LSHIFT";
                if(detectIfExists.add(parts[0])){
                    Wire wire = new Wire();
                    wire.name = parts[0];
                    wires.add(wire);
                }
                if(detectIfExists.add(parts[4])){
                    Wire wire = new Wire();
                    wire.name = parts[4];
                    wires.add(wire);
                }

                instruction.value = Integer.parseInt(parts[2]);
                instruction.in1 = findWire(parts[0]);
                instruction.out = findWire(parts[4]);

                instructions.add(instruction);

            // RSHIFT instructions
            } else if (line.contains("RSHIFT")){

                instruction.type = "RSHIFT";
                if(detectIfExists.add(parts[0])){
                    Wire wire = new Wire();
                    wire.name = parts[0];
                    wires.add(wire);
                }
                if(detectIfExists.add(parts[4])){
                    Wire wire = new Wire();
                    wire.name = parts[4];
                    wires.add(wire);
                }

                instruction.value = Integer.parseInt(parts[2]);
                instruction.in1 = findWire(parts[0]);
                instruction.out = findWire(parts[4]);

                instructions.add(instruction);
            }
        }
    }

    public Wire findWire(String wireName){
        for (int i = 0; i < wires.size(); i++) {
            if(wireName.equals(wires.get(i).name)){
                return wires.get(i);
            }
        }
        System.out.println("problem");
        return null;
    }

    public void part1(){
        readData();
        processInput();
        for (int j = 0; j < instructions.size(); j++) {
            for (int i = 0; i < instructions.size(); i++) {
                processOneInstruction(instructions.get(i));
            }
        }

        part1Result = findWire("a").value;

        System.out.println(findWire("a").value);

    }

    void processOneInstruction(Instruction instruction){
        if(instruction.done){
            return;
        }
        switch (instruction.type) {
            case "AND" -> {

                // type 1 AND
                if (instruction.value == 1 && instruction.in2.isComputed) {
                    instruction.out.value = (instruction.value & instruction.in2.value) & 0xFFFF;
                    instruction.done = true;
                    instruction.out.isComputed = true;
                }

                // type 2 AND
                if (instruction.in1 != null) {
                    if (instruction.in1.isComputed && instruction.in2.isComputed) {
                        instruction.out.value = (instruction.in1.value & instruction.in2.value) & 0xFFFF;
                        instruction.done = true;
                        instruction.out.isComputed = true;
                    }
                }
            }
            case "SIMPLE" -> {

                instruction.out.value = instruction.value & 0xFFFF;
                instruction.done = true;
                instruction.out.isComputed = true;
            }
            case "SIMPLEWIRE" -> {

                if (!instruction.in1.isComputed) {
                    return;
                }

                instruction.out.value = instruction.in1.value;
                instruction.done = true;
                instruction.out.isComputed = true;
            }
            case "OR" -> {
                if (!instruction.in1.isComputed || !instruction.in2.isComputed) {
                    return;
                }
                instruction.out.value = (instruction.in1.value | instruction.in2.value) & 0xFFFF;
                instruction.done = true;
                instruction.out.isComputed = true;
            }
            case "NOT" -> {
                if (!instruction.in1.isComputed) {
                    return;
                }
                instruction.out.value = (~instruction.in1.value) & 0xFFFF;
                instruction.done = true;
                instruction.out.isComputed = true;
            }
            case "RSHIFT" -> {
                if (!instruction.in1.isComputed) {
                    return;
                }
                instruction.out.value = (instruction.in1.value >> instruction.value) & 0xFFFF;
                instruction.done = true;
                instruction.out.isComputed = true;
            }
            case "LSHIFT" -> {
                if (!instruction.in1.isComputed) {
                    return;
                }
                instruction.out.value = (instruction.in1.value << instruction.value) & 0xFFFF;
                instruction.done = true;
                instruction.out.isComputed = true;
            }
        }
    }

    public void part2(){

        for (int i = 0; i < wires.size(); i++) {
            wires.get(i).isComputed = false;
            wires.get(i).value = 0;
            if(wires.get(i).name.equals("b")){
                wires.get(i).isComputed = true;
            }
        }

        for (int i = 0; i < instructions.size(); i++) {
            instructions.get(i).done = false;

            if(instructions.get(i).out.name.equals("b")){
                instructions.get(i).done = true;
            }
        }

        findWire("b").value = part1Result;

        for (int j = 0; j < instructions.size(); j++) {
            for (int i = 0; i < instructions.size(); i++) {
                processOneInstruction(instructions.get(i));
            }
        }

        System.out.println(findWire("a").value);

    }
}
