package year2025.day9;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day9 {

    private int[][] points;

    private final ReadLines reader = new ReadLines(2025, 9, 2);

    private void processData(){
        List<String> fileLines = reader.readFile();
        points = new int[2][fileLines.size()];
        for (int i = 0; i < fileLines.size(); i++) {
            String[] line = fileLines.get(i).split(",");
            points[0][i] = Integer.parseInt(line[0]); // x coord
            points[1][i] = Integer.parseInt(line[1]); // y coord
        }
    }

    public void part1(){
        processData();

        long result = 0;

        for (int i = 0; i < points[0].length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                int x = Math.abs(points[0][i] - points[0][j]);
                int y = Math.abs(points[1][i] - points[1][j]);

                result = Math.max((x + 1L) * (y + 1L), result);
            }
        }
        System.out.println(result);
    }
    /** idea for solution:
     * the polygon seems suspiciously handcrafted and special in many ways, see attached png
     * There are two big edges in the middle caused by two special corner points.
     * Other than that the polygon is very similar to a circle.*
     * From the picture it's obvious at first glance that one of the edges for the highest aread rectangle
     * is one of those special corner points extending to the middle. The other one is somewhere on the other side of the rectangle.
     * number 0 point starts at 3:00 and goes clockwise, so the 2 unique points are expected to be about i ~ 250;
     */
    public void part2() {
        processData();
        PolygonViewer.showWindow(points);

        // have some idea about min max coordinates:
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int i = 0; i < points[0].length; i++) {
            minX = Math.min(points[0][i], minX);
            minY = Math.min(points[1][i], minY);
            maxX = Integer.max(points[0][i], maxX);
            maxY = Integer.max(points[1][i], maxY);
        }

        //System.out.println("minX: " + minX + " minY: " + minY + " maxX: " + maxX + " maxY: "+ maxY);
        for (int i = 0; i < points[0].length - 1; i++) {
            int vibeDistance = 10 * Math.max(Math.abs(points[0][0] - points[0][1]), Math.abs(points[1][0] - points[1][1]));
            int currentDistance = Math.max(Math.abs(points[0][i] - points[0][i + 1]), Math.abs(points[1][i] - points[1][i + 1]));
            if(vibeDistance <  currentDistance){
                break;
            }
        }

        int tolerance = 35;
        int unique1 = 248;
        int index1 = 225;
        int area = 0;

        ArrayList<int[]> rectangle = new ArrayList<>();
        //SCENARIO 1:
        for (int i = index1 - tolerance; i < index1 + 2 * tolerance; i++) {
            boolean intersecting = false;
            if(i >= unique1){
                continue ;
            }
            // rectangle:
            int[] topRight = {points[0][unique1], points[1][unique1]};
            int[] topLeft = {points[0][i], points[1][unique1]};
            int[] bottomLeft = {points[0][i], points[1][i]};
            int[] bottomRight = {points[0][unique1], points[1][i]};

            rectangle.add(topRight);
            rectangle.add(bottomRight);
            rectangle.add(bottomLeft);
            rectangle.add(topLeft);

            // check intersections:
            int x1, y1, x2, y2, a1, b1, a2, b2;
            middleLoop:
            for (int j = 0; j < rectangle.size() - 1; j++) {
                int index = j + 1;
                if(j == rectangle.size() - 1){
                    index = 0;
                }
                x1 = rectangle.get(j)[0];
                y1 = rectangle.get(j)[1];
                x2 = rectangle.get(index)[0];
                y2 = rectangle.get(index)[1];

                for (int k = 0; k < unique1; k++) {
                    a1 = points[0][k];
                    b1 = points[1][k];
                    a2 = points[0][k + 1];
                    b2 = points[1][k + 1];

                    if(doLinesIntersect(x1, y1, x2, y2, a1, b1, a2, b2)){
                        intersecting = true;
                        break middleLoop;
                    }
                }
            }

            if(!intersecting) {
                int side1 = Math.abs(points[0][unique1] - points[0][i]);
                int side2 = Math.abs(points[1][i] - points[1][unique1]);
                int currentArea = (side1 + 1) * (side2 + 1);
                if (currentArea > area) {
                    area = currentArea;
                }
            }
            rectangle.clear();
        }
        // constructing scenario 2 was not needed, good result was in scenario 1 (lower half of the polygon)
        System.out.println(area);
    }

    private boolean doLinesIntersect(int x1, int y1, int x2, int y2,
                                     int a1, int b1, int a2, int b2) {

        // normalize ranges
        int minX1 = Math.min(x1, x2);
        int maxX1 = Math.max(x1, x2);
        int minY1 = Math.min(y1, y2);
        int maxY1 = Math.max(y1, y2);

        int minX2 = Math.min(a1, a2);
        int maxX2 = Math.max(a1, a2);
        int minY2 = Math.min(b1, b2);
        int maxY2 = Math.max(b1, b2);

        boolean pVert = (x1 == x2);
        boolean qVert = (a1 == a2);

        // vertical vs horizontal
        if (pVert != qVert) {
            boolean inside1;
            boolean inside2;
            if (pVert) {
                // horizontals y
                inside1 = (minY1 < b1 && b1 < maxY1);
                inside2 = (minX2 < x1 && x1 < maxX2);
            } else {
                inside1 = (minX1 < a1 && a1 < maxX1);
                inside2 = (minY2 < y1 && y1 < maxY2);
            }
            return inside1 && inside2;
        }

        // same orientation (vertical/vertical or horizontal/horizontal)
        if (pVert) {
            // must share X
            if (x1 != a1) return false;

            // check STRICT Y range overlap (no touching)
            return maxY1 > minY2 && maxY2 > minY1;
        } else {
            // horizontal: must share Y
            if (y1 != b1) return false;

            // check STRICT X range overlap (no touching)
            return maxX1 > minX2 && maxX2 > minX1;
        }
    }
}