package year2025.day10;

import java.util.ArrayList;

class Machine {
    ArrayList<Button> buttons = new ArrayList<>();
    int[] lights; // part 2 note toggle between 0 - 1, recalculate between each step, have to reach this
    int[] currentLights; // always starts from 0, toggle 0 - 1 each subStep
    int[] joltage; // stays same for part 2
    ArrayList<int[]> storedButtonPresses = new ArrayList<>(); // save here cumulative after each step
    int[] remainingJoltage; // counting backwards from final value, have to update after each step
    int index = 0;
}