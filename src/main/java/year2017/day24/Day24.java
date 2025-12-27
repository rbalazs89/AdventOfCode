package year2017.day24;

import main.ReadLines;

import java.util.*;

public class Day24 {
    private final ReadLines reader = new ReadLines(2017, 24, 2);

    private List<Component> getComponents(){
        List<String> fileLines = reader.readFile();
        ArrayList<Component> components = new ArrayList<>();

        for (int i = 0; i < fileLines.size(); i++) {
            String[] line = fileLines.get(i).split("/");
            components.add(new Component(Integer.parseInt(line[0]), Integer.parseInt(line[1]), i));
        }
        return components;
    }

    public void part1(){
        List<Component> components = getComponents();
        System.out.println(searchStrongestBridge(components));
    }

    public void part2(){
        List<Component> components = getComponents();
        System.out.println(searchLongestBridge(components));
    }

    private int searchAllBridges(boolean mustBeLongest, List<Component> components){
        // prepare queue:
        BridgeConfiguration firstBridgeConfiguration = new BridgeConfiguration(0, new HashSet<>(), 0, 0);
        Queue<BridgeConfiguration> queue = new LinkedList<>();
        queue.add(firstBridgeConfiguration);

        int highestBridgeStrength = 0;
        int longestBridge = 0;
        while (!queue.isEmpty()){
            BridgeConfiguration currentBridgeConfiguration = queue.poll();

            // keep track of the strongest bridge:
            if(mustBeLongest){
                if(longestBridge <= currentBridgeConfiguration.getNumberOfComponent()){
                    if(longestBridge == currentBridgeConfiguration.getNumberOfComponent()){
                        highestBridgeStrength = Math.max(highestBridgeStrength, currentBridgeConfiguration.getCurrentStrength());
                    } else {
                        highestBridgeStrength = currentBridgeConfiguration.getCurrentStrength();
                    }
                    longestBridge = Math.max(currentBridgeConfiguration.getNumberOfComponent(), longestBridge);
                }
            } else {
                highestBridgeStrength = Math.max(highestBridgeStrength, currentBridgeConfiguration.getCurrentStrength());
            }


            for (Component c : components) {
                BridgeConfiguration nextBridgeConfiguration = currentBridgeConfiguration.tryExtend(c);
                if(nextBridgeConfiguration != null) {
                    queue.add(nextBridgeConfiguration);
                }
            }
        }
        return highestBridgeStrength;
    }
    private int searchStrongestBridge(List<Component> components) {
        return searchAllBridges(false, components);
    }

    private int searchLongestBridge(List<Component> components) {
        return searchAllBridges(true, components);
    }
}