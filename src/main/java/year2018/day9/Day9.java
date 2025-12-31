package year2018.day9;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {
    private final ReadLines reader = new ReadLines(2018, 9, 2);
    private static final int M = 23; // multiples of 23 are kept, specified by task description
    private static final int S = 7; // steps backwards, specified by task description

    private int[] getInput(){ // number of player, and max value
        String line = reader.readFile().getFirst();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher m = pattern.matcher(line);
        List<Integer> n = new ArrayList<>();
        while (m.find()){
            n.add(Integer.parseInt(m.group()));
        }
        return new int[]{n.get(0), n.get(1)};
    }

    public void part1(){
        int[] input = getInput();
        int maxPlayers = input[0];
        int lastMarble = input[1];

        long[] scores = new long[maxPlayers];
        List<Integer> circle = new ArrayList<>();
        circle.add(0);
        int currentPosition = 0;
        int currentPlayer = 0;

        for (int marble = 1; marble <= lastMarble; marble++) {
            if (marble % M == 0) {
                // Player keeps the marble they would have placed
                scores[currentPlayer] += marble;
                int removePosition = currentPosition - S;
                if (removePosition < 0) {
                    removePosition = circle.size() + removePosition;
                }

                scores[currentPlayer] += circle.get(removePosition);
                circle.remove(removePosition);

                // Current marble becomes the one clockwise of the removed marble
                currentPosition = removePosition;
                if (currentPosition >= circle.size()) {
                    currentPosition = 0;
                }
            } else {
                currentPosition = (currentPosition + 2) % circle.size();
                if (currentPosition == 0) {
                    circle.add(marble);
                    currentPosition = circle.size() - 1;
                } else {
                    circle.add(currentPosition, marble);
                }
            }

            // Move to next player
            currentPlayer = (currentPlayer + 1) % maxPlayers;
        }

        // Find and print highest score
        long maxScore = 0;
        for (long score : scores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }
        System.out.println(maxScore);
    }


    public void part2(){

        int[] input = getInput();
        int maxPlayers = input[0];
        int lastMarble = input[1] * 100;

        long[] scores = new long[maxPlayers];

        int currentPlayer = 3;

        // set up first three manually: (0,2,1);
        Marble zero = new Marble(0);
        Marble one = new Marble(1);
        Marble two = new Marble(2);

        zero.next = two;
        zero.previous = one;

        two.next = one;
        two.previous = zero;

        one.next = zero;
        one.previous = two;

        Marble currentMarble = two;

        for (int marble = 3; marble <= lastMarble; marble++) {
            if (marble % M == 0) {
                // Player keeps the marble they would have placed
                scores[currentPlayer] += marble;

                for (int i = 0; i < S; i++) {
                    currentMarble = currentMarble.previous;
                }
                scores[currentPlayer] += currentMarble.value;

                // remove the one seven steps back from the chain
                Marble afterRemovedMarble = currentMarble.next;
                Marble beforeRemovedMarble = currentMarble.previous;

                beforeRemovedMarble.next = afterRemovedMarble;
                afterRemovedMarble.previous = beforeRemovedMarble;

                currentMarble = afterRemovedMarble;

            } else {
                // add next step in the middle of the chain
                Marble futureStepPrevious = currentMarble.next;
                Marble futureStepNext = currentMarble.next.next;

                Marble future = new Marble(marble); // the new marble this round
                future.previous = futureStepPrevious;
                futureStepPrevious.next = future;

                future.next = futureStepNext;
                futureStepNext.previous = future;

                currentMarble = future;
            }

            currentPlayer = (currentPlayer + 1) % maxPlayers;
        }

        // Find and print highest score
        long maxScore = 0;
        for (long score : scores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }
        System.out.println(maxScore);
    }

    private static class Marble {
        int value;
        Marble previous;
        Marble next;
        Marble(int value){
            this.value = value;
        }
    }
}