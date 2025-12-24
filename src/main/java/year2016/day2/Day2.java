package year2016.day2;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day2 {
    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 2,2);
    private void readData(){
        fileLines = reader.readFile();
    }
    /*
    +---+ +---+ +---+
    | 1 | | 2 | | 3 |
    +---+ +---+ +---+
    | 4 | | 5 | | 6 |
    +---+ +---+ +---+
    | 7 | | 8 | | 9 |
    +---+ +---+ +---+
     */

    public void part1() {
        readData();
        int x = 1;
        int y = 1;
        ArrayList<int[]> buttonPresses = new ArrayList<>();
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char current = line.charAt(j);
                if (current == 'L') {
                    x = Math.max(x - 1, 0);
                } else if (current == 'R') {
                    x = Math.min(x + 1, 2);
                } else if (current == 'U') {
                    y = Math.max(y - 1, 0);
                } else if (current == 'D') {
                    y = Math.min(y + 1, 2);
                }
            }
            buttonPresses.add(new int[]{x, y});
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buttonPresses.size(); i++) {
            result.append(buttonPresses.get(i)[0] + 1 + (buttonPresses.get(i)[1] * 3));
        }
        System.out.println(result);
    }

    /*
                +---+
                | 1 |
                +---+
          +---+ +---+ +---+
          | 2 | | 3 | | 4 |
          +---+ +---+ +---+
    +---+ +---+ +---+ +---+ +---+
    | 5 | | 6 | | 7 | | 8 | | 9 |
    +---+ +---+ +---+ +---+ +---+
          +---+ +---+ +---+
          | A | | B | | C |
          +---+ +---+ +---+
                +---+
                | D |
                +---+
    */
    public void part2() {
        int x = 0;
        int y = 2;
        ArrayList<int[]> buttonPresses = new ArrayList<>();
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char current = line.charAt(j);

                // column 0:
                if(y == 2 && x == 0){
                    if (current == 'R') {
                        x = x + 1;
                    }
                }

                // column 1:
                else if(y == 1 && x == 1){
                    if (current == 'R') {
                        x = x + 1;
                    }
                    else if (current == 'D') {
                        y = y + 1;
                    }
                }
                else if (y == 2 && x == 1){
                    if (current == 'L') {
                        x = x - 1;
                    }
                    else if (current == 'R') {
                        x = x + 1;
                    }
                    else if (current == 'U') {
                        y = y - 1;
                    }
                    else if (current == 'D') {
                        y = y + 1;
                    }
                }
                else if (y == 3 && x == 1){
                    if (current == 'R') {
                        x = x + 1;
                    }
                    else if (current == 'U') {
                        y = y - 1;
                    }
                }

                //column 3
                else if(x == 2){
                    if(y == 0){
                        if (current == 'D') {
                            y = y + 1;
                        }
                    }
                    else if (y == 1 || y == 2 || y == 3){
                        if (current == 'L') {
                            x = x - 1;
                        }
                        else if (current == 'R') {
                            x = x + 1;
                        }
                        else if (current == 'U') {
                            y = y - 1;
                        }
                        else if (current == 'D') {
                            y = y + 1;
                        }
                    } else if (y == 4){
                        if (current == 'U') {
                            y = y - 1;
                        }
                    }
                }

                // column 4:
                else if (x == 3){
                    if (y == 1){
                        if (current == 'L') {
                            x = x - 1;
                        }
                        else if (current == 'D') {
                            y = y + 1;
                        }
                    }
                    else if (y == 2){
                        if (current == 'L') {
                            x = x - 1;
                        }
                        else if (current == 'R') {
                            x = x + 1;
                        }
                        else if (current == 'U') {
                            y = y - 1;
                        }
                        else if (current == 'D') {
                            y = y + 1;
                        }
                    }
                    else if (y == 3){
                        if (current == 'L') {
                            x = x - 1;
                        }
                        else if (current == 'U') {
                            y = y - 1;
                        }
                    }
                }
                // column 5:
                else if (x == 4){
                    if(y == 2){
                        if (current == 'L') {
                            x = x - 1;
                        }
                    }
                } else {
                    System.out.println("beep");
                }
            }
            buttonPresses.add(new int[]{x, y});
        }
        String result = "";
        for (int i = 0; i < buttonPresses.size(); i++) {
            System.out.println(i + " " + buttonPresses.get(i)[0] + " " + buttonPresses.get(i)[1]);
        }
        System.out.println(result);
    }
}