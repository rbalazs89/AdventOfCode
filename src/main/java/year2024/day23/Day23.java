package year2024.day23;

import main.ReadLines;

import java.util.*;

public class Day23 {
    private static final ReadLines reader = new ReadLines(2024, 23, 2);
    private static Node[] graph;
    private static final HashMap<String, Node> graphMap = new HashMap<>();
    private static final List<Node[]> threeNodes = new ArrayList<>();

    private void prepare(){
        graphMap.clear();
        threeNodes.clear();

        List<String> input = reader.readFile();
        Set<String> names = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            names.add(input.get(i).substring(0,2));
            names.add(input.get(i).substring(3,5));
        }

        graph = new Node[names.size()];
        names.clear();

        for (int i = 0; i < input.size(); i++) {
            String s1 = input.get(i).substring(0,2);
            String s2 = input.get(i).substring(3,5);

            if(names.add(s1)){
                Node node1 = new Node();

                node1.connectedNodes = new ArrayList<>();
                node1.value = s1;
                addNodeToArray(node1);
                graphMap.put(s1, node1);
            }

            if(names.add(s2)){
                Node node2 = new Node();

                node2.connectedNodes = new ArrayList<>();
                node2.value = s2;
                addNodeToArray(node2);
                graphMap.put(s2, node2);
            }

            graphMap.get(s1).connectNode(graphMap.get(s2));
        }
    }

    public void part1(){
        prepare();
        int result = 0;

        for (Node current : graph) {
            for (int j = 0; j < current.connectedNodes.size(); j++) {
                Node secondNode = current.connectedNodes.get(j);

                if (secondNode.value.compareTo(current.value) > 0) {
                    for (int k = 0; k < secondNode.connectedNodes.size(); k++) {
                        Node thirdNode = secondNode.connectedNodes.get(k);

                        if (thirdNode.value.compareTo(secondNode.value) > 0
                                && thirdNode.connectedNodes.contains(current)) {

                            threeNodes.add(new Node[]{current, secondNode, thirdNode});

                            if (current.value.charAt(0) == 't'
                                    || secondNode.value.charAt(0) == 't'
                                    || thirdNode.value.charAt(0) == 't') {
                                result++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(result);
    }


    // requires part 1
    public void part2(){
        int maxCliqueSize = 0;
        ArrayList<Node> maxClique = new ArrayList<>();

        for (int i = 0; i < threeNodes.size(); i++) {
            ArrayList<Node> connectedGroup = new ArrayList<>();
            connectedGroup.add(threeNodes.get(i)[0]);
            connectedGroup.add(threeNodes.get(i)[1]);
            connectedGroup.add(threeNodes.get(i)[2]);

            boolean expanded;
            do {
                expanded = false;

                for (int j = 0; j < connectedGroup.size(); j++) {
                    Node current = connectedGroup.get(j);

                    for (int k = 0; k < current.connectedNodes.size(); k++) {
                        Node potential = current.connectedNodes.get(k);

                        boolean alreadyInGroup = false;
                        for (int l = 0; l < connectedGroup.size(); l++) {
                            if (connectedGroup.get(l) == potential) {
                                alreadyInGroup = true;
                                break;
                            }
                        }

                        if (!alreadyInGroup) {
                            boolean isClique = true;
                            for (int l = 0; l < connectedGroup.size(); l++) {
                                if (!connectedGroup.get(l).connectedNodes.contains(potential)) {
                                    isClique = false;
                                    break;
                                }
                            }

                            if (isClique) {
                                connectedGroup.add(potential);
                                expanded = true;
                            }
                        }
                    }
                }
            } while (expanded);

            if (connectedGroup.size() > maxCliqueSize) {
                maxCliqueSize = connectedGroup.size();
                maxClique = connectedGroup;
            }
        }

        maxClique.sort(Comparator.comparing(a -> a.value));

        for (int i = 0; i < maxClique.size(); i++) {
            if(i < maxCliqueSize - 1){
                System.out.print(maxClique.get(i).value + ",");
            } else {
                System.out.print(maxClique.get(i).value);
            }
        }
    }

    private static void addNodeToArray(Node node){
        for (int i = 0; i < graph.length; i++) {
            if(graph[i] == null){
                graph[i] = node;
                return;
            }
        }
    }
}