package year2023.day15;

class Command {
    String label;
    int number;
    int boxNumber;

    //0 for minus sign
    //1 for equal sign
    int type;
    Command(String label, int boxNumber, int type){
        this.label = label;
        this.boxNumber = boxNumber;
        this.type = type;
    }
}