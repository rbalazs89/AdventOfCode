package year2023.day23;

import main.ReadLines;

import java.util.*;

public class Day23 {
    // setup:
    // note for this puzzle: all boundary is either wall or end/start point -> no boundary checks needed during traversal
    private static final ReadLines reader = new ReadLines(2023, 23, 2);
    private static char[][] maze; // maze[y][x]
    private static int startX;
    private static int startY;
    private static int endX;
    private static int endY;
    private static final char WALL = '#';

    // part1:
    private final List<Intersection> graph = new ArrayList<>(); // represents graphs from the maze

    private void prepare(){
        List<String> input = reader.readFile();

        // set up maze:
        int maxY = input.size();
        int maxX = input.getFirst().length();

        maze = new char[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                char c = input.get(y).charAt(x);
                maze[y][x] = c;
            }
        }

        // Specified by task description
        startY = 0;
        startX = 1;
        endY = maxY - 1;
        endX = maxX - 2;

        // set up intersections (no roads yet) only coordinates
        // start and end
        graph.add(new Intersection(startY, startX));
        graph.add(new Intersection(endY, endX));

        // all other intersections
        for (int y = 1; y < maxY - 1; y++) {
            for (int x = 1; x < maxX - 1; x++) {
                if(maze[y][x] == WALL){
                    continue;
                }
                int open = 0;
                if(maze[y - 1][x] != WALL) open ++;
                if(maze[y + 1][x] != WALL) open ++;
                if(maze[y][x - 1] != WALL) open ++;
                if(maze[y][x + 1] != WALL) open ++;

                if(open >= 3){
                    graph.add(new Intersection(y, x));
                    //maze[y][x] = 'X';
                }
            }
        }
    }

    private void setupGraphEdgeValues(){
        for (int i = 0; i < graph.size(); i++) {
            Intersection currentNode = graph.get(i);
            if(currentNode.y == endY && currentNode.x == endX){
                continue;
            }
            findRoutesFromIntersection(currentNode);
        }
    }

    public void part1(){
        // setup graph
        prepare();
        maze[startY][startX] = WALL;
        setupGraphEdgeValues();

        // queue for bfs:
        Queue<Path> q = new LinkedList<>();
        Path start = new Path();
        start.currentSteps = 0;
        start.latestIntersection = graph.getFirst(); // starting node is the first element in graph

        int highestValue = 0;

        // bfs for highest value:
        q.add(start);
        while (!q.isEmpty()){
            Path current = q.poll();
            if(current.latestIntersection == graph.get(1)){ // ending node
                highestValue = Math.max(highestValue, current.currentSteps);
                continue;
            }
            for (int i = 0; i < current.latestIntersection.roadsOut.size(); i++) {
                Path p = new Path();
                p.currentSteps = current.currentSteps + current.latestIntersection.roadsOut.get(i).value;
                p.latestIntersection = current.latestIntersection.roadsOut.get(i).to;
                q.add(p);
            }
        }
        System.out.println(highestValue);
    }

    public void part2(){

    }

    private void findRoutesFromIntersection(Intersection thisRouteStart){
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{thisRouteStart.y, thisRouteStart.x});

        Set<String> visited = new HashSet<>();
        visited.add(thisRouteStart.y + "," + thisRouteStart.x);;

        int depth = 0;
        while (!q.isEmpty()){
            int movesOnThisLevel = q.size();
            for (int i = 0; i < movesOnThisLevel; i++) {
                int[] c = q.poll();

                int cy = c[0];
                int cx = c[1];

                int open = 0;

                if(cy != startY && cy != endY){
                    if(maze[cy - 1][cx] != WALL) open ++;
                    if(maze[cy + 1][cx] != WALL) open ++;
                    if(maze[cy][cx - 1] != WALL) open ++;
                    if(maze[cy][cx + 1] != WALL) open ++;
                }

                if(cy == endY && cx == endX){
                    Intersection end = findIntersection(cy, cx);
                    Road road = new Road();
                    road.value = depth;
                    road.from = thisRouteStart;
                    road.to = end;

                    thisRouteStart.roadsOut.add(road);
                    continue;
                }

                if(open >= 3 && depth != 0){
                    Intersection end = findIntersection(cy, cx);
                    Road road = new Road();
                    road.value = depth;
                    road.from = thisRouteStart;
                    road.to = end;

                    thisRouteStart.roadsOut.add(road);
                    continue;
                }

                // top:
                if(!(cy <= startY)){
                    char c1 = maze[cy - 1][cx];
                    if(c1 == '.' || c1 == '^'){
                        if(visited.add((cy - 1) + "," + cx)){
                            q.add(new int[]{cy - 1, cx});
                        }
                    }
                }

                // right:
                char c2 = maze[cy][cx + 1];
                if(c2 == '.' || c2 == '>'){
                    if(visited.add((cy) + "," + (cx + 1))){
                        q.add(new int[]{cy, cx + 1});
                    }
                }

                // down:
                char c3 = maze[cy + 1][cx];
                if(c3 == '.' || c3 == 'v'){
                    if(visited.add((cy + 1) + "," + (cx))){
                        q.add(new int[]{(cy + 1), cx});
                    }
                }

                // left:
                char c4 = maze[cy][cx - 1];
                if(c4 == '.' || c4 == '<'){
                    if(visited.add((cy) + "," + (cx - 1))){
                        q.add(new int[]{(cy), (cx - 1)});
                    }
                }
            }
            depth ++;
        }
    }

    /**
     * DEBUG METHOD
     * draws the current maze
     */
    private void drawMaze(){
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                System.out.print(maze[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private Intersection findIntersection(int y, int x){
        for (int i = 0; i < graph.size(); i++) {
            Intersection c = graph.get(i);
            if(c.x == x && c.y == y){
                return c;
            }
        }
        maze[y][x] = 'O';
        drawMaze();
        throw new IllegalStateException("no intersection found at the called location");
    }

    private void seeGraph(){
        for (int i = 0; i < graph.size(); i++) {
            Intersection node = graph.get(i);
            System.out.println("this node y,x: " + node.y + "," + node.x + ".");
            for (int j = 0; j < node.roadsOut.size(); j++) {
                Road r = node.roadsOut.get(j);
                System.out.println(" - road " + j + ": " + r.value + " , " + r.to.y + "," + r.to.x);
            }
        }
    }
}