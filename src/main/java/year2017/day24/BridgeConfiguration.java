package year2017.day24;

import java.util.*;

public class BridgeConfiguration {
    private int numberOfComponents;
    private int latestBridgeEnding;
    private int currentStrength;
    private final Set<Integer> usedComponentIDs;

    BridgeConfiguration tryExtend(Component bridgeElement){
        if(isConnectionPossible(bridgeElement)){
            BridgeConfiguration newBridgeConfiguration = deepCopyConfiguration(this);

            // assign latest bridge ending to the new Graph based on component
            if(bridgeElement.portNumber1() == latestBridgeEnding){
                newBridgeConfiguration.latestBridgeEnding = bridgeElement.portNumber2();
            } else {
                newBridgeConfiguration.latestBridgeEnding = bridgeElement.portNumber1();
            }

            // make sure new is added to already used set
            newBridgeConfiguration.usedComponentIDs.add(bridgeElement.uniqueID());
            newBridgeConfiguration.numberOfComponents ++;

            // update strength based on component
            newBridgeConfiguration.currentStrength = currentStrength + bridgeElement.portNumber1() + bridgeElement.portNumber2();

            return newBridgeConfiguration;
        }

        return null;
    }

    boolean isConnectionPossible(Component bridgeElement){
        return (bridgeElement.portNumber1() == latestBridgeEnding || bridgeElement.portNumber2() == latestBridgeEnding) && !usedComponentIDs.contains(bridgeElement.uniqueID());
    }

    BridgeConfiguration(int numberOfComponents, Set<Integer> usedComponentIDs, int currentStrength, int latestBridgeEnding){
        this.numberOfComponents = numberOfComponents;
        this.usedComponentIDs = usedComponentIDs;
        this.currentStrength = currentStrength;
        this.latestBridgeEnding = latestBridgeEnding;
    }

    static BridgeConfiguration deepCopyConfiguration(BridgeConfiguration original){
        Set<Integer> usedComponentsCopy = new HashSet<>(original.usedComponentIDs);
        return new BridgeConfiguration(original.numberOfComponents, usedComponentsCopy, original.currentStrength, original.latestBridgeEnding);
    }

    int getCurrentStrength() {
        return currentStrength;
    }

    int getNumberOfComponent(){
        return numberOfComponents;
    }
}