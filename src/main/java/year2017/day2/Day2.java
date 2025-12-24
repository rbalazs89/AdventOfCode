package year2017.day2;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    private final ReadLines reader = new ReadLines(2017, 2, 2);
    private List<String> fileLines; // lines from txt file

    private final ArrayList<ArrayList<Integer>> numbersTable = new ArrayList<>();
    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        numbersTable.clear();
        readData();

        Pattern p = Pattern.compile("\\d+");
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);
            ArrayList<Integer> current = new ArrayList<>();
            while (m.find()){
                current.add(Integer.parseInt(m.group()));
            }
            numbersTable.add(current);
        }
    }

    public void part1(){
        prepare();
        int result = 0;
        for (int i = 0; i < numbersTable.size(); i++) {
            int currentMin = Integer.MAX_VALUE;
            int currentMax = Integer.MIN_VALUE;
            ArrayList<Integer> current = numbersTable.get(i);
            for (int j = 0; j < current.size(); j++) {
                currentMin = Math.min(current.get(j), currentMin);
                currentMax = Math.max(current.get(j), currentMax);
            }
            result += currentMax - currentMin;
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();
        int result = 0;

        for (int i = 0; i < numbersTable.size(); i++) {
            ArrayList<Integer> current = numbersTable.get(i);

            middleLoop:
            for (int j = 0; j < current.size() - 1; j++) {
                for (int k = j + 1  ; k < current.size(); k++) {
                    int bigger = Math.max(current.get(j), current.get(k));
                    int smaller = Math.min(current.get(j), current.get(k));
                    if(bigger % smaller == 0){
                        result += bigger / smaller;
                        break middleLoop; // task description states that there is exactly one of these per row
                    }
                }
            }
        }
        System.out.println(result);
    }
}
