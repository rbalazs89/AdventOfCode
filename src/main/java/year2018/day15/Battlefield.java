package year2018.day15;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// note: map edges are always walls, so map out of bound issues don't need to be considered for any calculations
class Battlefield {
    List<Fighter> fighters;
    private final char[][] walls;
    char[][] map; // map[y][x]; only mutated from this class

    Battlefield(List<String> input, int elfDamage){
        walls = initializeMap(input);
        fighters = initializeFighters(input, elfDamage);
        orderFighters();
        updateMap();
    }

    private List<Fighter> initializeFighters(List<String> input, int elfDamage){
        List<Fighter> f = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.getFirst().length(); x++) {
                if(input.get(y).charAt(x) == 'E'){
                    f.add(new Elf(y,x, elfDamage));
                }

                if(input.get(y).charAt(x) == 'G'){
                    f.add(new Goblin(y,x));
                }
            }
        }
        return f;
    }

    private char[][] initializeMap(List<String> input){
        char[][] w = new char[input.size()][input.getFirst().length()];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if(c == 'E' || c == 'G'){
                    w[y][x] = '.';
                } else {
                    w[y][x] = c;
                }
            }
        }
        return w;
    }

    // maximum one dead per turn
    int removeDeadFighter() {
        for (int i = 0; i < fighters.size(); i++) {
            if(fighters.get(i).isDead()){
                fighters.remove(i);
                return i;
            }
        }
        return -1;
    }

    void updateMap(){
        deepCopy(); // copies clean map without fighters
        for (int i = 0; i < fighters.size(); i++) {
            Fighter c = fighters.get(i);
            map[c.getY()][c.getX()] = c.getSymbol();
        }
    }

    // copies walls when I want to update map
    private void deepCopy(){
        map = new char[walls.length][walls[0].length];
        for (int y = 0; y < walls.length; y++) {
            System.arraycopy(walls[y], 0, map[y], 0, walls[y].length);
        }
    }

    boolean isBattleOver(){
        int elves = 0;
        int goblins = 0;
        for (int i = 0; i < fighters.size(); i++) {
            if(fighters.get(i) instanceof Goblin){
                goblins ++;
            } else {
                elves ++;
            }
        }
        return elves == 0 || goblins == 0;
    }

    int getAllHitPoints(){
        int sum = 0;
        for (int i = 0; i < fighters.size(); i++) {
            sum += fighters.get(i).getHitPoints();
        }
        return sum;
    }

    void orderFighters() {
        fighters.sort(new Comparator<Fighter>() {
            @Override
            public int compare(Fighter o1, Fighter o2) {
                // y value compared
                if (o1.getY() != o2.getY()) {
                    return Integer.compare(o1.getY(), o2.getY());
                }
                // ties broken with x value
                return Integer.compare(o1.getX(), o2.getX());
            }
        });
    }

    /**
     * DEBUG METHOD
     */
    void drawBattlefield(){
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }


}
