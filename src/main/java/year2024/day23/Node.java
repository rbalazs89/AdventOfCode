package year2024.day23;

import java.util.ArrayList;

class Node {
    String value;
    ArrayList<Node> connectedNodes;

    void connectNode(Node node){
        if(!connectedNodes.contains(node)){
            connectedNodes.add(node);
        }
        if(!node.connectedNodes.contains(this)){
            node.connectedNodes.add(this);
        }
    }
}
