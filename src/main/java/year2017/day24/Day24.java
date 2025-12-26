package year2017.day24;

import main.ReadLines;

import java.util.*;

public class Day24 {
    private final ReadLines reader = new ReadLines(2017, 24, 2);

    private ArrayList<Component> getComponents(){
        List<String> fileLines = reader.readFile();
        ArrayList<Component> components = new ArrayList<>();

        for (int i = 0; i < fileLines.size(); i++) {
            String[] line = fileLines.get(i).split("/");
            components.add(new Component(Integer.parseInt(line[0]), Integer.parseInt(line[1]), i));
        }
        return components;
    }

    public void part1(){
        // setup:
        ArrayList<Component> components = getComponents();
        Graph firstGraph = new Graph(new ArrayList<>(), new HashSet<>(), 0, 0);
        Queue<Graph> queue = new LinkedList<>();
        queue.add(firstGraph);

        // BFS style search:
        int highestBridgeStrength = 0;
        while (!queue.isEmpty()){
            Graph currentGraph = queue.poll();

            // keep track of the strongest bridge:
            highestBridgeStrength = Math.max(highestBridgeStrength, currentGraph.getCurrentStrength());

            for (int i = 0; i < components.size(); i++) {
                Graph nextGraph = currentGraph.getPotentialNewGraph(components.get(i));
                if(nextGraph != null) {
                    queue.add(nextGraph);
                }
            }
        }
        System.out.println(highestBridgeStrength);
    }

    //private int(BFS)

    public void part2(){
        // setup:
        ArrayList<Component> components = getComponents();
        Graph firstGraph = new Graph(new ArrayList<>(), new HashSet<>(), 0, 0);
        Queue<Graph> queue = new LinkedList<>();
        queue.add(firstGraph);

        // BFS style search:
        int highestBridgeStrength = 0;
        int longestBridge = 0;

        while (!queue.isEmpty()){
            Graph currentGraph = queue.poll();

            // find thes trongest only if length is the greatest
            if(longestBridge <= currentGraph.getBridgeLength()){
                if(longestBridge == currentGraph.getBridgeLength()){
                    highestBridgeStrength = Math.max(highestBridgeStrength, currentGraph.getCurrentStrength());
                } else {
                    highestBridgeStrength = currentGraph.getCurrentStrength();
                }
                longestBridge = Math.max(currentGraph.getBridgeLength(), longestBridge);
            }

            for (int i = 0; i < components.size(); i++) {
                Graph nextGraph = currentGraph.getPotentialNewGraph(components.get(i));
                if(nextGraph != null) {
                    queue.add(nextGraph);
                }
            }
        }
        System.out.println(highestBridgeStrength);
    }
}