package year2016.day7;

import main.ReadLines;

import java.util.List;

public class Day7 {
    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 7, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();
        int counter = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);

            boolean requirementOne = false;
            for (int j = 0; j < line.length(); j++) {
                if(j + 3 < line.length()){
                    if(line.charAt(j) == line.charAt(j + 3) && line.charAt(j) != '[' && line.charAt(j) != ']'){
                        if(line.charAt(j + 1) == line.charAt(j + 2) && line.charAt(j + 1) != '[' && line.charAt(j + 1) != ']' && line.charAt(j) != line.charAt(j + 1)){

                            boolean rightFound = false;
                            boolean leftFound = false;

                            // check right
                            for (int k = j + 2; k < line.length(); k++) {
                                if(line.charAt(k) == '['){
                                    break;
                                } else if(line.charAt(k) == ']'){
                                    rightFound = true;
                                    break;
                                }
                            }

                            // check left
                            for (int k = j; k >= 0; k--) {
                                if(line.charAt(k) == ']'){
                                    break;
                                } else if(line.charAt(k) == '['){
                                    leftFound = true;
                                    break;
                                }
                            }
                            if(rightFound && leftFound){
                                requirementOne = false;
                                break;
                            } else {
                                requirementOne = true;
                            }

                        }
                    }
                }
            }

            if(requirementOne){
                counter ++;
            }
        }
        System.out.println(counter);
    }

    public void part2(){
        int counter = 0;
        outerLoop:
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);

            middleLoop:
            for (int j = 0; j < line.length(); j++) {
                if(j + 2 < line.length()){
                    if(line.charAt(j) == line.charAt(j + 2) && line.charAt(j) != '[' && line.charAt(j) != ']' && line.charAt(j) != line.charAt(j + 1) && line.charAt(j + 1) != '[' && line.charAt(j + 1) != ']'){
                        char char1 = line.charAt(j);
                        char char2 = line.charAt(j + 1);
                        boolean req1 = false; // needs to be in bracket

                        // req 1
                        // check right
                        boolean foundRight = false;
                        for (int k = j; k < line.length(); k++) {
                            if(line.charAt(k) == '['){
                                continue middleLoop;
                            } else if (line.charAt(k) == ']'){
                                foundRight = true;
                                break;
                            }
                        }
                        //check left
                        boolean foundLeft = false;
                        for (int k = j; k >= 0; k--) {
                            if(line.charAt(k) == ']'){
                                continue middleLoop;
                            } else if (line.charAt(k) == '['){
                                foundLeft = true;
                                break;
                            }
                        }
                        if(foundLeft && foundRight){
                            req1 = true;
                        }

                        // req 2
                        for (int k = 0; k < line.length(); k++) {
                            if(k + 2 < line.length()){
                                if(line.charAt(k) == char2 && line.charAt(k + 2) == char2 && line.charAt(k + 1) == char1){
                                    // needs req 3: must not be in bracket
                                    // check right
                                    boolean rightOK = false;
                                    for (int l = k; l < line.length(); l++) {
                                        if(line.charAt(l) == '['){
                                            break;
                                        } else if (line.charAt(l) == ']'){
                                            rightOK = true;
                                            break;
                                        }
                                    }
                                    // check left
                                    boolean leftOK = false;
                                    for (int l = k; l >= 0 ; l--) {
                                        if(line.charAt(l) == ']'){
                                            break;
                                        } else if (line.charAt(l) == '['){
                                            leftOK = true;
                                            break;
                                        }
                                    }
                                    if(!(leftOK && rightOK) && req1){
                                        counter ++;
                                        continue outerLoop;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(counter);
    }
}
