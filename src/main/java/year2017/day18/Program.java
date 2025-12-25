package year2017.day18;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Program {
    ArrayList<Instruction> instructions = new ArrayList<>();
    ArrayList<Register> registers = new ArrayList<>();
    boolean isWaiting = false;
    Queue<Long> receivedQueue = new LinkedList<>();
    int sentCounter = 0;
    int IDNumber;
    Long part1Result;
    int executionIndex = 0;

    Program(boolean isWaiting, ArrayList<Register> registers, ArrayList<Instruction> instructions) {
        this.isWaiting = isWaiting;
        this.registers = registers;
        this.instructions = instructions;
    }

    void sendValue(Program sendToThis, Long value){
        sentCounter ++;
        sendToThis.receivedQueue.add(value);
    }
}
