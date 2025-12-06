package year2015.day2;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Character.isDigit;

public class Day2 {
    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        readData();

        int solution = 0;
        ArrayList<Integer> dimensions = new ArrayList<>();
        for(int i = 0; i < fileLines.size(); i ++){
            for(int k = 0; k < fileLines.get(i).length(); k ++){
                if(isDigit(fileLines.get(i).charAt(k))){
                    int startDigit = k;
                    int endDigit = k + 1;
                    k++;
                    if( k < fileLines.get(i).length()){
                        if(isDigit(fileLines.get(i).charAt(k))){
                            endDigit++;
                            k++;
                            if( k < fileLines.get(i).length()) {
                                if (isDigit(fileLines.get(i).charAt(k))) {
                                    endDigit++;
                                    k++;
                                }
                            }
                        }
                    }
                    dimensions.add(Integer.valueOf(fileLines.get(i).substring(startDigit,endDigit)));
                }
            }
            Collections.sort(dimensions);
            solution = solution + 2 * (dimensions.get(0) * dimensions.get(1) + dimensions.get(1) * dimensions.get(2) + dimensions.get(2) * dimensions.get(0)) + dimensions.get(0) * dimensions.get(1);
            dimensions.clear();
        }
        System.out.println("part 1: " + solution);
    }

    public void part2(){
        int solution = 0;
        ArrayList<Integer> dimensions = new ArrayList<>();
        for(int i = 0; i < fileLines.size(); i ++){
            for(int k = 0; k < fileLines.get(i).length(); k ++){
                if(isDigit(fileLines.get(i).charAt(k))){
                    int startDigit = k;
                    int endDigit = k + 1;
                    k++;
                    if( k < fileLines.get(i).length()){
                        if(isDigit(fileLines.get(i).charAt(k))){
                            endDigit++;
                            k++;
                            if( k < fileLines.get(i).length()) {
                                if (isDigit(fileLines.get(i).charAt(k))) {
                                    endDigit++;
                                    k++;
                                }
                            }
                        }
                    }
                    dimensions.add(Integer.valueOf(fileLines.get(i).substring(startDigit,endDigit)));
                }
            }
            Collections.sort(dimensions);
            solution = solution + dimensions.get(0) * 2 + dimensions.get(1) * 2 + dimensions.get(2) * dimensions.get(1) * dimensions.get(0);
            dimensions.clear();
        }
        System.out.println("part 2: " + solution);
    }
}
