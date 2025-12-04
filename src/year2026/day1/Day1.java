package year2026.day1;
import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day1 {

    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        readData();

        // TRANSLATE input to instructions
        List<String> directions = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < fileLines.size(); i++) {
            directions.add(fileLines.get(i).substring(0,1));

            int number = Integer.valueOf(fileLines.get(i).substring(1));
            if(number > 100){
                number = number % 100;
            }
            numbers.add(number);
        }

        // apply instructions
        int currentIndex = 50;
        int counter = 0;
        for (int i = 0; i < directions.size(); i++) {
            if(directions.get(i).equals("L")){
                currentIndex = currentIndex - numbers.get(i);
            } else {
                currentIndex = currentIndex + numbers.get(i);
            }
            if (currentIndex >= 100){
                currentIndex = currentIndex - 100;
            }
            else if(currentIndex < 0){
                currentIndex = currentIndex + 100;
            }

            if(currentIndex == 0){
                counter++;
            }
        }
        System.out.println("part 1: " + counter);
    }

    public void part2(){
        List<String> directions = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        List<Integer> over100 = new ArrayList<>();

        //get instructions from txt
        for (int i = 0; i < fileLines.size(); i++) {
            directions.add(fileLines.get(i).substring(0,1));

            int number = Integer.valueOf(fileLines.get(i).substring(1));
            if(number > 100){
                over100.add(number/100);
                number = number % 100;
            } else {
                over100.add(0);
            }
            numbers.add(number);
        }

        //apply instructions, safe starts at index 50
        int currentIndex = 50;
        int counter = 0;
        for (int i = 0; i < directions.size(); i++) {
            int currentSteps = numbers.get(i);
            if(directions.get(i).equals("L")){
                for (int j = 0; j < currentSteps; j++) {
                    currentIndex --;
                    if(currentIndex == 0){
                        counter++;
                    }
                    if(currentIndex == -1){
                        currentIndex = 99;
                    }
                }
            } else {
                for (int j = 0; j < currentSteps; j++) {
                    currentIndex ++;
                    if(currentIndex == 100){
                        counter++;
                    }
                    if(currentIndex == 100){
                        currentIndex = 0;
                    }
                }
            }
            counter = counter + over100.get(i);
        }
        System.out.println("part 2: "+ counter);
    }
}
