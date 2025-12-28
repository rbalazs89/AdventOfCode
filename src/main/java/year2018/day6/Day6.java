package year2018.day6;

import main.ReadLines;

import java.util.*;

public class Day6 {
    private final ReadLines reader = new ReadLines(2018, 6, 1);
    private static final int MAX_SUM_DISTANCE = 10000; // given by task description

    // puzzle note: all values are under 500
    // all values are positive
    private List<Point> getPoints(){
        List<String> lines = reader.readFile();

        ArrayList<Point> points = new ArrayList<>();
        for (String line : lines){
            String[] parts = line.replaceAll( " ", "").split(",");
            points.add(new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[0])));
        }
        return points;
    }

    private void setInfiniteAreaPoints(List<Point> points){
        int listSize = points.size();

        for (int i = 0; i < listSize; i++) {
            Point p = points.get(i);

            int topCounter = 0;
            int rightCounter = 0;
            int downCounter = 0;
            int leftCounter = 0;

            for (int j = 0; j < listSize; j++) {
                Point compare = points.get(j);
                if(i == j){
                    continue;
                }
                int xAbs = Math.abs(p.getX() - compare.getX());
                int yAbs = Math.abs(p.getY() - compare.getY());

                // check top:
                if((yAbs < xAbs && p.getY() > compare.getY()) || p.getY() <= compare.getY()){
                    topCounter ++;
                }

                // check right:
                if((xAbs < yAbs && p.getX() < compare.getX()) || p.getX() >= compare.getX()){
                    rightCounter ++;
                }

                // check down:
                if((yAbs < xAbs && p.getY() < compare.getY()) || p.getY() >= compare.getY()){
                    downCounter ++;
                }

                // check left:
                if((xAbs < yAbs && p.getX() > compare.getX()) || p.getX() <= compare.getX()){
                    leftCounter ++;
                }
            }

            if(topCounter == listSize - 1 || rightCounter == listSize - 1 || downCounter == listSize - 1 || leftCounter == listSize - 1){
                p.setHasInfiniteAreaToTrue();
            }
        }
    }

    public void part1() {
        List<Point> points = getPoints();
        setInfiniteAreaPoints(points);

        // gets minX, maxX, minY, maxY
        int[] extremes = getExtremes(points);
        int minX = extremes[0];
        int maxX = extremes[1];
        int minY = extremes[2];
        int maxY = extremes[3];

        // check all relevant points:
        // all points outside the bounding square always belong to one of the "infinite" points
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                int lowestDistance = Integer.MAX_VALUE;
                int index = -1;

                // this will not include all the distances, but unique lowest should be there only once
                HashMap<Integer, Integer> frequency = new HashMap<>();

                //find the lowest distance from all the points for a coordinate pair
                for (int i = 0; i < points.size(); i++) {
                    Point p = points.get(i);
                    int distance = p.getManhattanDistance(x, y);
                    if (distance <= lowestDistance) {
                        frequency.merge(distance, 1, Integer::sum);
                        lowestDistance = distance;
                        index = i;
                    }
                }

                // hashmap version:
                if(frequency.get(lowestDistance) == 1){
                    points.get(index).incrementClosestSquareCounter();
                }
            }
        }
        System.out.println(getMostClosestSquares(points));
    }

    public void part2(){
        List<Point> points = getPoints();

        // gets minX, maxX, minY, maxY
        int[] extremes = getExtremes(points);
        int minX = extremes[0];
        int maxX = extremes[1];
        int minY = extremes[2];
        int maxY = extremes[3];

        // check all relevant points:
        int counter = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                int distanceSum = 0;
                for (int i = 0; i < points.size(); i++) {
                    distanceSum += points.get(i).getManhattanDistance(x,y);
                    if(distanceSum > MAX_SUM_DISTANCE){
                        break;
                    }
                }
                if(distanceSum < MAX_SUM_DISTANCE){
                    counter ++;
                }
            }
        }
        System.out.println(counter);
    }

    // returns minX, maxX, minY, maxY
    private int[] getExtremes(List<Point> points){
        int minY = Integer.MAX_VALUE;
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        for (Point p : points) {
            minX = Math.min(p.getX(), minX);
            minY = Math.min(p.getY(), minY);
            maxX = Math.max(p.getX(), maxX);
            maxY = Math.max(p.getY(), maxY);
        }
        return new int[] {minX, maxX, minY, maxY};
    }

    private int getMostClosestSquares(List<Point> points){
        int max = 0;
        for (Point p : points){
            if(p.hasInfiniteArea()){
                continue;
            }
            max = Math.max(p.getClosestSquareCounter(), max);
        }
        return max;
    }
}
