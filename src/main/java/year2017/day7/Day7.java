package year2017.day7;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

    // setup:
    private final ReadLines reader = new ReadLines(2017, 7, 2);
    private List<String> fileLines; // lines from txt file, updated once
    private final ArrayList<Vertex> tower = new ArrayList<>();

    // puzzle:
    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        tower.clear();

        // example line of AoC input: guttanj (208) -> tovvj, vkkgwbr, xcndr, ksioqg
        // prepare patterns
        Pattern namePattern = Pattern.compile("[a-z]+");
        Pattern digitPattern = Pattern.compile("\\d+");

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher nameMatcher = namePattern.matcher(line);
            Matcher digitMatcher = digitPattern.matcher(line);
            boolean firstFind = false;
            String firstName = null;

            // set up vertexes
            // set up incoming and outgoing
            while (nameMatcher.find()){
                String current = nameMatcher.group();
                addTower(current);
                if(!firstFind){
                    firstName = current;
                } else {
                    getTower(firstName).addVertexOut(getTower(current)); // always adds the first element in line
                }
                firstFind = true;
            }

            // one number each line
            while (digitMatcher.find()){
                int weight = Integer.parseInt(digitMatcher.group());
                Vertex current = getTower(firstName);

                current.setBalancedWeight(weight);
                current.setOriginalWeight(weight);
            }
        }
    }

    public void part1(){
        prepare();

        // call the one with no incoming vertex
        System.out.println(getHighest().getName());

    }

    public void part2(){
        prepare();
        findDepthOfAllVertices();

        // find the lowest depth to start the investigation
        int depthToCheck = Integer.MIN_VALUE;
        for (int i = 0; i < tower.size(); i++) {
            depthToCheck = Math.max(depthToCheck, tower.get(i).getDepthInGraph());
        }

        // marker to see when deeper investigation is needed in a branch
        boolean instabilityFound = false;

        // to start instability search, first search starts with depthToCheck - 1
        // always checks the deeper outgoing programs, then updates weight
        whileLoop:
        while (depthToCheck >= 0){
            depthToCheck--;
            for (int i = 0; i < tower.size(); i++) {
                if(tower.get(i).getDepthInGraph() == depthToCheck){

                    // investigate stability of one, then update weight accordingly
                    Vertex current = tower.get(i);

                    if(current.getOutgoing().isEmpty()){
                        continue; // nothing to balance if no outgoing programs
                    }

                    // get the sum of weights
                    int sum = 0;
                    for (int j = 0; j < current.getOutgoing().size(); j++) {
                        sum += current.getOutgoing().get(j).getBalancedWeight();
                    }

                    // task description states there is only one unstable element
                    // use average to investigate:
                    int avg = sum / current.getOutgoing().size();
                    int maxValue = 0;
                    for (int j = 0; j < current.getOutgoing().size(); j++) {
                        maxValue = Math.max(Math.abs(avg - current.getOutgoing().get(j).getBalancedWeight()), maxValue);
                        if(maxValue != 0){ // instability found if any of the element weight is not average
                            instabilityFound = true;
                        }
                    }

                    // loop again to investigate more in detail the unstable branch:
                    if(instabilityFound){
                        int supposedWeight = 0;
                        int unstableWeight = 0;
                        Vertex unstableVertex = null;
                        for (int j = 0; j < current.getOutgoing().size(); j++) {

                            // this is the weight causing instability:
                            if(Math.abs(avg - current.getOutgoing().get(j).getBalancedWeight()) == maxValue){
                                unstableVertex = current.getOutgoing().get(j);
                                unstableWeight = unstableVertex.getBalancedWeight();
                            }
                            // the ones not matching the max are the balanced weights
                            if(Math.abs(avg - current.getOutgoing().get(j).getBalancedWeight()) != maxValue){
                                supposedWeight = current.getOutgoing().get(j).getBalancedWeight();
                            }
                        }

                        // balanced weights already includes programs from lower depths in the same branch
                        // task description asks for original:
                        System.out.println(supposedWeight - unstableWeight + unstableVertex.getOriginalWeight());
                        break whileLoop;
                    }

                    // no instability found, update weights and continue with next loop
                    current.setBalancedWeight(current.getBalancedWeight() + avg * current.getOutgoing().size()); // updates weights of the program
                }
            }
        }
    }

    private void addTower(String name){
        for (int i = 0; i < tower.size(); i++) {
            if(tower.get(i).getName().equals(name)){
                return;
            }
        }
        tower.add(new Vertex(name));
    }

    private Vertex getTower(String name){
        for (int i = 0; i < tower.size(); i++) {
            Vertex current = tower.get(i);
            if(current.getName().equals(name)){
                return current;
            }
        }
        throw new IllegalStateException("Graph " + name + "not found");
    }

    private Vertex getHighest(){
        for (int i = 0; i < tower.size(); i++) {
            if(tower.get(i).getIncoming().isEmpty()){
                return getTower(tower.get(i).getName());
            }
        }
        throw new IllegalStateException("Graph was prepared, but highest not found");
    }

    private void findDepthOfAllVertices(){
        // set up queue to get depth
        int depth = 0;
        Queue<Vertex> queue = new LinkedList<>();
        Vertex highest = getHighest();
        highest.setDepthInGraph(depth);
        queue.add(highest);

        // start while loop
        while (!queue.isEmpty()){
            depth ++;

            int sizeOfThisBatch = queue.size(); // required for correct depth calculation
            for (int i = 0; i < sizeOfThisBatch; i++) {
                Vertex current = queue.poll();
                List<Vertex> currentList = current.getOutgoing();

                // every vertex added from the next level and depth assigned
                for (int j = 0; j < currentList.size(); j++) {
                    Vertex nextDepth = getTower(currentList.get(j).getName());
                    nextDepth.setDepthInGraph(depth);
                    queue.add(nextDepth);
                }
            }
        }
    }
}