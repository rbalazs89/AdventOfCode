package year2024.day1;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {
    private final ReadLines reader = new ReadLines(2024, 1, 2);

    ArrayList<Integer> firstColumn = new ArrayList<>();
    ArrayList<Integer> secondColumn = new ArrayList<>();

    private void prepare(){
        firstColumn.clear();
        secondColumn.clear();
        List<String> lines = reader.readFile();
        for(int i = 0; i < lines.size(); i ++){
            String[] parts = lines.get(i).split("\\s+");
            firstColumn.add(Integer.valueOf(parts[0]));
            secondColumn.add(Integer.valueOf(parts[1]));
        }
    }

    public void part1(){
        prepare();

        Collections.sort(firstColumn);
        Collections.sort(secondColumn);

        int result = 0;
        for(int i = 0; i < firstColumn.size(); i ++){
            result += Math.abs(firstColumn.get(i) - secondColumn.get(i));
        }

        System.out.println(result);
    }

    public void part2(){
        prepare();

        int result = 0;

        for(int i = 0; i < firstColumn.size(); i ++){
            int tempInt = firstColumn.get(i);
            int counter = 0;
            for(int j = 0; j < secondColumn.size(); j ++){
                if(tempInt == secondColumn.get(j)){
                    counter ++;
                }
            }
            result = result + tempInt * counter;
        }

        System.out.println(result);
    }
}
