package year2017.day22;

class Carrier {
    private int x;
    private int y;
    private Direction direction;

    Carrier(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void moveForward(){
        switch (direction){
            case UP:{y--; break;}
            case RIGHT:{x++; break;}
            case DOWN:{y++; break;}
            case LEFT:{x--; break;}
        }
    }

    void turnRight(){
        switch (direction){
            case UP:{direction = Direction.RIGHT;break;}
            case RIGHT:{direction = Direction.DOWN;break;}
            case DOWN:{direction = Direction.LEFT;break;}
            case LEFT:{direction = Direction.UP;break;}
        }
    }

    void turnLeft(){
        switch (direction){
            case UP:{direction = Direction.LEFT;break;}
            case RIGHT:{direction = Direction.UP;break;}
            case DOWN:{direction = Direction.RIGHT;break;}
            case LEFT:{direction = Direction.DOWN;break;}
        }
    }

    void reverse(){
        switch (direction){
            case UP:{direction = Direction.DOWN;break;}
            case RIGHT:{direction = Direction.LEFT;break;}
            case DOWN:{direction = Direction.UP;break;}
            case LEFT:{direction = Direction.RIGHT;break;}
        }
    }
}


