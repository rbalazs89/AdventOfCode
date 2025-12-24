package year2016.day22;

class Storage {
    int x;
    int y;
    int size;
    int used;
    int available;

    boolean VIPData;

    // calculated after constructor assigned
    boolean isWall;

    Storage(int x, int y, int size, int used, int available){
        this.x = x;
        this.y = y;
        this.size = size;
        this.used = used;
        this.available = available;
    }
}
