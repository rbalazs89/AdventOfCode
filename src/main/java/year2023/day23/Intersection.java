package year2023.day23;

import java.util.ArrayList;
import java.util.List;

// Represents nodes
class Intersection {
    int x;
    int y;
    List<Road> roadsOut = new ArrayList<>();

    Intersection(int y, int x){
        this.y = y;
        this.x = x;
    }
}
