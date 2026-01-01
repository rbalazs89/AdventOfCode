package year2024.day13;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day13 {
    private static final ArrayList<ClawSetup> setups = new ArrayList<>();
    private final ReadLines reader = new ReadLines(2024, 13, 2);

    private void prepare(){
        setups.clear();
        List<String> input = reader.readFile();
        for (int i = 0; i < input.size(); i += 4) {
            if (i + 2 >= input.size()) {
                System.err.println("Error" + i);
                break;
            }
            try {
                ClawSetup setup = new ClawSetup();
                setup.AX = parseCoordinate(input.get(i), "X");
                setup.AY = parseCoordinate(input.get(i), "Y");
                setup.BX = parseCoordinate(input.get(i + 1), "X");
                setup.BY = parseCoordinate(input.get(i + 1), "Y");
                setup.goalX = parseCoordinate(input.get(i + 2), "X");
                setup.goalY = parseCoordinate(input.get(i + 2), "Y");

                setups.add(setup);
            } catch (Exception e) {
                System.err.println("Error");
            }
        }
    }

    public void part1(){
        prepare();
        for (int a = 0; a < setups.size(); a++) {
            ClawSetup current = setups.get(a);
            long maxMultA = Math.max(current.goalX / current.AX + 1,  current.goalY / current.AY + 1);
            long maxMultB = Math.max(current.goalX / current.BX + 1,  current.goalY / current.BY + 1);
            ArrayList<long[]> possibleCombinations = new ArrayList<>();
            for (int i = 0; i < maxMultA; i++) {
                for (int j = 0; j < maxMultB; j++) {
                    if( i * current.AX + j * current.BX == current.goalX &&
                            i * current.AY + j * current.BY == current.goalY){
                        possibleCombinations.add(new long[]{i,j});
                    }
                }
            }
            if(possibleCombinations.isEmpty()){
                current.value = 0;
                continue;
            }

            // get the lowest value: A cost 3 tokens, B cost 1 tokens
            long minValue = Integer.MAX_VALUE;
            for (int i = 0; i < possibleCombinations.size(); i++) {
                if(possibleCombinations.get(i)[0] * 3 +  possibleCombinations.get(i)[1] < minValue){
                    minValue = possibleCombinations.get(i)[0] * 3L +  possibleCombinations.get(i)[1];
                }
            }
            current.value = minValue;
        }

        long result = 0;
        for (int i = 0; i < setups.size(); i++) {
            result += setups.get(i).value;
        }
        System.out.println(result);
    }

    public void part2(){
        long result = 0;

        for (int i = 0; i < setups.size(); i++) {

            ClawSetup current = setups.get(i);
            current.goalY = current.goalY + 10000000000000L;
            current.goalX = current.goalX + 10000000000000L;
            long numerator = current.goalY * current.BX - current.BY * current.goalX;
            long denominator = current.AY * current.BX - current.BY * current.AX;

            if (denominator == 0) {
                continue;
            }

            // check if results are valid round positive numbers
            if (numerator % denominator == 0) {
                long n = numerator / denominator;
                if (n > 0) {
                    long mNumerator = current.goalX - current.AX * n;
                    if (mNumerator % current.BX == 0) {
                        long m = mNumerator / current.BX;
                        if (m > 0) {
                            current.value2 = 3 * n + m;
                            result += current.value2;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    private static int parseCoordinate(String line, String coordinate) throws Exception {
        String[] parts = line.split(",");
        for (String part : parts) {
            if (part.contains(coordinate + "+") || part.contains(coordinate + "=")) {
                return Integer.parseInt(part.replaceAll("[^\\d-]", "").trim());
            }
        }
        throw new Exception("Missing coordinate");
    }

    private static class ClawSetup {
        private long AX;
        private long AY;
        private long BX;
        private long BY;
        private long goalX;
        private long goalY;
        private long value;
        private long value2 = 0;
    }
}
