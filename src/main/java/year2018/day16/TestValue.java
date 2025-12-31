package year2018.day16;

import jdk.incubator.vector.VectorOperators;

class TestValue {
    private final int[] before;
    private final int[] instruction;
    private final int[] after;

    TestValue(int[] before, int[] instruction, int[] after){
        this.before = before;
        this.instruction = instruction;
        this.after = after;
    }

    public int[] getBefore() {
        return before;
    }

    public int[] getInstruction() {
        return instruction;
    }

    public int[] getAfter() {
        return after;
    }
}
