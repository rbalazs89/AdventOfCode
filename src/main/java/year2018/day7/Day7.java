package year2018.day7;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    private final ReadLines reader = new ReadLines(2018, 7, 2);
    private final static Pattern PATTERN_STEP = Pattern.compile("[Ss]tep (.)");
    private static final int WORKERS = 5; // specified by task description

    private Graph prepareGraph(){
        List<String> lines = reader.readFile();
        Graph graph = new Graph();
        graph.setSteps(new ArrayList<>());

        // initialize all vertices
        for (String line : lines){
            Matcher m = PATTERN_STEP.matcher(line);
            while(m.find()){
                graph.addStepToGraph(new Step(m.group(1).charAt(0)));
            }
        }

        // add incoming and outgoing for each vertex
        // example line: Step F must be finished before step E can begin.
        for (String line : lines){
            Matcher m = PATTERN_STEP.matcher(line);

            ArrayList<Character> characters = new ArrayList<>();
            while(m.find()){
                characters.add(m.group(1).charAt(0));
            }
            Step first = graph.getStepById(characters.getFirst());
            Step second = graph.getStepById(characters.get(1));

            first.addOutgoing(second);
            second.addIncoming(first);
        }
        return graph;
    }

    public void part1(){
        Graph graph = prepareGraph();
        Set<Character> visited = new HashSet<>(); // stores visited nodes
        StringBuilder sb = new StringBuilder();

        while (sb.length() < graph.getSteps().size()) {
            ArrayList<Step> nextSteps = getNextSteps(graph, visited);
            if (nextSteps.isEmpty()) {
                System.out.println("Cycle detected in this graph.");
                break;
            }
            Collections.sort(nextSteps);
            Step next = nextSteps.getFirst();
            sb.append(next.getId());
            visited.add(next.getId());
        }
        System.out.println(sb);
    }

    public void part2() {
        Graph graph = prepareGraph();

        for (Step step : graph.getSteps()) {
            step.prepareWorkTime(); // 61 + (id - 'A')
        }

        PriorityQueue<Step> ready = new PriorityQueue<>(); // alphabetical order
        Map<Step, Integer> inProgress = new HashMap<>(); // step, time already spent on it
        Set<Step> completed = new HashSet<>();

        int workersAvailable = WORKERS;
        int time = 0;

        // Initialize: add all steps with no prerequisites
        for (Step step : graph.getSteps()) {
            if (step.getIncoming().isEmpty()) {
                ready.offer(step);
            }
        }

        while (completed.size() < graph.getSteps().size()) {
            // Assign as many workers as possible to ready steps
            while (workersAvailable > 0 && !ready.isEmpty()) {
                Step next = ready.poll();
                inProgress.put(next, 0);  // start working, 0 seconds done yet
                workersAvailable--;
            }

            if (inProgress.isEmpty() && ready.isEmpty()) {
                // If nothing is progressing and nothing ready
                System.out.println("Graph travel halted");
                break;
            }

            // If no tasks in progress, but some are ready → we already assigned above
            if (inProgress.isEmpty()) {
                time++;
                continue;
            }

            // Advance time by 1 second: all in-progress tasks get +1 work
            time++;

            List<Step> justFinished = new ArrayList<>();
            for (Map.Entry<Step, Integer> entry : inProgress.entrySet()) {
                Step step = entry.getKey();
                int worked = entry.getValue() + 1;  // +1 second
                inProgress.put(step, worked);

                if (worked >= step.getWorkTime()) {
                    justFinished.add(step);
                }
            }

            // Complete finished tasks
            for (Step finished : justFinished) {
                inProgress.remove(finished);
                completed.add(finished);
                workersAvailable++;

                // Unlock dependent steps
                for (Step dependent : finished.getOutgoing()) {

                    // Check if all prerequisites are now complete
                    boolean allPrereqsDone = true;
                    for (Step prereq : dependent.getIncoming()) {
                        if (!completed.contains(prereq)) {
                            allPrereqsDone = false;
                            break;
                        }
                    }
                    if (allPrereqsDone && !completed.contains(dependent) && !inProgress.containsKey(dependent)) {
                        ready.offer(dependent);
                    }
                }
            }
        }
        System.out.println(time);
    }

    private ArrayList<Step> getNextSteps(Graph graph, Set<Character> visited){
        ArrayList<Step> readyToBegin = new ArrayList<>();
        for (int i = 0; i < graph.getSteps().size(); i++) {

            Step currentStep = graph.getSteps().get(i);
            if(visited.contains(currentStep.getId())){
                continue;
            }
            int visits = 0;
            for (int j = 0; j < currentStep.getIncoming().size(); j++) {
                if(visited.contains(currentStep.getIncoming().get(j).getId())){
                    visits ++;
                }
            }

            // add to next round if it has no incoming vertices, or all incoming vertices have been visited
            if(visits == currentStep.getIncoming().size()){
                readyToBegin.add(currentStep);
            }
        }
        return readyToBegin;
    }
}