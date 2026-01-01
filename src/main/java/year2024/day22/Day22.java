package year2024.day22;

import main.ReadLines;

import java.util.*;

public class Day22 {
    private static final ReadLines reader = new ReadLines(2024, 22, 1);
    private static int[] numbers;
    private static final Set<String> seq = new HashSet<>();
    private static final HashMap<String, Integer> savedData = new HashMap<>();

    private void prepare(){
        seq.clear();
        savedData.clear();
        List<String> input = reader.readFile();

        numbers = new int[input.size()];
        for (int i = 0; i < input.size(); i++) {
            numbers[i] = Integer.parseInt(input.get(i));
        }
    }

    public void part1(){
        prepare();
        long result = 0L;
        for (long number : numbers) {
            long current = number;
            for (int i = 0; i < 2000; i++) {
                current = oneStep(current);
                if (i == 1999) {
                    result += current;
                }
            }
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();
        for (long number : numbers) {
            long current = number;
            int[] window = new int[4];
            for (int i = 0; i < 2000; i++) {
                long changed = oneStep(current);
                int ch10 = (int) (changed % 10);
                int cu10 = (int) (current % 10);

                if (i < 4) {
                    window[i] = ch10 - cu10;
                }

                if (i >= 4) {

                    window[0] = window[1];
                    window[1] = window[2];
                    window[2] = window[3];
                    window[3] = ch10 - cu10;

                    String s = window[0] + "," + window[1] + "," + window[2] + "," + window[3];
                    if (seq.add(s)) {
                        savedData.merge(s, ch10, Integer::sum);
                    }
                }
                current = changed;
            }
            seq.clear();
        }
        int max = 0;
        for( Map.Entry<String, Integer> entry : savedData.entrySet()){
            max = Math.max(entry.getValue(), max);
        }
        System.out.println(max);
    }

    private static long oneStep(Long n){
        n = (n * 64) ^ n;
        n = prune(n);

        n = n ^ (n / 32);
        n = prune(n);

        n = (n * 2048) ^ n;
        n = prune(n);
        return n;
    }

    private static long prune(long n){
        return n % 16777216;
    }
}