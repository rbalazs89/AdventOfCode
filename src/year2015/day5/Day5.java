package year2015.day5;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day5 {

    ArrayList<PairOfLetters> letters = new ArrayList<>();
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
            int vowelCounter = 0;
            boolean twiceInARow = false;
            boolean threeVowel = false;
            boolean noNaughtyString = true;
            for(int j = 0 ; j < fileLines.get(i).length(); j ++){

                if(fileLines.get(i).contains("ab") || fileLines.get(i).contains("cd") || fileLines.get(i).contains("pq") || fileLines.get(i).contains("xy")) {
                    noNaughtyString = false;
                }
                if("aeiou".contains(fileLines.get(i).substring(j,j+1))){
                    vowelCounter++;
                }
                if (vowelCounter >=3){
                    threeVowel = true;
                }
                if(j + 1 < fileLines.get(i).length()){
                    if(fileLines.get(i).substring(j, j + 1).equals(fileLines.get(i).substring(j + 1, j + 2))){
                        twiceInARow = true;
                    }
                }

            }
            if(twiceInARow && threeVowel && noNaughtyString){
                solution++;
            }
        }
        System.out.println(solution);
    }

    public void part2(){
        readData();
        int solution = 0;
        for(int i = 0; i < fileLines.size(); i ++){
            boolean twiceInARow = false;
            boolean goodPair = false;


            for(int j = 0 ; j < fileLines.get(i).length(); j ++){
                //2string twice in a row
                if(j + 2 < fileLines.get(i).length()){
                    if(fileLines.get(i).substring(j, j + 1).equals(fileLines.get(i).substring(j + 2, j + 3))){
                        twiceInARow = true;
                    }
                }
                //NoOverlapPair goodpair
                if(j + 1 < fileLines.get(i).length()) {
                    letters.add(new PairOfLetters(fileLines.get(i).substring(j, j+2), j));
                }

            }
            for(int a = 0; a < letters.size(); a ++){
                for(int b = a + 1; b < letters.size(); b ++){
                    if (letters.get(a).content.equals(letters.get(b).content) &&
                            Math.abs(letters.get(a).position - letters.get(b).position) != 1) {
                        goodPair = true;
                        break;
                    }
                }
            }
            letters.clear();
            if(twiceInARow && goodPair){
                solution++;
            }
        }
        System.out.println(solution);
    }
}
