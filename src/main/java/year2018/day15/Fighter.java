package year2018.day15;

import java.util.*;

abstract class Fighter{
    private int x;
    private int y;
    private int hitPoints = 200;
    private int damage = 3;
    private boolean dead = false;
    private final char symbol;

    Fighter (int y, int x, char symbol){
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    void loseHitPoints(int damageValue){
        hitPoints = hitPoints - damageValue;
        if(hitPoints <= 0){
            dead = true;
        }
    }

    void setDamage(int value){
        damage = value;
    }

    boolean canMove(Battlefield bf){
        if(bf.map[y + 1][x] == '.') return true;
        if(bf.map[y - 1][x] == '.') return true;
        if(bf.map[y][x + 1] == '.') return true;
        if(bf.map[y][x - 1] == '.') return true;
        return false;
    }

    void attack(Fighter other){
        other.loseHitPoints(this.damage);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getHitPoints(){
        return hitPoints;
    }

    char getSymbol() {
        return symbol;
    }

    boolean isDead() {
        return dead;
    }

    void doOneTurn(Battlefield bf){
        // attack if enemy in range
        if(areEnemiesInAttackRange(bf)){
            Fighter f = chooseEnemyInRange(bf);
            attack(f);
            return;
        }

        // no enemies in range and cannot move
        if(!canMove(bf)){
            return;
        }

        // moves one tile max
        searchForEnemiesAndMove(bf);

        if(areEnemiesInAttackRange(bf)){
            Fighter f = chooseEnemyInRange(bf);
            attack(f);
        }
    }
    private void searchForEnemiesAndMove(Battlefield bf) {

        HashSet<String> visited = new HashSet<>();
        Queue<Step> q = new ArrayDeque<>();

        Step start = new Step(y, x);
        visited.add(y + "," + x);
        q.add(start);

        List<Step> winningSteps = new ArrayList<>();
        boolean foundDistance = false;

        while (!q.isEmpty() && !foundDistance) {
            int layerSize = q.size(); // IMPORTANT: process one distance layer at a time

            for (int i = 0; i < layerSize; i++) {
                Step c = q.poll();

                // If this square is adjacent to an enemy, it's a valid target
                if (areEnemiesInAttackRange(bf, c.x, c.y)) {
                    winningSteps.add(c);
                    foundDistance = true;
                    continue;
                }

                // Explore neighbors in reading order up, left, right, down

                // up
                if (bf.map[c.y - 1][c.x] == '.' &&
                        visited.add((c.y - 1) + "," + c.x)) {
                    Step s = new Step(c.y - 1, c.x);
                    s.parent = c;
                    q.add(s);
                }

                // left
                if (bf.map[c.y][c.x - 1] == '.' &&
                        visited.add(c.y + "," + (c.x - 1))) {
                    Step s = new Step(c.y, c.x - 1);
                    s.parent = c;
                    q.add(s);
                }

                // right
                if (bf.map[c.y][c.x + 1] == '.' &&
                        visited.add(c.y + "," + (c.x + 1))) {
                    Step s = new Step(c.y, c.x + 1);
                    s.parent = c;
                    q.add(s);
                }

                // down
                if (bf.map[c.y + 1][c.x] == '.' &&
                        visited.add((c.y + 1) + "," + c.x)) {
                    Step s = new Step(c.y + 1, c.x);
                    s.parent = c;
                    q.add(s);
                }
            }
        }

        if (winningSteps.isEmpty()) {
            return;
        }

        // Choose target square by reading order
        winningSteps.sort((a, b) -> {
            if (a.y != b.y) return Integer.compare(a.y, b.y);
            return Integer.compare(a.x, b.x);
        });

        Step winnerStep = winningSteps.getFirst();

        // walk back to find first move
        while (winnerStep.parent.parent != null) {
            winnerStep = winnerStep.parent;
        }

        // Perform move
        x = winnerStep.x;
        y = winnerStep.y;
    }

    abstract boolean areEnemiesInAttackRange(Battlefield bf);
    abstract boolean areEnemiesInAttackRange(Battlefield bf, int x, int y);
    abstract Fighter chooseEnemyInRange(Battlefield bf);

    // only used to determine next step
    private static class Step {
        Step parent;
        int x;
        int y;

        Step(int y, int x){
            this.x = x;
            this.y = y;
        }
    }
}