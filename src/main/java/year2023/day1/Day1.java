package year2023.day1;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

public class Day1 {
    private final ReadLines reader = new ReadLines(2023, 1, 2);

    public void part1(){
        List<String> input = reader.readFile();

        List<Integer> lineSolutions = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        int solution = 0;

        for(int i = 0; i < input.size(); i ++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (isDigit(input.get(i).charAt(j))) {
                    lineSolutions.add(Integer.valueOf(input.get(i).substring(j, j + 1)));
                }
            }
            numbers.add(lineSolutions.getFirst());
            numbers.add(lineSolutions.getLast());
            lineSolutions.clear();
        }
        for(int i = 0 ; i < numbers.size(); i = i + 2){
            solution = solution + Integer.parseInt((numbers.get(i)) + String.valueOf(numbers.get(i+1)));
        }
        System.out.println(solution);
    }

    public void part2(){
        List<String> input = reader.readFile();

        List<Integer> lineSolutions = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        int solution = 0;

        for(int i = 0; i < input.size(); i ++){
            for(int j = 0; j < input.get(i).length(); j ++){
                if(isDigit(input.get(i).charAt(j))){
                    lineSolutions.add(Integer.valueOf(input.get(i).substring(j,j+1)));
                } else {
                    if(j + 3 <= input.get(i).length()){
                        if(input.get(i).startsWith("one", j)){
                            lineSolutions.add(1);
                        }
                        if(input.get(i).startsWith("two", j)){
                            lineSolutions.add(2);
                        }
                        if(input.get(i).startsWith("six", j)){
                            lineSolutions.add(6);
                        }
                    }
                    if(j + 4 <= input.get(i).length()){
                        if(input.get(i).startsWith("four", j)){
                            lineSolutions.add(4);
                        }
                        if(input.get(i).startsWith("five", j)){
                            lineSolutions.add(5);
                        }
                        if(input.get(i).startsWith("nine", j)){
                            lineSolutions.add(9);
                        }
                    }
                    if(j + 5 <= input.get(i).length()){
                        if(input.get(i).startsWith("three", j)){
                            lineSolutions.add(3);
                        }
                        if(input.get(i).startsWith("seven", j)){
                            lineSolutions.add(7);
                        }
                        if(input.get(i).startsWith("eight", j)){
                            lineSolutions.add(8);
                        }
                    }
                }
            }
            numbers.add(lineSolutions.getFirst());
            numbers.add(lineSolutions.getLast());
            lineSolutions.clear();
        }

        for(int i = 0 ; i < numbers.size(); i = i + 2){
            solution = solution + Integer.parseInt((numbers.get(i)) + String.valueOf(numbers.get(i+1)));
        }
        System.out.println(solution);
    }
}
