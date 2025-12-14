package year2025.day12;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day12 {
    List<String> fileLines;
    int inputFileIndex = 2;
    ArrayList<Configuration> configurations = new ArrayList<>();

    public void readData() {
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }

    public void processData(){
        for (int i = 30; i < fileLines.size(); i++) {
            Configuration c = new Configuration();
            String parts[] = fileLines.get(i).split(" ");

            c.shapes = new int[parts.length - 1];
            for (int j = 0; j < parts.length - 1; j++) {
                c.shapes[j] = Integer.parseInt(parts[j + 1]);
            }
            String firstPart = parts[0];
            firstPart = firstPart.substring(0, firstPart.length() - 1);
            String sizes[] = firstPart.split("x");
            c.x = Integer.parseInt(sizes[0]);
            c.y = Integer.parseInt(sizes[1]);
            configurations.add(c);
        }
    }

    public void part1(){
        readData();
        processData();
        int solution = 0;
        for (int i = 0; i < configurations.size(); i++) {
            Configuration c = configurations.get(i);
            int sum = 0;
            for (int j = 0; j < c.shapes.length; j++) {
                sum += c.shapes[j];
            }
            int fullArea = c.x * c.y;
            int takenArea = sum * 7;
            if(fullArea > takenArea){
                solution ++;
            }
        }
        System.out.println(solution);
    }

    public void part2(){
        // nothing to do here, no part 2
    }
}
