package year2025.day8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Box {

    long x;
    long y;
    long z;

    long squaredDistance(Box other) {
        long dx = x - other.x;
        long dy = y - other.y;
        long dz = z - other.z;
        return (long)(dx*dx + dy*dy + dz*dz);
    }

}
