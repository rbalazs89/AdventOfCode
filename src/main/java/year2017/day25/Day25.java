package year2017.day25;

import main.ReadLines;

import java.util.HashMap;
import java.util.Map;

public class Day25 {
    /**
     * no input needed, rules are put in manually
     */

    private static final int STEPS = 12683008;
    private final Map<Integer, Integer> infiniteTape = new HashMap<>();
    private int currentPosition = 0;
    private char currentState = 'A';

    public void part1() {

        for (int i = 0; i < STEPS; i++) {
            doOneStep();
        }

        System.out.println(getDiagnosticChecksum());
    }

    public void part2(){
        // nothing to do here, no part 2
    }

    private int getDiagnosticChecksum(){
        int counter = 0;
        for (Map.Entry<Integer, Integer> my : infiniteTape.entrySet()){
            if(my.getValue() == 1){
                counter ++;
            }
        }
        return counter;
    }

    private void doOneStep(){
        infiniteTape.putIfAbsent(currentPosition, 0);
        int currentValue = infiniteTape.get(currentPosition);
        switch (currentState) {
            case 'A': {
                if (currentValue == 0) {
                    infiniteTape.put(currentPosition, 1);
                    currentPosition++;
                    currentState = 'B';
                } else {
                    infiniteTape.put(currentPosition, 0);
                    currentPosition--;
                    currentState = 'B';
                }
                break;
            }

            case 'B': {
                if (currentValue == 0) {
                    infiniteTape.put(currentPosition, 1);
                    currentPosition--;
                    currentState = 'C';
                } else {
                    infiniteTape.put(currentPosition, 0);
                    currentPosition++;
                    currentState = 'E';
                }
                break;
            }

            case 'C': {
                if (currentValue == 0) {
                    infiniteTape.put(currentPosition, 1);
                    currentPosition++;
                    currentState = 'E';
                } else {
                    infiniteTape.put(currentPosition, 0);
                    currentPosition--;
                    currentState = 'D';
                }
                break;
            }

            case 'D': {
                if (currentValue == 0) {
                    infiniteTape.put(currentPosition, 1);
                    currentPosition--;
                    currentState = 'A';
                } else {
                    infiniteTape.put(currentPosition, 1);
                    currentPosition--;
                    currentState = 'A';
                }
                break;
            }

            case 'E': {
                if (currentValue == 0) {
                    infiniteTape.put(currentPosition, 0);
                    currentPosition++;
                    currentState = 'A';
                } else {
                    infiniteTape.put(currentPosition, 0);
                    currentPosition++;
                    currentState = 'F';
                }
                break;
            }

            case 'F': {
                if (currentValue == 0) {
                    infiniteTape.put(currentPosition, 1);
                    currentPosition++;
                    currentState = 'E';
                } else {
                    infiniteTape.put(currentPosition, 1);
                    currentPosition++;
                    currentState = 'A';
                }
                break;
            }
        }
    }
}
