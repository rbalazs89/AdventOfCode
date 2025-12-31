package year2024.day8;

import main.ReadLines;

import java.util.List;

public class Day8 {
    private final ReadLines reader = new ReadLines(2024, 8, 2);
    private static Node[][] nodes;
    private static int maxX;
    private static int maxY;

    private void prepare(){
        List<String> inputRaw = reader.readFile();
        maxX = inputRaw.getFirst().length();
        maxY  = inputRaw.size();
        nodes = new Node[maxY][maxX];
        for (int i = 0; i < inputRaw.size(); i++) {
            for (int j = 0; j < inputRaw.get(i).length(); j++) {
                Node node = new Node();
                node.x = j;
                node.y = i;
                node.frequency = String.valueOf(inputRaw.get(i).charAt(j));
                nodes[i][j] = node;
            }
        }
    }

    public void part1(){
        prepare();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Node currentNode = nodes[i][j];
                for (int k = 0; k < nodes.length; k++) {
                    for (int l = 0; l < nodes[0].length; l++) {
                        Node compare = nodes[k][l];
                        if (currentNode != compare && currentNode.frequency.equals(compare.frequency) && !currentNode.frequency.equals(".") &&
                                !currentNode.frequency.equals("#")) {
                            int xDif = currentNode.x - compare.x;
                            int yDif = currentNode.y - compare.y;
                            int newX = currentNode.x + xDif;
                            int newY = currentNode.y + yDif;
                            if (isInside(newX, newY)) {
                                nodes[newY][newX].containsAntiNode = true;
                            }
                        }
                    }
                }
            }
        }
        int result = 0;
        for (Node[] node : nodes) {
            for (int j = 0; j < nodes[0].length; j++) {
                if (node[j].containsAntiNode) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Node currentNode = nodes[i][j];
                for (int k = 0; k < nodes.length; k++) {
                    for (int l = 0; l < nodes[0].length; l++) {
                        Node compare = nodes[k][l];
                        if (currentNode != compare && currentNode.frequency.equals(compare.frequency) && !currentNode.frequency.equals(".") &&
                                !currentNode.frequency.equals("#")) {
                            int xDif = currentNode.x - compare.x;
                            int yDif = currentNode.y - compare.y;
                            for (int m = -51; m < 51; m++) {
                                int newX = currentNode.x + m * xDif;
                                int newY = currentNode.y + m * yDif;
                                if (isInside(newX, newY)) {
                                    nodes[newY][newX].containsAntiNode = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        int result = 0;
        for (Node[] node : nodes) {
            for (int j = 0; j < nodes[0].length; j++) {
                if (node[j].containsAntiNode) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    private static boolean isInside(int x, int y){
        return x >= 0 && x < maxX && y >= 0 && y < maxY;
    }

    private static class Node {
        boolean containsAntiNode = false;
        int x;
        int y;
        String frequency;
    }
}
