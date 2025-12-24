package year2016.day24;

import java.util.ArrayList;

class Passage {
    private final char name;
    private final int x;
    private final int y;
    private final ArrayList<Distance> distances = new ArrayList<>();

    Passage(char name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    char getName() {
        return name;
    }

    void addDistance(Distance d){
        char c1 = d.getPassage1().getName();
        char c2 = d.getPassage2().getName();
        for (int i = 0; i < distances.size(); i++) {
            char compare1 = distances.get(i).getPassage1().getName();
            char compare2 = distances.get(i).getPassage2().getName();
            if((c1 == compare1 && c2 == compare2) ||(c1 == compare2 && c2 == compare1)){
                return; // distance already added
            }
        }
        distances.add(d);
    }

    Integer getDistanceFromPassage(Passage p){
        char current = name;
        char next = p.name;
        for (int i = 0; i < distances.size(); i++) {
            char c1 = distances.get(i).getPassage1().getName();
            char c2 = distances.get(i).getPassage2().getName();
            if(c1 == current && c2 == next || c1 == next && c2 == current){
                return distances.get(i).getDistanceInSteps();
            }
        }
        throw new IllegalStateException(
                "No route found between passages '" + current + "' and '" + next + "'"
        );
    }
}
