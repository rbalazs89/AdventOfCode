package year2018.day12;

import main.ReadLines;

import java.util.*;

public class Day12 {
    private final ReadLines reader = new ReadLines(2018, 12, 2);
    private Map<String, Character> rules = new HashMap<>();
    private static final int PATTERN_SIZE = 5;
    private static final int PART1_GENERATIONS = 20;
    private static final long PART2_GENERATIONS = 50000000000L;

    private void initializeRules() {
        List<String> lines = reader.readFile();

        // initiate
        String initialLine = lines.getFirst();
        if (!initialLine.startsWith("initial state: ")) {
            throw new IllegalStateException("Invalid starting line.");
        }

        // skip initial state and empty line
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("=>");
            if (parts.length != 2) continue;

            String pattern = parts[0].trim();
            char result = parts[1].trim().charAt(0);

            rules.put(pattern, result);
        }
    }

    private Map<Integer, Character> getInitialState() {
        List<String> lines = reader.readFile();
        String line = lines.getFirst();
        line = line.substring("initial state: ".length()).trim();

        Map<Integer, Character> pots = new HashMap<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '#') {
                pots.put(i, '#'); // only store plants
            } else if (c != '.') {
                throw new IllegalStateException("Invalid character in input: " + c);
            }
        }
        return pots;
    }

    public void part1() {
        initializeRules();
        Map<Integer, Character> pots = getInitialState();

        for (long gen = 0; gen < PART1_GENERATIONS; gen++) {
            pots = nextGeneration(pots);
        }

        System.out.println(sumPlantPositions(pots));
    }

    public void part2() {

    }
    private int getHighestIndex(Map<Integer, Character> current){
        int max = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Character> c : current.entrySet()){
            max = Math.max(max, c.getKey());
        }
        return max;
    }

    private int getLowestIndex(Map<Integer, Character> current){
        int min = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Character> c : current.entrySet()){
            min = Math.min(min, c.getKey());
        }
        return min;
    }

    private Map<Integer, Character> nextGeneration(Map<Integer, Character> current) {

        int min = getLowestIndex(current);
        int max = getHighestIndex(current);

        // check from min-2 to max+2 to allow growing
        int start = min - 2;
        int end = max + 2;

        Map<Integer, Character> nextGeneration = new HashMap<>();

        for (int pos = start; pos <= end; pos++) {
            StringBuilder pattern = new StringBuilder();
            for (int j = -2; j <= 2; j++) {
                // get '.' if empty otherwise get value
                pattern.append(current.getOrDefault(pos + j, '.') == '#' ? '#' : '.');
            }

            Character result = rules.get(pattern.toString());
            if (result == '#') {
                nextGeneration.put(pos, '#');
            }
        }

        return nextGeneration;
    }

    private long sumPlantPositions(Map<Integer, Character> pots) {
        long sum = 0;
        for (Map.Entry<Integer, Character> entry : pots.entrySet()) {
            if (entry.getValue() == '#') {
                sum += entry.getKey();
            }
        }
        return sum;
    }
}