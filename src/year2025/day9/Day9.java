package year2025.day9;

import main.ReadLines;

import java.util.List;
import java.util.stream.IntStream;

public class Day9 {
    // 3147366750 part 2 too high
    // 1082535750 part 2 too low

    List<String> fileLines;
    int inputFileIndex = 2;
    int[][] points;

    public void readData() {
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }
    public void processData(){
        points = new int[2][fileLines.size()];
        for (int i = 0; i < fileLines.size(); i++) {
            String[] line = fileLines.get(i).split(",");
            points[0][i] = Integer.parseInt(line[0]); // x coord
            points[1][i] = Integer.parseInt(line[1]); // y coord
        }
    }

    public void part1(){
        readData();
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

    public void part2() {
        /** idea for solution:
         * the polygon seems suspiciously handcrafted and special in many ways, see attached png
         * There are two big edges in the middle caused by two special corner points.
         * Other than that the polygon is very similar to a circle.
         *
         * Idea for solution:
         * From the picture its obvious at first glance that one of the edges for the highest aread rectangle
         * is one of those special corner points extending to the middle. The other one is somewhere on the other side of the rectangle.
         */
        readData();
        processData();

        PolygonViewer.showWindow(points);

        System.out.println("i dont know how to do this part 2 :(");

    }
}
