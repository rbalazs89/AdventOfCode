package year2023.day17;

import main.ReadLines;

import java.util.*;

public class Day17 {
    private final ReadLines reader = new ReadLines(2023, 17, 2);
    private static int[][] lavaMap;
    private static final int MAX_STRAIGHT_STEPS = 3;
    private static final int[] DY = {-1, 0, 1, 0};
    private static final int[] DX = {0, 1, 0, -1};
    private static final int MAX_STRAIGHT_PART2 = 10;
    private static final int MIN_STRAIGHT_PART2 = 4;

    private void prepareMap(){
        List<String> lines = reader.readFile();
        lavaMap = new int[lines.size()][lines.getFirst().length()];

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.getFirst().length(); j++) {
                lavaMap[i][j] = lines.get(i).charAt(j) - '0';
            }
        }
    }

    public void part1(){
        prepareMap();
        // top -> 0, right -> 1, down -> 2, left -> 3;
        int maxY = lavaMap.length;
        int maxX = lavaMap[0].length;
        int[][][][] visited = new int[maxY][maxX][4][MAX_STRAIGHT_STEPS + 1]; // y,x,arrivedDirection, straightSteps -> "6,8,>,2"
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                for (int dir = 0; dir < 4; dir++) {
                    Arrays.fill(visited[y][x][dir], Integer.MAX_VALUE);
                }
            }
        }

        // two starting nodes added manually, heat loss from 0,0 not incurred according to puzzle description:
        Node startingNode1 = new Node(0,1, 1, 1, lavaMap[0][1]);
        Node startingNode2 = new Node(1,0,2,1, lavaMap[1][0]);
        updateVisited(startingNode1, visited);
        updateVisited(startingNode2, visited);

        Queue<Node> pq = new PriorityQueue<>(Comparator.comparing(a -> a.cost));
        pq.add(startingNode1);
        pq.add(startingNode2);

        while (!pq.isEmpty()) {
            Node c = pq.poll();

            if (c.cost > visited[c.y][c.x][c.arrivedDirection][c.straightSteps]) {
                continue;
            }

            if (c.y == maxY - 1 && c.x == maxX - 1) {
                System.out.println(c.cost);
                return;
            }

            for (int nextDir = 0; nextDir < 4; nextDir++) {

                // cannot immediately turn back
                if (nextDir == (c.arrivedDirection + 2) % 4) {
                    continue;
                }
                int nextY = c.y + DY[nextDir];
                int nextX = c.x + DX[nextDir];

                // bounds check
                if (nextY < 0 || nextY >= maxY || nextX < 0 || nextX >= maxX) {
                    continue;
                }

                int nextStraightSteps;
                if(nextDir == c. arrivedDirection){
                    nextStraightSteps = c.straightSteps + 1;
                } else {
                    nextStraightSteps = 1;
                }

                if (nextStraightSteps > MAX_STRAIGHT_STEPS) {
                    continue;
                }
                if (isNextStateBetter(nextY, nextX, nextDir, nextStraightSteps, visited, c.cost)) {
                    continue;
                }
                int newCost = c.cost + lavaMap[nextY][nextX];
                Node nextNode = new Node(nextY, nextX, nextDir, nextStraightSteps, newCost);

                updateVisited(nextNode, visited);
                pq.add(nextNode);
            }
        }
    }

    public void part2(){
        prepareMap();
        // top -> 0, right -> 1, down -> 2, left -> 3;
        int maxY = lavaMap.length;
        int maxX = lavaMap[0].length;
        int[][][][] visited = new int[maxY][maxX][4][MAX_STRAIGHT_PART2 + 1]; // y,x,arrivedDirection, straightSteps -> "6,8,>,2"
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                for (int dir = 0; dir < 4; dir++) {
                    Arrays.fill(visited[y][x][dir], Integer.MAX_VALUE);
                }
            }
        }

        // two starting nodes added manually, heat loss from 0,0 not incurred according to puzzle description:
        Node startingNode1 = new Node(0,1, 1, 1, lavaMap[0][1]);
        Node startingNode2 = new Node(1,0,2,1, lavaMap[1][0]);
        updateVisited(startingNode1, visited);
        updateVisited(startingNode2, visited);

        Queue<Node> pq = new PriorityQueue<>(Comparator.comparing(a -> a.cost));
        pq.add(startingNode1);
        pq.add(startingNode2);

        while (!pq.isEmpty()) {
            Node c = pq.poll();

            if (c.cost > visited[c.y][c.x][c.arrivedDirection][c.straightSteps]) {
                continue;
            }

            if (c.y == maxY - 1 && c.x == maxX - 1 && c.straightSteps >= MIN_STRAIGHT_PART2) {
                System.out.println(c.cost);
                return;
            }

            for (int nextDir = 0; nextDir < 4; nextDir++) {
                // cannot immediately turn back

                if (nextDir == (c.arrivedDirection + 2) % 4) {
                    continue;
                }

                int nextY = c.y + DY[nextDir];
                int nextX = c.x + DX[nextDir];
                // bounds check
                if (nextY < 0 || nextY >= maxY || nextX < 0 || nextX >= maxX) {
                    continue;
                }

                if(c.arrivedDirection != nextDir && c.straightSteps < MIN_STRAIGHT_PART2){
                    continue;
                }
                int nextStraightSteps;
                if(nextDir == c. arrivedDirection){
                    nextStraightSteps = c.straightSteps + 1;
                } else {
                    nextStraightSteps = 1;
                }

                if (nextStraightSteps > MAX_STRAIGHT_PART2) {
                    continue;
                }
                if (isNextStateBetter(nextY, nextX, nextDir, nextStraightSteps, visited, c.cost)) {
                    continue;
                }
                int newCost = c.cost + lavaMap[nextY][nextX];
                Node nextNode = new Node(nextY, nextX, nextDir, nextStraightSteps, newCost);

                updateVisited(nextNode, visited);
                pq.add(nextNode);
            }
        }
    }

    private void updateVisited(Node n, int[][][][] v){
        v[n.y][n.x][n.arrivedDirection][n.straightSteps] = n.cost;
    }

    private boolean isNextStateBetter(int y, int x, int dir, int steps, int[][][][] v, int currentCost){
        int newCost = currentCost + lavaMap[y][x];
        return newCost >= v[y][x][dir][steps];
    }
}