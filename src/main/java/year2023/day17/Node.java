package year2023.day17;

class Node {
    int cost;
    int x;
    int y;
    int arrivedDirection;
    int straightSteps; // how many straights steps in a row were used to arrive to this node
    Node(int y, int x, int arrivedDirection, int straightSteps, int cost){
        this.y = y;
        this.x = x;
        this.arrivedDirection = arrivedDirection;
        this.straightSteps = straightSteps;
        this.cost = cost;
    }
}