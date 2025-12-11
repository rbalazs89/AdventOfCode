package year2025.day10;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day10 {
    // too low 2551
    // too low 10793
    List<String> fileLines;
    int inputFileIndex = 2;
    ArrayList<Machine> machines = new ArrayList<>();
    private static final int MAX_J = 250;

    //optimization variables:
    int buttonsNumber;
    int[] tempSolution;
    long tempSolutionSum = Long.MAX_VALUE;
    int finalSolution = 0;
    int lightSize = 0;

    public void readData() {
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }

    public void processFile(){
        for (int i = 0; i < fileLines.size(); i++) {
            // get lights:
            String line = fileLines.get(i);
            Machine m = new Machine();
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
            for (int j = 0; j < joltageArrayString.length; j++) {
                m.joltage[j] = Integer.parseInt(joltageArrayString[j]);
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
        readData();
        processFile();

        for (int i = 0; i < machines.size(); i++) {
            Machine currentM = machines.get(i);
            buttonsNumber = machines.get(i).buttons.size();
            tempSolution = new int[buttonsNumber];
            tempSolutionSum = Integer.MAX_VALUE;
            lightSize = currentM.lights.length;

            generateCombinations(currentM, 0);
            finalSolution += tempSolutionSum;
        }

        for (int i = 0; i < tempSolution.length; i++) {
            System.out.print(tempSolution[i] + " ");
        }
        System.out.println();
        System.out.println(finalSolution);
    }

    public void generateCombinations(Machine m, int nthButtonPressed){
        if (nthButtonPressed == buttonsNumber) {
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

        // check if its a solution
        checkIfSolution(m);
        generateCombinations(m, nthButtonPressed + 1);

        // unpress to restore original state
        unPressButton(m, nthButtonPressed);
    }

    public void checkIfSolution(Machine m){
        int size = m.currentLights.length;
        int points = 0;
        for (int i = 0; i < size; i++) {
            if(m.currentLights[i] == m.lights[i]){
                points ++;
            }
        }
        if(points == lightSize){
            int currentSum = 0;
            for (int i = 0; i < buttonsNumber; i++) {
                currentSum += m.buttons.get(i).pressed;
            }
            if(currentSum < tempSolutionSum){
                tempSolutionSum = currentSum;
                for (int i = 0; i < buttonsNumber; i++) {
                    tempSolution[i] = m.buttons.get(i).pressed;
                }
            }
        }
    }

    public void pressButton(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed ++;
        toggleLights(m, button.numbers);
    }

    public void unPressButton(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed--;
        toggleLights(m, button.numbers);
    }

    private void toggleLights(Machine m, int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int light = numbers[i];
            m.currentLights[light] = 1 - m.currentLights[light];  // toggle 0 or 1
        }
    }

    public void part2(){
        clearData();

        for (int i = 0; i < machines.size(); i++) {
            Machine currentM = machines.get(i);
            buttonsNumber = machines.get(i).buttons.size();
            tempSolution = new int[buttonsNumber];
            tempSolutionSum = Integer.MAX_VALUE;
            lightSize = currentM.joltage.length;

            generateCombinations2(currentM, 0);
            finalSolution += tempSolutionSum;
            if(tempSolutionSum == Integer.MAX_VALUE){
                System.out.println("not solved: " + i);
            }
        }

        for (int i = 0; i < tempSolution.length; i++) {
            System.out.print(tempSolution[i] + " ");
        }
        System.out.println();
        System.out.println(finalSolution);
    }

    public void generateCombinations2(Machine m, int nthButtonPressed){
        if (nthButtonPressed == buttonsNumber * 5) {
            return;
        }
        int index = nthButtonPressed % buttonsNumber;
        /// ////////////////////////
        // 1 branching path one -> ignore current button and go next
        /// ////////////////////////
        generateCombinations2(m, nthButtonPressed + 1);

        // delete branch if already overshot any light
        if (isOvershot(m)) {
            return;
        }

        /// //////////////////
        // 2 branching path two -> press the current button and see if we got a solution -> if not, go next
        /// //////////////////
        // these two branches should cover all combinations
        // press current button
        pressButton2(m, index);

        // check if its a solution
        checkIfSolution2(m);
        generateCombinations2(m, nthButtonPressed + 1);

        // unpress to restore original state for recursive function to work
        unPressButton2(m, index);
    }

    public void pressButton2(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed ++;
        for (int i = 0; i < button.numbers.length; i++) {
            int light = button.numbers[i];
            m.currentLights[light] ++;
        }
    }

    public void unPressButton2(Machine m, int index){
        Button button = m.buttons.get(index);
        button.pressed--;
        for (int i = 0; i < button.numbers.length; i++) {
            int light = button.numbers[i];
            m.currentLights[light] --;
        }
    }

    public boolean isOvershot(Machine m) {
        for (int i = 0; i < m.currentLights.length; i++) {
            if (m.currentLights[i] > m.joltage[i]) {
                return true;
            }
        }
        return false;
    }

    public void checkIfSolution2(Machine m){
        int size = m.currentLights.length;
        int points = 0;
        for (int i = 0; i < size; i++) {
            if(m.currentLights[i] == m.joltage[i]){
                points ++;
            }
        }
        if(points == lightSize){
            int currentSum = 0;
            for (int i = 0; i < buttonsNumber; i++) {
                currentSum += m.buttons.get(i).pressed;
            }
            if(currentSum < tempSolutionSum){
                tempSolutionSum = currentSum;
                for (int i = 0; i < buttonsNumber; i++) {
                    tempSolution[i] = m.buttons.get(i).pressed;
                }
            }
        }
    }

    public void clearData(){
        tempSolutionSum = Integer.MAX_VALUE;
        finalSolution = 0;
        lightSize = 0;
        machines.clear();
        readData();
        processFile();
    }
}
