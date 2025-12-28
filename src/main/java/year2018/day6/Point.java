package year2018.day6;

class Point {
    private final int y;
    private final int x;
    private int closestSquareCounter;
    private boolean hasInfiniteArea;

    Point(int y, int x){
        this.y = y;
        this.x = x;
        hasInfiniteArea = false;
    }

    int getY() {
        return y;
    }

    int getX() {
        return x;
    }

    boolean hasInfiniteArea() {
        return hasInfiniteArea;
    }

    void setHasInfiniteAreaToTrue() {
        this.hasInfiniteArea = true;
    }

    int getManhattanDistance(int x, int y){
        int deltaX = Math.abs(this.getX() - x);
        int deltaY = Math.abs(this.getY() - y);

        return deltaX + deltaY;
    }

    void incrementClosestSquareCounter(){
        closestSquareCounter++;
    }

    int getClosestSquareCounter(){
        return closestSquareCounter;
    }
}
