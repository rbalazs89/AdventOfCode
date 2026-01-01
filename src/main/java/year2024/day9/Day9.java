package year2024.day9;

import main.ReadLines;

import java.util.ArrayList;

public class Day9 {
    private final ReadLines reader = new ReadLines(2024, 9, 1);
    private static int[] blocks;

    private void processInput(){
        String input = reader.readFile().getFirst();
        int length = 0;
        for (int i = 0; i < input.length(); i++) {
            length = length + Integer.parseInt(String.valueOf(input.charAt(i)));
        }
        blocks = new int[length];

        int blockValue = 0;
        int stepCounter = 0;
        int currentValue;

        for (int i = 0; i < blocks.length; i++) {
            int currentLength = Integer.parseInt(String.valueOf(input.charAt(stepCounter)));
            stepCounter++;
            if (stepCounter % 2 == 0) {
                currentValue = -1;
                blockValue--;
            } else {
                currentValue = blockValue;
            }
            for (int j = 0; j < currentLength; j++) {
                blocks[i] = currentValue;
                i++;
            }
            blockValue++;
            i--;
        }
    }

    public void part1(){
        processInput();
        outerLoop:
        for (int i = 0; i < blocks.length; i++) {
            if(blocks[i] != -1){
                continue;
            }

            for (int j = blocks.length - 1; j >= 0 ; j--) {
                if(i >= j){
                    break outerLoop;
                }
                if(blocks[j] != -1){
                    int tempInt = blocks[i];
                    blocks[i] = blocks[j];
                    blocks[j] = tempInt;
                    break;
                }
            }
        }
        printResult();
    }

    public void part2(){
        processInput();

        // starting values
        boolean foundToMoveEndingNumber = false;
        boolean foundToMoveStartingNumber = false;
        int numberToMove = -2;
        int toMoveEndingIndex = -2;
        int toMoveStartingIndex = -2;
        int gapToFindStartIndex = 0;
        int gapToFindEndIndex = 0;

        // don't reset these:
        int investigateToMoveFromHere = blocks.length - 1;

        for (int i = investigateToMoveFromHere;  i >= 0; i--) {

            // find window of the current array to move
            // first find ending number of the window
            if(blocks[i] != -1){
                foundToMoveEndingNumber = true;
                toMoveEndingIndex = i;
                numberToMove = blocks[i];
            }

            //find starting number of the window
            if(foundToMoveEndingNumber){
                for (int j = toMoveEndingIndex;  j >= 0; j--) {
                    if(blocks[j] != numberToMove){
                        foundToMoveStartingNumber = true;
                        toMoveStartingIndex = j + 1;
                        break;
                    }
                }
            }

            //find a suitable window where to place the array window
            if(foundToMoveStartingNumber){
                int gapToFind = toMoveEndingIndex - toMoveStartingIndex + 1;
                int counterTheGap = 0;
                int gapIndex = -1;

                //start searching for a suitable gap
                for (int j = 0; j < toMoveEndingIndex; j++) {

                    //one break condition
                    if (j > toMoveEndingIndex - gapToFind) {
                        foundToMoveEndingNumber = false;
                        foundToMoveStartingNumber = false;
                        numberToMove = -2;
                        toMoveEndingIndex = -2;
                        toMoveStartingIndex = -2;
                        i = i - gapToFind + 1;
                        break;
                    }

                    //break condition end
                    if(blocks[j] == -1){
                        counterTheGap ++;
                        gapIndex = j;
                    } else {
                        counterTheGap = 0;
                    }

                    //switch the numbers if suitable gap found:
                    if (counterTheGap == gapToFind) {
                        gapToFindEndIndex = gapIndex;
                        gapToFindStartIndex = gapIndex - counterTheGap + 1;
                        for (int k = gapToFindStartIndex; k <= gapToFindEndIndex; k++) {
                            blocks[k] = numberToMove;
                        }

                        for (int k = toMoveStartingIndex; k <= toMoveEndingIndex; k++) {
                            blocks[k] = -1;
                        }

                        //reset values for the next loop:
                        foundToMoveEndingNumber = false;
                        foundToMoveStartingNumber = false;
                        numberToMove = -2;
                        toMoveEndingIndex = -2;
                        toMoveStartingIndex = -2;
                        i = i - counterTheGap + 1;
                        break;
                    }
                }
            }
        }

        printResult();
    }

    private static void printResult(){
        Long result = 0L;
        for (int i = 0; i < blocks.length; i++) {
            if(blocks[i] != -1){
                result = result + (long) i * blocks[i];
            }
        }
        System.out.println(result);
    }
}
