package year2016.day3;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private List<String> fileLines;
    private final ArrayList<int[]> triangles = new ArrayList<>();
    private final ReadLines reader = new ReadLines(2016, 3, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    private void processData() {
        readData();
        triangles.clear();

        Pattern p = Pattern.compile("\\d+");
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);
            int[] numbers = new int[3];
            int counter = 0;
            while(m.find()){
                numbers[counter] = Integer.parseInt(m.group());
                counter ++;
            }
            triangles.add(numbers);
        }
    }

    public void part1(){
        processData();
        int counter = 0;
        for (int i = 0; i < triangles.size(); i++) {
            if(isTrianglePossible(triangles.get(i))){
                counter++;
            }
        }
        System.out.println(counter);
    }

    public void part2(){
        processData();
        int counter = 0;
        for (int i = 0; i < triangles.size(); i = i + 3) {
            for (int q = 0; q < 3; q++) {
                if(isTrianglePossible(new int[] {triangles.get(i)[q], triangles.get(i + 1)[q], triangles.get(i + 2)[q]})){
                    counter ++;
                }
            }
        }
        System.out.println(counter);
    }

    private boolean isTrianglePossible(int[] threeNumbers){
        int highest = Math.max(Math.max(threeNumbers[0], threeNumbers[1]), threeNumbers[2]);
        int sum = threeNumbers[0] + threeNumbers[1] + threeNumbers[2];
        return sum - highest > highest;
    }
}
