package year2023.day8;

import main.ReadLines;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day8 {
    private final ReadLines reader = new ReadLines(2023, 8, 2);
    private final ArrayList<Node> nodes = new ArrayList<>();
    private String instructions = "";

    private void prepare(){
        nodes.clear();
        instructions = "";
        List<String> input = reader.readFile();

        StringBuilder instruction = new StringBuilder();
        for (int i = 0; i < input.size(); i++) {
            if (!input.get(i).isEmpty()) {
                if (!input.get(i).contains("=")) {
                    instruction.append(input.get(i));
                }
                if (input.get(i).contains("=")) {
                    String tempString = input.get(i);
                    nodes.add(new Node(tempString.substring(0, 3), tempString.substring(7, 10), tempString.substring(12, 15)));
                }
            }
        }
        instructions = instruction.toString();
    }

    public void part1(){
        prepare();

        String currentNodeName = "AAA";
        Node currentNode = nodes.getFirst(); // will be overwritten
        int solution = 0;

        for (int test = 0; test < Integer.MAX_VALUE; test++) {
            solution++;
            char currentInstruction = instructions.charAt((solution - 1) % instructions.length());
            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j).name.equals(currentNodeName)) {
                    currentNode = nodes.get(j);
                    break;
                }
            }
            if (currentInstruction == 'R') {
                currentNodeName = currentNode.right;
            } else {
                currentNodeName = currentNode.left;
            }
            if (currentNodeName.equals("ZZZ")) {
                break;
            }
        }
        System.out.println(solution);
    }

    public void part2(){
        prepare();
        ArrayList<String> currentNodeNames = new ArrayList<>();
        ArrayList<Node> currentNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.endsWith("A")) {
                currentNodes.add(nodes.get(i));
                currentNodeNames.add(nodes.get(i).name);
            }
        }

        //TRAVEL THE GRAPH
        int solution = 0;
        int arrived = currentNodes.size();
        List<Integer> solutions = new ArrayList<>();

        for (int test = 0; test < Integer.MAX_VALUE; test++) {
            solution++;
            char currentInstruction = instructions.charAt((solution - 1) % instructions.length());

            for (int i = 0; i < currentNodes.size(); i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).name.equals(currentNodeNames.get(i))) {
                        currentNodes.set(i, nodes.get(j));
                        break;
                    }
                }
            }
            for (int i = 0; i < currentNodes.size(); i++) {
                if (currentInstruction == 'R') {
                    currentNodeNames.set(i, currentNodes.get(i).right);
                } else {
                    currentNodeNames.set(i, currentNodes.get(i).left);
                }
            }
            for (int i = 0; i < currentNodeNames.size(); i++) {
                if (currentNodeNames.get(i).endsWith("Z")) {
                    solutions.add(solution);
                }
            }
            if (solutions.size() == arrived) {
                break;
            }
        }

        //lcm solution works because each starting point only ever arrives to one ending point
        //all paths seem to be separate

        // code line above wouldn't work, if solutions were too far apart from each other
        // ie: one starting point meets ending point twice, before some other circle finishes

        BigInteger lcm = BigInteger.valueOf(solutions.getFirst());
        for (Integer integer : solutions) {
            lcm = lcm.multiply(BigInteger.valueOf(integer).divide(lcm.gcd(BigInteger.valueOf(integer))));
        }
        System.out.println(lcm);
    }
}