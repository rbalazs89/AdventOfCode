package year2018.day12;

class SpreadPattern {
    private final char[] currentPattern;
    private final char plant;

    public SpreadPattern(char[] currentPattern, char plant) {
        this.currentPattern = currentPattern;
        this.plant = plant;
    }

    char[] getCurrentPattern() {
        return currentPattern;
    }

    char getPlant() {
        return plant;
    }
}
