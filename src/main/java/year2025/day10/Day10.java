package year2025.day10;

import main.ReadLines;

import java.util.*;

public class Day10 {

    private List<String> fileLines;
    private final ArrayList<Machine> machines = new ArrayList<>();
    private long tempSolutionSum = Long.MAX_VALUE;
    private int finalSolution = 0;

    //part 2:
    private final ArrayList<Machine> solutionsQueue = new ArrayList<>();
    private final ArrayList<Machine> temporaryQueue = new ArrayList<>();
    private final ArrayList<Machine> fullSolved = new ArrayList<>();
    private int part2cumulative = 0;

    private final ReadLines reader = new ReadLines(2025, 10, 2);
    private void readData(){
        // READ INPUT
        fileLines = reader.readFile();
    }

    private void processFile(){
        for (int i = 0; i < fileLines.size(); i++) {
            // get lights:
            String line = fileLines.get(i);
            Machine m = new Machine();
            m.index = i;
            int part1 = line.indexOf("]");
            String temp = line.substring(1, part1);
            m.lights = new int[temp.length()];
            for (int j = 0; j < temp.length(); j++) {
                if(temp.charAt(j) == '.'){
                    m.lights[j] = 0;
                } else {
                    m.lights[j] = 1;
                }
            }
            m.currentLights = new int[temp.length()];
            Arrays.fill(m.currentLights, 0);


            // split rest of the line
            line = line.substring(part1 + 2);
            String[] parts = line.split(" ");

            // get joltage:
            String joltageString = parts[parts.length - 1];
            joltageString = joltageString.substring(1, joltageString.length() - 1);
            String[] joltageArrayString = joltageString.split(",");
            m.joltage = new int[joltageArrayString.length];
            m.remainingJoltage = new int[m.joltage.length]; // for part 2
            for (int j = 0; j < joltageArrayString.length; j++) {
                m.joltage[j] = Integer.parseInt(joltageArrayString[j]);
                m.remainingJoltage[j] = m.joltage[j]; // for part 2
            }

            // buttons:
            for (int j = 0; j < parts.length - 1; j++) {
                parts[j] = parts[j].substring(1, parts[j].length() - 1);
                String[] parts2 = parts[j].split(",");
                Button b = new Button();
                int[] number = new int[parts2.length];
                for (int k = 0; k < parts2.length; k++) {
                    number[k] = Integer.parseInt(parts2[k]);
                }
                b.numbers = number;
                m.buttons.add(b);
            }

            machines.add(m);
        }
    }

    public void part1(){
        clearData();

        for (int i = 0; i < machines.size(); i++) {
            Machine currentM = machines.get(i);
            tempSolutionSum = Integer.MAX_VALUE;

            generateCombinations(currentM, 0);
            finalSolution += (int) tempSolutionSum;
        }
        System.out.println(finalSolution);
    }

    private void generateCombinations(Machine m, int nthButtonPressed){
        if (nthButtonPressed == m.buttons.size()) {
            return;
        }
        /// ////////////////////////
        // 1 branching path one -> ignore current button and go next
        /// ////////////////////////
        generateCombinations(m, nthButtonPressed + 1);

        /// //////////////////
        // 2 branching path two -> press the current button and see if we got a solution -> if not, go next
        /// //////////////////
        // these two branches should cover all combinations
        // press current button
        pressButton(m, nthButtonPressed);

        // check if it's a solution
        checkIfSolution(m);
        generateCombinations(m, nthButtonPressed + 1);

        // unpress to restore original state
        unPressButton(m, nthButtonPressed);
    }

    private void checkIfSolution(Machine m){
        int size = m.currentLights.length;
        int points = 0;
        for (int i = 0; i < size; i++) {
            if(m.currentLights[i] == m.lights[i]){
                points ++;
            }
        }
        if(points == m.lights.length){
            int currentSum = 0;
            for (int i = 0; i < m.buttons.size(); i++) {
                currentSum += m.buttons.get(i).pressed;
            }
            if(currentSum < tempSolutionSum){
                tempSolutionSum = currentSum;
            }
        }
    }

    private void pressButton(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed ++;
        toggleLights(m, button.numbers);
    }

    private void unPressButton(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed--;
        toggleLights(m, button.numbers);
    }

    private void toggleLights(Machine m, int[] numbers) {
        for (int light : numbers) {
            m.currentLights[light] = 1 - m.currentLights[light];  // toggle 0 or 1
        }
    }

    public void part2() {
        // read input txt and fill machines arraylist
        clearData();

        for (int q = 0; q < machines.size(); q++) {

            // initiate first step for a machine:
            Machine myMachine = machines.get(q);

            myMachine.lights = new int[myMachine.joltage.length];
            for (int i = 0; i < myMachine.lights.length; i++) {
                myMachine.lights[i] = myMachine.remainingJoltage[i] % 2;
            }

            getValidCombinations(myMachine, 0); // fills solutionsqueue from recursive

            // go until there are solutions
            while(!solutionsQueue.isEmpty()){
                fillQueue();
                for (int i = 0; i < temporaryQueue.size(); i++) {
                    Machine m = temporaryQueue.get(i);
                    getValidCombinations(m, 0); // fills solutions queue
                }
                temporaryQueue.clear();
            }
            temporaryQueue.clear();
            part2cumulative += getBestFromSolvedList(q); // fullSolved arraylist
            fullSolved.clear();
        }
        System.out.println(part2cumulative);

    }

