package year2017.day13;

class Layer {
    private final int range;
    private final int depth;

    public Layer(int depth, int range) {
        this.depth = depth;
        this.range = range;
    }

    int getRange() {
        return range;
    }

    int getDepth() {
        return depth;
    }
}
