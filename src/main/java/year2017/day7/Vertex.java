package year2017.day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Vertex {
    private final ArrayList<Vertex> outgoing = new ArrayList<>();
    private final ArrayList<Vertex> incoming = new ArrayList<>();
    private final String name;
    private int balancedWeight; // gets updated over as graph is travelled for simple calculation
    private int depthInGraph;
    private int originalWeight;

    Vertex(String name){
        this.name = name;
    }

    String getName() {
        return name;
    }

    void addVertexOut(Vertex vertex){
        String name = vertex.name;
        for (int i = 0; i < outgoing.size(); i++) {
            if(outgoing.get(i).name.equals(name)){
                return;
            }
        }
        outgoing.add(vertex);
        vertex.addVertexIn(this);
    }

    void addVertexIn(Vertex vertex){
        String name = vertex.name;
        for (int i = 0; i < incoming.size(); i++) {
            if(incoming.get(i).name.equals(name)){
                return;
            }
        }
        incoming.add(vertex);
    }

    List<Vertex> getOutgoing() {
        return Collections.unmodifiableList(outgoing);
    }

    List<Vertex> getIncoming() {
        return Collections.unmodifiableList(incoming);
    }

    int getBalancedWeight() {
        return balancedWeight;
    }

    void setBalancedWeight(int balancedWeight) {
        this.balancedWeight = balancedWeight;
    }

    int getDepthInGraph() {
        return depthInGraph;
    }

    void setDepthInGraph(int depthInGraph) {
        this.depthInGraph = depthInGraph;
    }

    int getOriginalWeight() {
        return originalWeight;
    }

    void setOriginalWeight(int originalWeight) {
        this.originalWeight = originalWeight;
    }
}
