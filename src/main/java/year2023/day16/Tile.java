package year2023.day16;

class Tile {
    boolean energized;
    int x;
    int y;
    String type;
    // possible string types:
    // 1. .
    // 2. \
    // 3. /
    // 3. |
    // 4. -

    //the directions of light that passed this tile:
    //if something already passed this tile, the light travel function can stop
    boolean left;
    boolean right;
    boolean up;
    boolean down;


    Tile(int x, int y, String type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
}