package year2018.day13;

class Cart {
    private int x;
    private int y;
    private MoveDirection moveDirection;
    private TurnDirection lastTurn;

    Cart(int x, int y, MoveDirection moveDirection){
        this.x = x;
        this.y = y;
        this.moveDirection = moveDirection;
    }

    TurnDirection getTurnDirection(){
        if(lastTurn == null){
            lastTurn = TurnDirection.LEFT;
            return TurnDirection.LEFT;
        }

        return switch (lastTurn) {
            case TurnDirection.LEFT -> {
                lastTurn = TurnDirection.STRAIGHT;
                yield TurnDirection.STRAIGHT;
            }
            case TurnDirection.STRAIGHT -> {
                lastTurn = TurnDirection.RIGHT;
                yield TurnDirection.RIGHT;
            }
            case TurnDirection.RIGHT -> {
                lastTurn = TurnDirection.LEFT;
                yield TurnDirection.LEFT;
            }
        };
    }

    void moveCart(){
        switch (moveDirection){
            case UP -> y --;
            case RIGHT -> x ++;
            case DOWN -> y ++;
            case LEFT -> x --;
        }
    }

    void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    MoveDirection getMoveDirection() {
        return moveDirection;
    }
}