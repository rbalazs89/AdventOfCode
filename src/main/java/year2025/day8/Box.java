package year2025.day8;

class Box {

    long x;
    long y;
    long z;

    long squaredDistance(Box other) {
        long dx = x - other.x;
        long dy = y - other.y;
        long dz = z - other.z;
        return (dx*dx + dy*dy + dz*dz);
    }

}
