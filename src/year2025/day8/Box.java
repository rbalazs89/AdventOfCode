package year2025.day8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Box {

    int x;
    int y;
    int z;

    long squaredDistance(Box other) {
        int dx = x - other.x;
        int dy = y - other.y;
        int dz = z - other.z;
        return (long)(dx*dx + dy*dy + dz*dz);
    }

}
