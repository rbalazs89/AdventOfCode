package year2017.day10;

class Number {
    int value;
    int originalPosition;

    public Number(int value, int originalPosition) {
        this.value = value;
        this.originalPosition = originalPosition;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getOriginalPosition() {
        return originalPosition;
    }
}
