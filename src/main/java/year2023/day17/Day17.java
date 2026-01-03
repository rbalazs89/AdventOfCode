package year2023.day17;

import main.ReadLines;

import java.util.*;

public class Day17 {
    private final ReadLines reader = new ReadLines(2023, 17, 1);
    private static int[][] lavaMap;

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
        Set<String> visited = new HashSet<>();
        Node startingNode = new Node(0,0);

        Queue<Node> pq = new PriorityQueue<>(Comparator.comparing(a -> a.cost));
        while(!pq.isEmpty()){
            Node c = pq.poll();
            if(c.y == lavaMap.length - 1 && c.x == lavaMap[0].length - 1){
                System.out.println(c.cost);
                break;
            }

            // check top:
            //TODO this is not good
            if(c.y - 1 >= 0 && c.arrivedDirection != 'v'){
                if(c.previous != null){
                    if(c.previous.previous != null){
                        if(c.previous.previous.previous != null){
                            if(c.previous.arrivedDirection == '^' && c.previous.previous.arrivedDirection == '^' && c.previous.previous.previous.arrivedDirection == '^'){

                            }
                        }
                    }
                }
            }
        }

    }

    public void part2(){

    }
}