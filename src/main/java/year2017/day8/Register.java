package year2017.day8;

class Register {
    private final String name;
    private int value;
    final static int startingValue = 0;

    Register(String name){
        this.name = name;
        value = 0;
    }

    String getName() {
        return name;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }
}
