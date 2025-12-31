package year2018.day15;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Goblin extends Fighter{

    Goblin(int y, int x){
        super(y, x, 'G');
    }

    @Override
    boolean areEnemiesInAttackRange(Battlefield bf) {
        if(bf.map[getY() + 1][getX()] == 'E') return true;
        if(bf.map[getY() - 1][getX()] == 'E') return true;
        if(bf.map[getY()][getX() + 1] == 'E') return true;
        if(bf.map[getY()][getX() - 1] == 'E') return true;
        return false;
    }

    @Override
    boolean areEnemiesInAttackRange(Battlefield bf, int x, int y) {
        if(bf.map[y + 1][x] == 'E') return true;
        if(bf.map[y][x - 1] == 'E') return true;
        if(bf.map[y][x + 1] == 'E') return true;
        if(bf.map[y - 1][x] == 'E') return true;
        return false;
    }

    // method assumes that enemy is in range
    // reading order applied to the method below
    @Override
    Fighter chooseEnemyInRange(Battlefield bf) {
        List<Fighter> potentialVictims = new ArrayList<>();
        Fighter up = null;
        Fighter left = null;
        Fighter right = null;
        Fighter down = null;
        if(bf.map[getY() - 1][getX()] == 'E'){
            for (int i = 0; i < bf.fighters.size(); i++) {
                Fighter f = bf.fighters.get(i);
                if(f.getX() == getX() && getY() - 1 == f.getY()){
                    up = f;
                    potentialVictims.add(up);
                    break;
                }
            }
        }
        if(bf.map[getY()][getX() - 1] == 'E'){
            for (int i = 0; i < bf.fighters.size(); i++) {
                Fighter f = bf.fighters.get(i);
                if(f.getX() == getX() - 1 && getY() == f.getY()){
                    left = f;
                    potentialVictims.add(left);
                    break;
                }
            }
        }
        if(bf.map[getY()][getX() + 1] == 'E'){
            for (int i = 0; i < bf.fighters.size(); i++) {
                Fighter f = bf.fighters.get(i);
                if(f.getX() == getX() + 1 && getY() == f.getY()){
                    right = f;
                    potentialVictims.add(right);
                    break;
                }
            }
        }
        if(bf.map[getY() + 1][getX()] == 'E'){
            for (int i = 0; i < bf.fighters.size(); i++) {
                Fighter f = bf.fighters.get(i);
                if(f.getX() == getX() && getY() + 1 == f.getY()){
                    down = f;
                    potentialVictims.add(down);
                    break;
                }
            }
        }

        if(potentialVictims.size() == 1){
            return potentialVictims.getFirst();
        }

        potentialVictims.sort(Comparator.comparingInt(Fighter::getHitPoints));

        int minValue = potentialVictims.getFirst().getHitPoints();
        for (int i = 0; i < potentialVictims.size(); i++) {
            if(potentialVictims.get(i).getHitPoints() != minValue){
                potentialVictims.remove(i);
                i --;
            }
        }

        // keep order if there are more and all hps are the same:
        if(up != null && potentialVictims.contains(up)){return up;}
        if(left != null && potentialVictims.contains(left)){return left;}
        if(right != null && potentialVictims.contains(right)){return right;}
        if(down != null && potentialVictims.contains(down)){return down;}

        // debug:
        System.out.println("enemy not found in range, but one should be in range");
        return null;
    }
}
