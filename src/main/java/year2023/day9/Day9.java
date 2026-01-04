package year2023.day9;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {
    private final ReadLines reader = new ReadLines(2023, 9, 2);
    private final List<List<Integer>> numbers = new ArrayList<>();

    private void prepare(){
        numbers.clear();
        List<String> input = reader.readFile();
        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<>();
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int number = Integer.parseInt(matcher.group());
                lineNumbers.add(number);
            }
            numbers.add(lineNumbers);
        }
    }

    public void part1(){
        prepare();

        int solution = 0;
        for (List<Integer> wip : numbers) {
            while (!wip.stream().allMatch(x -> x == 0)) {
                solution += wip.getLast();
                List<Integer> nextLine = new ArrayList<>();
                for (int i = 0; i < wip.size() - 1; i++) {
                    int diff = wip.get(i + 1) - wip.get(i);
                    nextLine.add(diff);
                }
                wip = nextLine;
            }
        }
        System.out.println(solution);
    }

    public void part2(){
        prepare();

        List<Integer> solutions = new ArrayList<>();
        for (List<Integer> wip : numbers) {
            List<Integer> firstColumn = new ArrayList<>();
            while (!wip.stream().allMatch(x -> x == 0)) {
                firstColumn.add(wip.getFirst());
                List<Integer> nextLine = new ArrayList<>();
                for (int i = 0; i < wip.size() - 1; i++) {
                    int diff = wip.get(i + 1) - wip.get(i);
                    nextLine.add(diff);
                }
                wip = nextLine;
            }
            int tempNumber = 0;
            for (int i = 0; i < firstColumn.size(); i++) {
                tempNumber = firstColumn.get(firstColumn.size() - 1 - i) - tempNumber;
                if(i == firstColumn.size() - 1){
                    solutions.add(tempNumber);
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < solutions.size(); i++) {
            sum += solutions.get(i);
        }
        System.out.println(sum);
    }
}