package year2018.day2;

import main.ReadLines;

import java.util.HashMap;
import java.util.List;

public class Day2 {
    private final ReadLines reader = new ReadLines(2018, 2, 2);

    private List<String> getFileLines(){
        return reader.readFile();
    }

    public void part1(){
        List<String> fileLines = getFileLines();

        int twoOfTheSameCounter = 0;
        int threeOfTheSameCounter = 0;

        for (String line : fileLines) {

            // character name and occurrence map
            HashMap<Character, Integer> charFrequency = new HashMap<>();
            for (char c : line.toCharArray()) {
                charFrequency.merge(c, 1, Integer::sum);
            }

            // max only one increment per word
            boolean hasThreeOfSame = false;
            boolean hasTwoOfSame = false;

            for(int count : charFrequency.values()){
                if(count == 2){
                    hasTwoOfSame = true;
                } else if (count == 3){
                    hasThreeOfSame = true;
                }
            }

            // increment the counters:
            if(hasThreeOfSame){
                threeOfTheSameCounter ++;
            }
            if(hasTwoOfSame){
                twoOfTheSameCounter ++;
            }
        }

        System.out.println(threeOfTheSameCounter * twoOfTheSameCounter);
    }

    public void part2(){
        List<String> fileLines = getFileLines();

        int linesSize = fileLines.size();

        for (int i = 0; i < linesSize; i++) {
            String line1 = fileLines.get(i);
            for (int j = i + 1; j < linesSize; j++) {
                String line2 = fileLines.get(j);

                // only one character difference:
                int misMatches = 0;
                int savedPosition = -1;

                // inputs are guaranteed to be the same length from puzzle description
                for (int k = 0; k < line1.length(); k++) {
                    if(line1.charAt(k) != line2.charAt(k)){
                        misMatches ++;
                        savedPosition = k; // save the position of the difference
                        if(misMatches > 1){
                            break;
                        }
                    }
                }

                if(misMatches == 1){
                    String part2Result = line1.substring(0,savedPosition) + line1.substring(savedPosition + 1);
                    System.out.println(part2Result);
                    return; // ends loop if exactly one mismatch was found
                }
            }
        }

        System.out.println("no result found");
    }
}
