package year2024.day16;

class Node {
    int y;
    int x;
    int cost; // pq guaranteed lowest will be visited first
    Node previous;
    Node next;
    char arrivedDirection; // direction {^,>,<,v} // arrived to this node with this direction

    Node(int y, int x){
        this.y = y;
        this.x = x;
    }
}
