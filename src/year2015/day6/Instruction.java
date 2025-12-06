package year2015.day6;

class Instruction {
    int type;
        // 1 turn on
        // 2 turn off
        // 3 toggle
    int[] start;
    int[] end;

    public Instruction(int type, int[] start, int[] end){
        this.type = type;
        this.start = start;
        this.end = end;
    }
}
