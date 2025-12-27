package year2018.day3;

// rectangle represents the fabric dimensions from the puzzle:
class Fabric {

    private final int ID;
    private final int topLeftX;
    private final int topLeftY;
    private final int width;
    private final int height;

    Fabric(int ID, int topLeftX, int topLeftY, int width, int height) {
        this.ID = ID;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.width = width;
        this.height = height;
    }

    // all input values are guaranteed to be positive
    static boolean isThereOverlap(Fabric src1, Fabric src2){
        int x1 = Math.max(src1.getMinX(), src2.getMinX());
        int y1 = Math.max(src1.getMinY(), src2.getMinY());
        int x2 = Math.min(src1.getMaxX(), src2.getMaxX());
        int y2 = Math.min(src1.getMaxY(), src2.getMaxY());

        int width  = x2 - x1;
        int height = y2 - y1;

        return width > 0 && height > 0;
    }

    int getID(){
        return ID;
    }

    int getMinY(){
        return topLeftY;
    }

    int getMaxY(){
        return topLeftY + height;
    }

    int getMaxX(){
        return topLeftX + width;
    }

    int getMinX(){
        return topLeftX;
    }
}
