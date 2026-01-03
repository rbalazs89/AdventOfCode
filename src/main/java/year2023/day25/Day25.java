package year2023.day25;

import main.ReadLines;

import java.util.*;

public class Day25 {
    // this algorithm doesn't work for sample input, it relies on huge number of nodes
    private final ReadLines reader = new ReadLines(2023, 25, 2);
    private final Map<String, Component> network = new HashMap<>();
    private final List<Wire> allWires = new ArrayList<>();

    private void prepareNetwork(){
        List<String> lines = reader.readFile();
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).replace(":","").split(" ");

            for (String part : parts) {
                addToNetwork(part);
            }

            for (int j = 1; j < parts.length; j++) {
                Component c = network.get(parts[0]);
                Component other = network.get(parts[j]);
                c.doubleConnect(other);
            }
        }

        for (Map.Entry<String, Component> entry : network.entrySet()) {
            for (int i = 0; i < entry.getValue().wires.size(); i++) {
                Wire w = entry.getValue().wires.get(i);
                if(!allWires.contains(w)){
                    allWires.add(w);
                }
            }
        }
    }

    public void part1(){
        prepareNetwork();

        Component[] allComponents = new Component[network.size()];

        int i = 0;

        // travel all the network & fill allComponents
        for (Map.Entry<String, Component> entry : network.entrySet()) {
            allComponents[i] = entry.getValue();
            i ++;
            travelNetwork(entry.getValue());
        }

        allWires.sort(Comparator.comparingInt(a -> a.visited * -1));
        Wire first = allWires.getFirst();
        Wire second = allWires.get(1);
        Wire third = allWires.get(2);

        int wiresToBelDeleted = 0;
        for (Component c : allComponents) {
            if (c.wires.contains(first) || c.wires.contains(second) || c.wires.contains(third)) {
                wiresToBelDeleted++;
                c.deleteWire(first, second, third);
                if (wiresToBelDeleted == 6) { // double of 3, because they need to be deleted from both sides
                    break;
                }
            }
        }
        int clusterSizeOne = getClusterSize(allComponents[0]);
        int clusterSizeTwo = allComponents.length - clusterSizeOne;
        System.out.println(clusterSizeOne * clusterSizeTwo);
    }

    private void travelNetwork(Component startingComponent) {
        Set<String> visited = new HashSet<>();
        Queue<Component> q = new LinkedList<>();

        q.add(startingComponent);
        visited.add(startingComponent.name);

        while (!q.isEmpty()){
            Component c = q.poll();
            for (int i = 0; i < c.wires.size(); i++) {
                Component other = c.getConnectedFromWire(c.wires.get(i));
                Wire w = c.wires.get(i);
                if(visited.add(other.name)){
                    w.visited ++;
                    q.add(other);
                }
            }
        }
    }

    private int getClusterSize(Component startingComponent) {
        Set<String> visited = new HashSet<>();
        Queue<Component> q = new LinkedList<>();

        q.add(startingComponent);
        visited.add(startingComponent.name);

        while (!q.isEmpty()){
            Component c = q.poll();
            for (int i = 0; i < c.wires.size(); i++) {
                Component other = c.getConnectedFromWire(c.wires.get(i));
                Wire w = c.wires.get(i);
                if(visited.add(other.name)){
                    q.add(other);
                }
            }
        }
        return visited.size();
    }

    public void part2(){
        // no part 2, nothing to do here
    }


    private void addToNetwork(String name){
        if(!network.containsKey(name)){
            network.put(name, new Component(name));
        }
    }
}