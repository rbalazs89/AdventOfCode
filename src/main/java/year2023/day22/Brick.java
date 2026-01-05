package year2023.day22;

class Brick {
    int[] start;
    int[] end;

    Brick(int[] start, int[] end){
        this.start = start;
        this.end = end;
    }

    Brick deepCopyBrick(){
        int[] newStart = new int[]{start[0], start[1], start[2]};
        int[] newEnd = new int[]{end[0], end[1], end[2]};
        return new Brick(newStart, newEnd);
    }
}
