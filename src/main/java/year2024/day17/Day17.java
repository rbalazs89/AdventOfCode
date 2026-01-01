package year2024.day17;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    private final ReadLines reader = new ReadLines(2024, 17, 2);
    private static int regA;
    private static int regB;
    private static int regC;
    private static int[] instructions;
    private static int instructionPointer = 0;
    private static final ArrayList<Integer> outputs = new ArrayList<>();

    private void prepare(){
        outputs.clear();
        List<String> input = reader.readFile();

        int[] tempInt = new int[3];
        for (int i = 0; i < input.size(); i++) {
            if(i == 0 || i == 1 || i == 2){
                String[] s = input.get(i).split(" ");
                tempInt[i] = Integer.parseInt(s[2]);
            }
            if(i == 4){
                String[] s2 = input.get(i).split(" ");
                String[] temp = s2[1].split(",");
                instructions = new int[temp.length];
                for (int j = 0; j < temp.length; j++) {
                    instructions[j] = Integer.parseInt(temp[j]);
                }
            }
        }
        regA = tempInt[0];
        regB = tempInt[1];
        regC = tempInt[2];
    }

    public void part1(){
        prepare();
        instructionPointer = 0;
        while(instructionPointer < instructions.length){
            command(instructions[instructionPointer], instructions[instructionPointer + 1]);
            instructionPointer += 2;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < outputs.size(); i++) {
            result.append((outputs.get(i))).append(",");
        }
        System.out.println(result.substring(0, result.length() - 1));
    }

    public void part2(){
        prepare();
        int instructionsLength = instructions.length - 1;
        long start = Long.parseLong("3" + "0".repeat(instructionsLength), 8);
        long end = (long) Math.pow(8, 16);
        int increment = 01;
        for (long i = start; i < end; i += increment) {
            long a = i;
            long b;
            long c;
            loop:{
                int outputs = 0;
                while (a != 0) {
                    b = a & 0b111;
                    b ^= 5;
                    c = (b == 0) ? a : a >> b;
                    b ^= 6;
                    b ^= c;
                    a >>= 3;
                    if ((b & 0b111) != instructions[outputs])
                        break loop;
                    outputs++;
                }
                if (outputs >= instructionsLength) {
                    System.out.println(i);
                    return;
                }
            }
        }
    }

    private static void command(int i, int literalOperand) {
        int comboOperand = literalOperand;
        if(comboOperand == 4){
            comboOperand = regA;
        } else if (comboOperand == 5){
            comboOperand = regB;
        } else if (comboOperand == 6){
            comboOperand = regC;
        }

        if(i == 0){
            double denominator = Math.pow(2, comboOperand);
            regA = (int)(Math.floor(regA / denominator));
        } else if(i == 1){
            regB = regB ^ literalOperand;
        } else if(i == 2){
            regB = comboOperand % 8;
        } else if (i == 3){
            if(regA != 0){
                instructionPointer = literalOperand - 2;
            }
        } else if (i == 4){
            regB = regB ^ regC;
        } else if (i == 5){
            outputs.add(comboOperand % 8);
        } else if (i == 6){
            double denominator = Math.pow(2, comboOperand);
            regB = (int)(Math.floor(regA / denominator));
        } else if (i == 7){
            double denominator = Math.pow(2, comboOperand);
            regC = (int)(Math.floor(regA / denominator));
        }
    }
}
