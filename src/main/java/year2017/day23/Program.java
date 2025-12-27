package year2017.day23;

import java.util.ArrayList;

public class Program {
    ArrayList<Instruction> instructions;
    ArrayList<Register> registers;
    private int currentIndex;

    Program(ArrayList<Instruction> instructions, ArrayList<Register> registers){
        this.instructions = instructions;
        this.registers = registers;
    }

    void addRegister(String name){
        for (int i = 0; i < registers.size(); i++) {
            if(registers.get(i).getName().equals(name)){
                return;
            }
        }
        registers.add(new Register(name));
    }

    Register getRegisterByName(String name){
        for (int i = 0; i < registers.size(); i++) {
            Register current = registers.get(i);
            if(current.getName().equals(name)){
                return current;
            }
        }
        throw new IllegalStateException("Register with name: " + name + " not recignized.");
    }
}