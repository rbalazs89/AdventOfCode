package year2017.day24;

import java.util.*;

public class Graph {
    private ArrayList<Component> bridge = new ArrayList<>();
    private int latestBridgeEnding;
    private int currentStrength;
    private Set<Integer> usedComponentIDs = new HashSet<>();

    Graph getPotentialNewGraph(Component bridgeElement){
        if(isConnectionPossible(bridgeElement)){
            Graph newGraph = deepCopyGraph(this);

            // assign latest bridge ending to the new Graph based on component
            if(bridgeElement.getPortNumber1() == latestBridgeEnding){
                newGraph.latestBridgeEnding = bridgeElement.getPortNumber2();
            } else {
                newGraph.latestBridgeEnding = bridgeElement.getPortNumber1();
            }

            // make sure new is added
            newGraph.usedComponentIDs.add(bridgeElement.getUniqueID());

            // update strength based on component
            newGraph.currentStrength = currentStrength + bridgeElement.getPortNumber1() + bridgeElement.getPortNumber2();
            newGraph.bridge.add(bridgeElement);

            return newGraph;
        }

        return null;
    }

    boolean isConnectionPossible(Component bridgeElement){
        return (bridgeElement.getPortNumber1() == latestBridgeEnding || bridgeElement.getPortNumber2() == latestBridgeEnding) && !usedComponentIDs.contains(bridgeElement.getUniqueID());
    }

    Graph (ArrayList<Component> bridge, Set<Integer> usedComponentIDs, int currentStrength, int latestBridgeEnding){
        this.bridge = bridge;
        this.usedComponentIDs = usedComponentIDs;
        this.currentStrength = currentStrength;
        this.latestBridgeEnding = latestBridgeEnding;
    }

    Graph deepCopyGraph(Graph original){
        ArrayList<Component> bridgeCopy = new ArrayList<>(original.bridge);
        Set<Integer> usedComponentsCopy = new HashSet<>(original.usedComponentIDs);
        return new Graph(bridgeCopy, usedComponentsCopy, original.latestBridgeEnding, original.currentStrength);
    }

    int getCurrentStrength() {
        return currentStrength;
    }

    int getBridgeLength(){
        return bridge.size();
    }
}