package year2018.day16;

import java.util.ArrayList;

class TestValue {
    private final int[] before;
    private final int[] instruction;
    private final int[] after;
    private final ArrayList<Integer> possibleNumbers = new ArrayList<>();
    private final ArrayList<Opcode> possibleOpcodes = new ArrayList<>();

    TestValue(int[] before, int[] instruction, int[] after){
        this.before = before;
        this.instruction = instruction;
        this.after = after;
    }

    int[] getBefore() {
        return before;
    }

    int[] getInstruction() {
        return instruction;
    }

    int[] getAfter() {
        return after;
    }

    void addPossibleNumber(int n){
        possibleNumbers.add(n);
    }

    void addPossibleOpcode(Opcode opcode){
        possibleOpcodes.add(opcode);
    }


}
