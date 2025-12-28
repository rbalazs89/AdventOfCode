package year2018.day7;

import java.util.ArrayList;

class Graph {
    private ArrayList<Step> steps = new ArrayList<>();

    Step getStepById(char c){
        for (Step s : steps) {
            if(s.getId() == c) return s;
        }
        throw new IllegalStateException("Step with id: " + c + " not found.");
    }

    void addStepToGraph(Step step){
        for(Step s : steps){
            if(s.getId() == step.getId()) return;
        }
        steps.add(step);
    }

    void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    ArrayList<Step> getSteps(){
        return steps;
    }
}