    private void fillQueue(){
        Set<String> set = new HashSet<>();
        for (int i = 0; i < solutionsQueue.size(); i++) {
            Machine m = solutionsQueue.get(i);
            StringBuilder sb = new StringBuilder();
            for (int[] row : m.storedButtonPresses) {
                sb.append(Arrays.toString(row)).append(";");
            }
            String currentString = sb.toString();
            if(set.add(currentString)){
                temporaryQueue.add(solutionsQueue.get(i));
            }
        }
        solutionsQueue.clear();
    }

    private void getValidCombinations(Machine m, int nthButtonPressed){
        int buttonsNumber = m.buttons.size();

        if (nthButtonPressed == buttonsNumber) {
            checkIfSolution2(m);
            return;
        }

        getValidCombinations(m, nthButtonPressed + 1);
        pressButton2(m, nthButtonPressed);
        getValidCombinations(m, nthButtonPressed + 1);
        unPressButton2(m, nthButtonPressed);
    }

    private void checkIfSolution2(Machine m){
        int size = m.currentLights.length;
        int points = 0;
        for (int i = 0; i < size; i++) {
            if(m.currentLights[i] == m.lights[i]){
                points ++;
            }
        }
        if(points == m.lights.length){
            // solution found, save solution and copy new instance to solutionsQueue()
            copyMachineForNextStep(m);
        }
    }

    private void pressButton2(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed ++;
        toggleLights2(m, button.numbers);
    }

    private void unPressButton2(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed--;
        toggleLights2(m, button.numbers);
    }

    private void toggleLights2(Machine m, int[] numbers) {
        for (int light : numbers) {
            m.currentLights[light] = 1 - m.currentLights[light];  // toggle 0 or 1
        }
    }

    private void copyMachineForNextStep(Machine m) {
        // new instance:
        Machine copied = new Machine();

        copied.storedButtonPresses = new ArrayList<>();
        for (int[] arr : m.storedButtonPresses) {
            copied.storedButtonPresses.add(arr.clone());
        }

        // store button presses
        int[] thisStepPresses = new int[m.buttons.size()];
        for (int i = 0; i < thisStepPresses.length; i++) {
            thisStepPresses[i] = m.buttons.get(i).pressed;
        }
        copied.storedButtonPresses.add(thisStepPresses);

        // copy button statics
        for (Button b : m.buttons) {
            Button newB = new Button();
            newB.pressed = 0; // reset, not copy, preserve copy in arraylist
            newB.numbers = b.numbers.clone();
            copied.buttons.add(newB);
        }

        // clone joltage ? this only used for first step do i need to always pass this
        copied.joltage = m.joltage.clone();

        // reset lights
        copied.currentLights = new int[m.currentLights.length]; // reset not copy

        // // CHECK IF SOLVED:
        // remaining joltage:
        copied.remainingJoltage = m.remainingJoltage.clone();
        for (int buttonIndex = 0; buttonIndex < thisStepPresses.length; buttonIndex++) {
            if (thisStepPresses[buttonIndex] == 1) {
                Button b = m.buttons.get(buttonIndex); // use original, since structure same
                for (int joltageIndex : b.numbers) {
                    copied.remainingJoltage[joltageIndex] -= 1;
                }
            }
        }

        // divide all
        for (int i = 0; i < copied.remainingJoltage.length; i++) {
            copied.remainingJoltage[i] = copied.remainingJoltage[i] / 2;
        }

        // prune if negative
        for (int i = 0; i < copied.remainingJoltage.length; i++) {
            if (copied.remainingJoltage[i] < 0) {
                //System.out.println("negative joltage - can happen at last iteration");
                return;
            }
        }

        // check if solved
        boolean fullSolution = true;
        for (int i = 0; i < copied.remainingJoltage.length; i++) {
            if(copied.remainingJoltage[i] != 0){
                fullSolution = false;
                break;
            }
        }
        copied.index = m.index;

        // exit if solved
        if(fullSolution){
            fullSolved.add(copied);
            return;
        }
        calculateLights(copied);
        solutionsQueue.add(copied);
    }

    private void calculateLights(Machine ma){
        ma.lights = new int[ma.joltage.length];
        for (int i = 0; i < ma.lights.length; i++) {
            ma.lights[i] = ma.remainingJoltage[i] % 2;
        }
    }

    private int getBestFromSolvedList(int q){
        int minPresses = Integer.MAX_VALUE;
        for (int i = 0; i < fullSolved.size(); i++) {
            int currentPresses = 0;
            Machine m = fullSolved.get(i);
            for (int j = 0; j < m.storedButtonPresses.size(); j++) {
                currentPresses += arraySum(m.storedButtonPresses.get(j)) * (int)(Math.pow(2, j));
            }
            minPresses = Math.min(minPresses, currentPresses);
        }
        fullSolved.clear();
        System.out.println(q + " " + minPresses);
        return minPresses;
    }

    public void clearData(){
        tempSolutionSum = Integer.MAX_VALUE;
        finalSolution = 0;
        machines.clear();
        readData();
        processFile();
    }

    public int arraySum(int[] a){
        int sum = 0;
        for (int j : a) {
            sum += j;
        }
        return sum;
    }
}