package year2015.day1;

import main.ReadLines;

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
        int solution = 0;
        for(int i = 0; i < fileLines.size(); i ++){
            for (int k = 0; k < fileLines.get(i).length(); k++) {
                if(fileLines.get(i).charAt(k) == '('){
                    solution++;
                } else if (fileLines.get(i).charAt(k) == ')'){
                    solution--;
                }
            }
        }
        System.out.println(solution);
    }

    public void part2(){
        int solution = 0;
        int part2Solution = 0;
        for(int i = 0; i < fileLines.size(); i ++){
            for (int k = 0; k < fileLines.get(i).length(); k++) {
                if(fileLines.get(i).charAt(k) == '('){
                    solution++;
                } else if (fileLines.get(i).charAt(k) == ')'){
                    solution--;
                }
                if(solution < 0){
                    part2Solution = k + 1;
                    break;
                }
            }
        }
        System.out.println(part2Solution);
    }
}
