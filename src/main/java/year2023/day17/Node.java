package year2023.day17;

class Node {
    int cost;
    int x;
    int y;
    char arrivedDirection; // direction {^,>,<,v} // arrived to this node with this direction
    Node(int y, int x){
        this.y = y;
        this.x = x;
    }
    Node previous;
    Node next;
}
