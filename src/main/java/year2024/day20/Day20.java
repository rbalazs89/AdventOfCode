package year2024.day20;

import main.ReadLines;

import java.util.*;

public class Day20 {
    private static final ReadLines reader = new ReadLines(2024, 20, 1);
    private static int maxX;
    private static int maxY;
    private static int startX = -1;
    private static int startY = -1;
    private static int endX = -1;
    private static int endY = -1;
    private static int shortestPathCost = Integer.MAX_VALUE;
    private static Node[][] field;
    private static final int SAVED_PICOSECONDS = 100; // specified by task description, 100 in real input

    private void prepare(){
        List<String> input = reader.readFile();
        shortestPathCost = Integer.MAX_VALUE;

        maxX = input.getFirst().length();
        maxY = input.size();
        field = new Node[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Node node = new Node();
                node.X = j;
                node.Y = i;
                node.wall = input.get(i).charAt(j) == '#';
                if(input.get(i).charAt(j) == 'S'){
                    startX = j;
                    startY = i;
                    node.costFromStart = 0;
                }
                if(input.get(i).charAt(j) == 'E'){
                    endX = j;
                    endY = i;
                }
                field[i][j] = node;
            }
        }
    }

    public void part1(){
        prepare();

        // get costs of all nodes from the starting point:
        Queue<Node> q = new PriorityQueue<>(Comparator.comparingInt(node -> node.costFromStart));
        q.add(field[startY][startX]);
        Set<Node> visited = new HashSet<>();
        visited.add(field[startY][startX]);
        while(!q.isEmpty()){
            Node current = q.poll();
            visited.add(current);
            int[][] steps = {{0,1},{0,-1},{1,0},{-1,0}};
            for (int[] step : steps) {
                int nextY = current.Y + step[0];
                int nextX = current.X + step[1];
                if (nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX) {
                    Node nextNode = field[nextY][nextX];
                    if (!nextNode.wall && !visited.contains(nextNode)) {
                        if (nextNode.costFromStart > current.costFromStart + 1) {
                            nextNode.costFromStart = current.costFromStart + 1;
                            q.add(nextNode);
                        }
                    }
                }
            }
        }
        shortestPathCost = field[endY][endX].costFromStart;

        //get cost of all nodes from the ending point:
        Queue<Node> q2 = new PriorityQueue<>(Comparator.comparingInt(node -> node.costFromStart));
        field[endY][endX].costFromEnd = 0;
        q2.add(field[endY][endX]);
        Set<Node> visited2 = new HashSet<>();
        visited2.add(field[endY][endX]);
        while(!q2.isEmpty()){
            Node current = q2.poll();
            visited2.add(current);
            int[][] steps = {{0,1},{0,-1},{1,0},{-1,0}};
            for (int[] step : steps) {
                int nextY = current.Y + step[0];
                int nextX = current.X + step[1];
                if (nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX) {
                    Node nextNode = field[nextY][nextX];
                    if (!nextNode.wall && !visited2.contains(nextNode)) {
                        if (nextNode.costFromEnd > current.costFromEnd + 1) {
                            nextNode.costFromEnd = current.costFromEnd + 1;
                            q2.add(nextNode);
                        }
                    }
                }
            }
        }

        //get how many seconds (cost) one jump would save:
        int result = getResult();
        System.out.println(result);
    }

    private static int getResult() {
        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                int[][] steps = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};
                Node current = field[i][j];
                for (int[] step : steps) {
                    int nextY = current.Y + step[0];
                    int nextX = current.X + step[1];
                    if (nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX && !field[nextY][nextX].wall && !current.wall) {
                        Node nextNode = field[nextY][nextX];
                        if (nextNode.costFromEnd + current.costFromStart + 2 <= shortestPathCost - SAVED_PICOSECONDS) {
                            result++;
                        }
                    }
                }
            }
        }
        return result;
    }

    // requires part 1
    public void part2(){
        // 20 max distance allowed instead of 2, create steps array:
        ArrayList<int[]> stepsList = new ArrayList<>();
        for (int k = -20; k <= 20; k++) {
            for (int l = -20; l <= 20; l++) {
                if (Math.abs(k) + Math.abs(l) <= 20) {
                    stepsList.add(new int[]{k, l});
                }
            }
        }
        int[][] steps = new int[stepsList.size()][3];

        for (int k = 0; k < stepsList.size(); k++) {
            steps[k][0] = stepsList.get(k)[0];
            steps[k][1] = stepsList.get(k)[1];
            steps[k][2] = Math.abs(steps[k][0]) + Math.abs(steps[k][1]);
        }

        int result = getResult(steps);
        System.out.println(result);
    }

    private static int getResult(int[][] steps) {
        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Node current = field[i][j];
                for (int[] step : steps) {
                    int nextY = current.Y + step[0];
                    int nextX = current.X + step[1];
                    if (nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX && !field[nextY][nextX].wall && !current.wall) {
                        Node nextNode = field[nextY][nextX];
                        if (nextNode.costFromEnd + current.costFromStart + step[2] <= shortestPathCost - SAVED_PICOSECONDS) {
                            result++;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * DEBUG METHOD
     */
    private static void printoutField(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(!field[i][j].wall){
                    if(field[i][j].costFromStart < 10){
                        System.out.print( "." + field[i][j].costFromStart);
                    } else {
                        System.out.print(field[i][j].costFromStart);
                    }

                } else {
                    System.out.print("##");
                }

            }
            System.out.println();
        }
        System.out.println();
    }

    private static class Node {
        private int X;
        private int Y;
        private int costFromStart = Integer.MAX_VALUE;
        private int costFromEnd = Integer.MAX_VALUE;
        private boolean wall;
    }
}
