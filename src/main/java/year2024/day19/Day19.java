package year2024.day19;

import main.ReadLines;

import java.util.*;

public class Day19 {
    private static final ReadLines reader = new ReadLines(2024, 19, 2);
    private static String[] towels;
    private static final ArrayList<String> patterns = new ArrayList<>();

    public void part1(){
        prepare();
        int result = 0;

        for (int j = 0; j < patterns.size(); j++) {
            String pattern = patterns.get(j);
            Queue<Integer> queue = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            queue.add(0);

            boolean isPossible = false;

            while (!queue.isEmpty()) {
                int currentPosition = queue.poll();
                if (visited.contains(currentPosition)) {
                    continue;
                }
                visited.add(currentPosition);

                for (String towel : towels) {
                    int nextPosition = currentPosition + towel.length();

                    if (nextPosition <= pattern.length() &&
                            pattern.substring(currentPosition, nextPosition).equals(towel)) {

                        if (nextPosition == pattern.length()) {
                            isPossible = true;
                            result++;
                            break;
                        }
                        queue.add(nextPosition);
                    }
                }

                if (isPossible){
                    break;
                }
            }
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();
        long totalArrangements = 0;

        for (int j = 0; j < patterns.size(); j++) {
            String pattern = patterns.get(j);
            long[] dp = new long[pattern.length() + 1];
            dp[0] = 1;

            for (int i = 0; i < pattern.length(); i++) {
                if (dp[i] == 0) continue;

                for (String towel : towels) {
                    int endPosition = i + towel.length();

                    if (endPosition <= pattern.length() &&
                            pattern.substring(i, endPosition).equals(towel)) {
                        dp[endPosition] += dp[i];
                    }
                }
            }

            long arrangements = dp[pattern.length()];
            totalArrangements += arrangements;
        }

        System.out.println(totalArrangements);
    }

    private static void prepare(){
        List<String> input = reader.readFile();

        patterns.clear();
        String s = input.getFirst().replaceAll(" ", "");
        towels = s.split(",");

        for (int i = 2; i < input.size(); i++) {
            patterns.add(input.get(i));
        }
    }
}
