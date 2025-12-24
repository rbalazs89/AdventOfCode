package year2016.day11;

class Item {
    final String namePrefix;

    // 1 generator
    // 2 chip
    final int type;
    int currentFloor;
    Item pair; // Pair chips and generators to ease lookup, final once assigned never changed again

    // this puzzle is extremely long to code and confusing
    // I ended up adding a unique ID for easier comparison
    // and to avoid mutating original values during bfs search/creating combinations by mistake
    final int uniqueID;

    Item(String namePrefix, int type, int currentFloor, int uniqueID) {
        this.namePrefix = namePrefix;
        this.type = type;
        this.currentFloor = currentFloor;
        this.uniqueID = uniqueID;
    }

    void addPair(Item item) {
        if (this.type == item.type) {
            return;
        }
        if (this.namePrefix.equals(item.namePrefix)) {
            this.pair = item;
        }
    }

    static Item deepCopy(Item item) {
        return new Item(item.namePrefix, item.type, item.currentFloor, item.uniqueID);
    }
}
