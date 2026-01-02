package year2024.day16;

import main.ReadLines;

import java.util.*;

public class Day16 {
    private static final ReadLines reader = new ReadLines(2024, 16, 2);

    // note: boundary is wall everywhere -> no out of bounds checks are needed during traversal
    private static final char WALL = '#';
    private static final char START_SYMBOL = 'S';
    private static final char END_SYMBOL = 'E';
    private static final int TURN_COST = 1000; // costs 1000 points to make a turn, instead of 1 form one step
    private static char[][] maze;
    private static int startX;
    private static int startY;
    private static int endX;
    private static int endY;
    private static final char startingDirection = '>'; // specified by task description

    // part 2:
    private static Path winningPath;

    // sets up the starting grid:
    private void setupMaze(){
        List<String> input = reader.readFile();
        int maxY = input.size();
        int maxX = input.getFirst().length();

        maze = new char[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                char c = input.get(y).charAt(x);
                maze[y][x] = c;

                if(c == START_SYMBOL){
                    startY = y;
                    startX = x;
                } else if (c == END_SYMBOL){
                    endX = x;
                    endY = y;
                }
            }
        }
    }

    public void part1(){
        setupMaze();
        // set up starting node:
        Node startingNode = new Node(startY, startX);
        startingNode.arrivedDirection = startingDirection;
        Set<String> visited = new HashSet<>();
        visited.add(startingNode.y + "," + startingNode.x);

        // dijkstra:
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        pq.add(startingNode);

        while(!pq.isEmpty()){
            Node c = pq.poll(); // current
            if(c.x == endX && c.y == endY){
                System.out.println(c.cost);
                return;
            }

            // check top:
            if(maze[c.y - 1][c.x] != WALL){
                if(visited.add((c.y - 1) + "," + c.x)){
                    Node newNode = new Node(c.y - 1, c.x);
                    newNode.arrivedDirection = '^';
                    if(c.arrivedDirection == '^') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check right:
            if(maze[c.y][c.x + 1] != WALL){
                if(visited.add(c.y + "," + (c.x + 1))){
                    Node newNode = new Node(c.y, c.x + 1);
                    newNode.arrivedDirection = '>';
                    if(c.arrivedDirection == '>') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check down:
            if(maze[c.y + 1][c.x] != WALL){
                if(visited.add((c.y + 1) + "," + c.x)){
                    Node newNode = new Node(c.y + 1, c.x);
                    newNode.arrivedDirection = 'v';
                    if(c.arrivedDirection == 'v') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check left:
            if(maze[c.y][c.x - 1] != WALL){
                if(visited.add((c.y) + "," + (c.x - 1))){
                    Node newNode = new Node(c.y, (c.x - 1));
                    newNode.arrivedDirection = '<';
                    if(c.arrivedDirection == '<') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }
        }
    }


    public void part2(){
        // --------------------------------------------------
        // similar to part one, but winning path is saved
        // --------------------------------------------------
        setupMaze();

        // set up starting node:
        Node startingNode = new Node(startY, startX);
        startingNode.arrivedDirection = startingDirection;

        Set<String> visited = new HashSet<>();
        visited.add(startingNode.y + "," + startingNode.x);

        // dijkstra:
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        pq.add(startingNode);


        while(!pq.isEmpty()){

            Node c = pq.poll(); // current node

            if(c.x == endX && c.y == endY){
                constructWinningPath(c);
                break;
            }

            // check top:
            if(maze[c.y - 1][c.x] != WALL){
                if(visited.add((c.y - 1) + "," + c.x)){
                    Node newNode = new Node(c.y - 1, c.x);
                    newNode.arrivedDirection = '^';
                    if(c.arrivedDirection == '^') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check right:
            if(maze[c.y][c.x + 1] != WALL){
                if(visited.add(c.y + "," + (c.x + 1))){
                    Node newNode = new Node(c.y, c.x + 1);
                    newNode.arrivedDirection = '>';
                    if(c.arrivedDirection == '>') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check down:
            if(maze[c.y + 1][c.x] != WALL){
                if(visited.add((c.y + 1) + "," + c.x)){
                    Node newNode = new Node(c.y + 1, c.x);
                    newNode.arrivedDirection = 'v';
                    if(c.arrivedDirection == 'v') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check left:
            if(maze[c.y][c.x - 1] != WALL){
                if(visited.add((c.y) + "," + (c.x - 1))){
                    Node newNode = new Node(c.y, (c.x - 1));
                    newNode.arrivedDirection = '<';
                    if(c.arrivedDirection == '<') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }
        }

        // --------------------------------------
        // find others from winningPath
        // --------------------------------------
        // rejoining node

        for (int i = 0; i < winningPath.allNodes.size(); i++) {
            findPathWithSameCost(winningPath.allNodes.get(i));
        }
        System.out.println(winningPath.allNodes.size());
    }

    private void constructWinningPath(Node winningNode){
        winningPath = new Path();
        winningPath.allNodes = new ArrayList<>();

        // Add next field to all nodes from the winning path
        Node currentNode = winningNode;
        while(currentNode.previous != null){
            winningPath.allNodes.add(currentNode);
            Node previous = currentNode.previous;
            previous.next = currentNode;
            currentNode = previous;
        }
        winningPath.allNodes.add(currentNode); // this adds the starting node

        winningPath.pathVisited = new HashSet<>();
        // add global visited, to check if branching path arrived back
        for (int i = 0; i < winningPath.allNodes.size(); i++) {
            Node n = winningPath.allNodes.get(i);
            winningPath.pathVisited.add(n.y + "," + n.x);
        }
    }

    private void findPathWithSameCost(Node branchStartingNode){
        Set<String> visited = new HashSet<>();
        visited.add(branchStartingNode.y + "," + branchStartingNode.x);
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        pq.add(branchStartingNode);

        while(!pq.isEmpty()){
            Node c = pq.poll(); // current node

            //ending check
            if(winningPath.pathVisited.contains(c.y + "," + c.x) && c != branchStartingNode){
                Node rejoinNode = getNodeFromCoords(c.y, c.x);

                // valid merge found:
                if(rejoinNode.previous != null){
                    if(Math.abs(rejoinNode.previous.cost - c.previous.cost) == 1000 && rejoinNode.cost == c.cost){
                        addBranchToWinningPath(c, branchStartingNode);
                    }
                }
            }

            if (winningPath.pathVisited.contains(c.y + "," + c.x) && c != branchStartingNode) {
                if(winningPath.pathVisited.contains(c.previous.y + "," + c.previous.x)){
                    continue;
                }
            }

            // check top:
            if(maze[c.y - 1][c.x] != WALL){
                if(visited.add((c.y - 1) + "," + c.x)){
                    Node newNode = new Node(c.y - 1, c.x);
                    newNode.arrivedDirection = '^';
                    if(c.arrivedDirection == '^') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check right:
            if(maze[c.y][c.x + 1] != WALL){
                if(visited.add(c.y + "," + (c.x + 1))){
                    Node newNode = new Node(c.y, c.x + 1);
                    newNode.arrivedDirection = '>';
                    if(c.arrivedDirection == '>') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check down:
            if(maze[c.y + 1][c.x] != WALL){
                if(visited.add((c.y + 1) + "," + c.x)){
                    Node newNode = new Node(c.y + 1, c.x);
                    newNode.arrivedDirection = 'v';
                    if(c.arrivedDirection == 'v') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }

            // check left:
            if(maze[c.y][c.x - 1] != WALL){
                if(visited.add((c.y) + "," + (c.x - 1))){
                    Node newNode = new Node(c.y, (c.x - 1));
                    newNode.arrivedDirection = '<';
                    if(c.arrivedDirection == '<') {
                        newNode.cost = c.cost + 1;
                    } else {
                        newNode.cost = c.cost + 1 + TURN_COST;
                    }
                    newNode.previous = c;
                    pq.add(newNode);
                }
            }
        }
    }

    private void addBranchToWinningPath(Node mergedNode, Node branchStartingNode){
        // Add next field to all nodes from the winning path
        Node currentNode = mergedNode;
        while(currentNode.previous != null){

            // the only element that is already in the pathVisited should be the one merging node, the first in this while loop
            if(winningPath.pathVisited.add(currentNode.y + "," + currentNode.x)){ // add to winning hashset
                winningPath.allNodes.add(currentNode); // adds to winning array
            }
            Node previous = currentNode.previous;
            previous.next = currentNode;
            currentNode = previous;
            if(branchStartingNode == currentNode){
                break;
            }
        }
    }

    /**
     * Debug method draws the current maze:
     */
    private void drawGrid(){
        for (char[] chars : maze) {
            for (int x = 0; x < maze[0].length; x++) {
                System.out.print(chars[x]);
            }
            System.out.println();
        }
        System.out.println();
    }

    Node getNodeFromCoords(int y, int x){
        for (int i = 0; i < winningPath.allNodes.size(); i++) {
            Node c = winningPath.allNodes.get(i);
            if(c.y == y && c.x == x){
                return c;
            }
        }
        throw new IllegalStateException("only called if coordinate is available in the winning path");
    }
}