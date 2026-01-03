package year2023.day23;

import java.util.ArrayList;
import java.util.List;

// represents nodes
class Intersection {
    int x;
    int y;
    List<Road> roadsOut = new ArrayList<>();
    List<Road> roadsIn = new ArrayList<>();
    int highestSteps = -1;
    int depth = 0;

    Intersection(int y, int x){
        this.y = y;
        this.x = x;
    }
}
