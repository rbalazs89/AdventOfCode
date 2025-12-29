package year2018.day13;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day13 {
    private final ReadLines reader = new ReadLines(2018, 13, 2);
    private char[][] map; // map[y][x] // map never changes after initialization
    private List<Cart> carts;

    private void makeMapAndCarts(){
        // initialize:
        List<String> lines = reader.readFile();
        carts = new ArrayList<>();
        int maxY = lines.size();
        int maxX = getMaxLength(lines);
        map = new char[maxY][maxX];

        // fill grid & make new carts
        for (int y = 0; y < maxY; y ++) {
            for (int x = 0; x < maxX; x ++) {

                // input txt has empty nothing (not even spaces) after last x position in line
                if(lines.get(y).length() <= x){
                    map[y][x] = ' ';
                    continue;
                }

                char c = lines.get(y).charAt(x);
                switch (c) {
                    case '^':
                        carts.add(new Cart(x, y, MoveDirection.UP));
                        map[y][x] = '|';
                        break;

                    case '>':
                        carts.add(new Cart(x, y, MoveDirection.RIGHT));
                        map[y][x] = '-';
                        break;

                    case 'v':
                        carts.add(new Cart(x, y, MoveDirection.DOWN));
                        map[y][x] = '|';
                        break;

                    case '<':
                        carts.add(new Cart(x, y, MoveDirection.LEFT));
                        map[y][x] = '-';
                        break;

                    default:
                        map[y][x] = c;
                }
            }
        }
    }

    public void part1(){
        makeMapAndCarts();

        while (true){
            for (int i = 0; i < carts.size(); i++) {
                Cart currentCar = carts.get(i);
                MoveDirection moveDirection = getMoveDirection(currentCar);
                currentCar.setMoveDirection(moveDirection);
                currentCar.moveCart();
                if(hasCartCrashed(currentCar)){
                    System.out.println(currentCar.getX() + "," + currentCar.getY());
                    return;
                }
            }
        }
    }

    public void part2(){
        makeMapAndCarts();

        while (true){
            for (int i = 0; i < carts.size(); i++) {
                Cart currentCar = carts.get(i);
                MoveDirection moveDirection = getMoveDirection(currentCar);
                currentCar.setMoveDirection(moveDirection);
                currentCar.moveCart();
                Cart toRemove = getCrashedCart(currentCar);

                //carefully remove both inside loop:
                if(toRemove != null){
                    int toRemoveIndex = getIndexOfCart(toRemove, carts);

                    // remove if second car index is smaller:
                    if(toRemoveIndex < i){
                        carts.remove(i);
                        i--;
                        carts.remove(toRemoveIndex);
                        i--;

                    // remove if second car index is higher:
                    } else {
                        carts.remove(i);
                        i--;
                        carts.remove(toRemoveIndex - 1);
                    }
                }
            }
            if(carts.size() == 1){
                System.out.println(carts.getFirst().getX() + "," + carts.getFirst().getY());
                return;
            }
        }
    }

    private int getIndexOfCart(Cart c, List<Cart> a){
        for (int i = 0; i < a.size(); i++) {
            if(c == a.get(i)){
                return i;
            }
        }
        return -1;
    }

    private MoveDirection getMoveDirection(Cart cart){
        char c = map[cart.getY()][cart.getX()];

        switch (c){
            case '|':
                if(cart.getMoveDirection().equals(MoveDirection.UP)) return MoveDirection.UP;
                if(cart.getMoveDirection().equals(MoveDirection.DOWN)) return MoveDirection.DOWN;
                break;
            case '-':
                if(cart.getMoveDirection().equals(MoveDirection.RIGHT)) return MoveDirection.RIGHT;
                if(cart.getMoveDirection().equals(MoveDirection.LEFT)) return MoveDirection.LEFT;
                break;
            case '\\': // means equals one slash '\'
                if(cart.getMoveDirection().equals(MoveDirection.UP)) return MoveDirection.LEFT;
                if(cart.getMoveDirection().equals(MoveDirection.DOWN)) return MoveDirection.RIGHT;
                if(cart.getMoveDirection().equals(MoveDirection.LEFT)) return MoveDirection.UP;
                if(cart.getMoveDirection().equals(MoveDirection.RIGHT)) return MoveDirection.DOWN;
                break;
            case '/':
                if(cart.getMoveDirection().equals(MoveDirection.UP)) return MoveDirection.RIGHT;
                if(cart.getMoveDirection().equals(MoveDirection.RIGHT)) return MoveDirection.UP;
                if(cart.getMoveDirection().equals(MoveDirection.DOWN)) return MoveDirection.LEFT;
                if(cart.getMoveDirection().equals(MoveDirection.LEFT)) return MoveDirection.DOWN;
                break;
            case '+':
                TurnDirection t = cart.getTurnDirection(); // calling this updates last turn on cart
                MoveDirection m = cart.getMoveDirection();
                MoveDirection newMoveDirection = getMoveDirectionFromTurning(m, t);
                return newMoveDirection;
        }

        throw new IllegalStateException("Cant get new direction -> grid: " + c + ", current direction: " + cart.getMoveDirection());
    }

    // helper method:
    static MoveDirection getMoveDirectionFromTurning(MoveDirection moveDirection, TurnDirection turnDirection){
        switch (moveDirection){
            case MoveDirection.UP:
                if(turnDirection.equals(TurnDirection.STRAIGHT)) return MoveDirection.UP;
                if(turnDirection.equals(TurnDirection.RIGHT)) return MoveDirection.RIGHT;
                if(turnDirection.equals(TurnDirection.LEFT)) return MoveDirection.LEFT;
                break;

            case MoveDirection.RIGHT:
                if(turnDirection.equals(TurnDirection.STRAIGHT)) return MoveDirection.RIGHT;
                if(turnDirection.equals(TurnDirection.RIGHT)) return MoveDirection.DOWN;
                if(turnDirection.equals(TurnDirection.LEFT)) return MoveDirection.UP;
                break;

            case MoveDirection.DOWN:
                if(turnDirection.equals(TurnDirection.STRAIGHT)) return MoveDirection.DOWN;
                if(turnDirection.equals(TurnDirection.RIGHT)) return MoveDirection.LEFT;
                if(turnDirection.equals(TurnDirection.LEFT)) return MoveDirection.RIGHT;
                break;

            case MoveDirection.LEFT:
                if(turnDirection.equals(TurnDirection.STRAIGHT)) return MoveDirection.LEFT;
                if(turnDirection.equals(TurnDirection.RIGHT)) return MoveDirection.UP;
                if(turnDirection.equals(TurnDirection.LEFT)) return MoveDirection.DOWN;
                break;
        }

        throw new IllegalStateException("Helper function detected invalid input directions. Move: " + moveDirection + " ,turn: " + turnDirection);
    }

    private boolean hasCartCrashed(Cart c){
        for (int j = 0; j < carts.size(); j++) {
            Cart other = carts.get(j);
            if(c == other){
                continue;
            }
            if(c.getX() == other.getX() && c.getY() == other.getY()){
                return true;
            }
        }
        return false;
    }

    private static int getMaxLength(List<String> lines){
        int max = 0;
        for (int i = 0; i < lines.size(); i++) {
            max = Math.max(max, lines.get(i).length());
        }
        return max;
    }

    Cart getCrashedCart(Cart c){
        for (int j = 0; j < carts.size(); j++) {
            Cart other = carts.get(j);
            if(c == other){
                continue;
            }
            if(c.getX() == other.getX() && c.getY() == other.getY()){
                return other;
            }
        }
        return null;
    }
}
