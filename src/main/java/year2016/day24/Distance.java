package year2016.day24;

class Distance {

    private final Passage passage1;
    private final Passage passage2;
    private final int distanceInSteps;


    Distance(Passage passage1, Passage passage2, int distance){
        this.passage1 = passage1;
        this.passage2 = passage2;
        this.distanceInSteps = distance;
    }

    Passage getPassage1() {
        return passage1;
    }

    Passage getPassage2() {
        return passage2;
    }


    int getDistanceInSteps() {
        return distanceInSteps;
    }

}
