package year2023.day23;

import java.util.ArrayList;
import java.util.List;

// Represents nodes
class Intersection {
    int x;
    int y;
    List<Road> roadsOut = new ArrayList<>();
    List<Road> roadsIn = new ArrayList<>();
    int depth = 0;

    Intersection(int y, int x){
        this.y = y;
        this.x = x;
    }
}
