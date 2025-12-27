package year2017.day23;

import main.ReadLines;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day23 {
    private final ReadLines reader = new ReadLines(2017, 23, 2);

    private int mulInvoked = 0;

    private Program prepareInstructionsAndRegisters(){
        List<String> fileLines = reader.readFile();
        ArrayList<Instruction> instructions = new ArrayList<>();
        ArrayList<Register> registers = new ArrayList<>();
        Program program = new Program(instructions, registers);

        program.instructions = instructions;
        program.registers = registers;

        // catch registers first:
        for (int i = 0; i < fileLines.size(); i++) {
            String name = fileLines.get(i).split(" ")[1];
            if(!Character.isDigit(name.charAt(0)) && !name.startsWith("-")){
                program.addRegister(name);
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
                registerOne = program.getRegisterByName(parts[1]);
            }

            if(parts.length != 3){
                instructions.add(new Instruction(longOne, registerOne, longTwo, registerTwo, name));
                continue;
            }

            if(Character.isDigit(parts[2].charAt(0)) || parts[2].startsWith("-")){
                longTwo = Long.parseLong(parts[2]);
            } else {
                registerTwo = program.getRegisterByName(parts[2]);
            }
            instructions.add(new Instruction(longOne, registerOne, longTwo, registerTwo, name));
        }
        return program;
    }

    public void part1(){
        Program program = prepareInstructionsAndRegisters();

        for (int index = 0; index < program.instructions.size();) {
            index = index + applyInstruction(program, index);
        }
        System.out.println(mulInvoked);
    }

    public void part2(){

        System.out.println(getNumber());
    }

    private int getNumber (){
        int counter = 0;
        // set register b 99
        // multi register b 100
        // remove 100000
        int fromRegisters = 99 * 100 + 100000;

        for (int n = 0; n <= 1000; ++n) {
            Long number = fromRegisters + 17L * n; // value always detracted from register b;
            if (!isPrime(number)){
                counter++;
            }
        }
        return counter;
    }

    private static boolean isPrime(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;

        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        for (int i = 5; (long) i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        }

        return true;
    }


    private int applyInstruction(Program program, int index){
        Instruction instruction = program.instructions.get(index);
        Long valX, valY;

        switch (instruction.type) {
            case "set":
                valY = (instruction.regAttributeTwo != null) ?
                        instruction.regAttributeTwo.getValueOrZero() : instruction.longAttributeTwo;
                instruction.regAttributeOne.setValue(valY);
                return 1;  // advance by 1

            case "sub":
                valY = (instruction.regAttributeTwo != null) ?
                        instruction.regAttributeTwo.getValueOrZero() : instruction.longAttributeTwo;
                instruction.regAttributeOne.setValue(
                        instruction.regAttributeOne.getValueOrZero() - valY);
                return 1;

            case "mul":
                mulInvoked++;
                valY = (instruction.regAttributeTwo != null) ?
                        instruction.regAttributeTwo.getValueOrZero() : instruction.longAttributeTwo;
                instruction.regAttributeOne.setValue(
                        instruction.regAttributeOne.getValueOrZero() * valY);
                return 1;

            case "jnz":
                valX = (instruction.regAttributeOne != null) ?
                        instruction.regAttributeOne.getValueOrZero() : instruction.longAttributeOne;
                valY = (instruction.regAttributeTwo != null) ?
                        instruction.regAttributeTwo.getValueOrZero() : instruction.longAttributeTwo;

                if (valX != 0) {
                    return (int) valY.longValue();  // full jump offset
                }
                return 1;  // no jump → normal advance

            default:
                throw new IllegalStateException("Unknown instruction: " + instruction.type);
        }
    }
}
