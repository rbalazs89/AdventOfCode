package year2017.day20;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 {
    // setup:
    private final ReadLines reader = new ReadLines(2017, 20, 2);

    // puzzle:
    // considering all velocity, acceleration, starting distance values
    // used to estimate arbitrary values in the puzzle
    private long maxValue = 0;

    private List<String> readData() {
        return reader.readFile();
    }

    private ArrayList<SnowBall> prepareSnowBalls(){
        List<String> inputLines = readData();
        ArrayList<SnowBall> snowballs = new ArrayList<>();

        // prepare snowball list from input
        Pattern pattern = Pattern.compile("-?\\d+");
        for (int i = 0; i < inputLines.size(); i++) {
            ArrayList<Long> numbers = new ArrayList<>();
            Matcher m = pattern.matcher(inputLines.get(i));
            while (m.find()){
                numbers.add(Long.parseLong(m.group()));
            }

            long[] p = new long[3];
            long[] v = new long[3];
            long[] a = new long[3];
            for (int j = 0; j < numbers.size(); j++) {
                maxValue = Math.max(Math.abs(numbers.get(j)), maxValue);
                if(j < 3){
                    p[j % 3] = numbers.get(j);
                }
                else if(j < 6){
                    v[j % 3] = numbers.get(j);
                } else {
                    a[j % 3] = numbers.get(j);
                }
            }
            // each line has one new snowball
            snowballs.add(new SnowBall(p,v,a,i));
        }

        return snowballs;
    }

    // need to find snowball closest to 0,0,0 when t-> infinity
    public void part1(){
        ArrayList<SnowBall> snowBalls = prepareSnowBalls();

        long minAcceleration = Long.MAX_VALUE;
        // only acceleration is considered at t -> infinity
        for (int i = 0; i < snowBalls.size(); i++) {
            SnowBall snowBall = snowBalls.get(i);
            long currentAcceleration = Math.abs(snowBall.a()[0]) + Math.abs(snowBall.a()[1]) + Math.abs(snowBall.a()[2]);
            if (currentAcceleration < minAcceleration) {
                minAcceleration = currentAcceleration;
            }
        }

        // solve tiebreaker situation:
        // gather snowballs with the lowest acceleration
        ArrayList<SnowBall> tiebreaker = new ArrayList<>();
        for (int i = 0; i < snowBalls.size(); i++) {
            SnowBall snowBall = snowBalls.get(i);
            long currentDistance = Math.abs(snowBall.a()[0]) + Math.abs(snowBall.a()[1]) + Math.abs(snowBall.a()[2]);
            if (currentDistance == minAcceleration) {
                tiebreaker.add(snowBall);
            }
        }

        long minDistance = Long.MAX_VALUE;
        int winnerWinnerChickenDinner = -1;
        long timePast = maxValue;

        // check distance from 0,0,0 after reasonable time has passed, pick the minimum
        for (int i = 0; i < tiebreaker.size(); i++) {
            SnowBall snowBall = tiebreaker.get(i);
            long x = Math.abs(getPosition(timePast, snowBall, 0));
            long y = Math.abs(getPosition(timePast, snowBall, 1));
            long z = Math.abs(getPosition(timePast, snowBall, 2));
            long currentDistance = x + y + z;
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                winnerWinnerChickenDinner = snowBall.uniqueID();
            }
        }

        System.out.println(winnerWinnerChickenDinner);
        /** Worth to note for full mathematical correctness, that arbitrary timePast might not be enough
         *  for full puzzle solution on all inputs. For this puzzle, in all tried (timePast >= 29) the same output was given;
         */
    }

    // x -> 0, y -> 1, z -> 2
    // increments happen at ticks, not continuously
    private long getPosition(long timePast, SnowBall snowBall, int axis){
        return snowBall.p()[axis] + snowBall.v()[axis] * timePast + snowBall.a()[axis] * timePast * (timePast + 1) / 2;
    }

    public void part2(){
        // hinted in the puzzle description, that collisions only happen in exact seconds
        ArrayList<SnowBall> snowBalls = prepareSnowBalls();

        for (int time = 0; time < maxValue; time++) { // arbitrary max value

            HashMap<String, ArrayList<Integer>> collisionMap = new HashMap<>();
            // find snowballs that end up in the exact same position
            for (int i = 0; i < snowBalls.size(); i++) {
                SnowBall snowBall = snowBalls.get(i);
                long[] distances = new long[3]; // functions as key

                distances[0] = getPosition(time, snowBall, 0);
                distances[1] = getPosition(time, snowBall, 1);
                distances[2] = getPosition(time, snowBall, 2);
                String key = distances[0] + "," + distances[1] + ',' + distances[2];

                ArrayList<Integer> value;
                if(collisionMap.containsKey(key)){
                    value = collisionMap.get(key);
                } else {
                    value = new ArrayList<>();
                }
                value.add(snowBall.uniqueID());
                collisionMap.put(key, value);
            }

            // if more than one of these end up in the same position, add them to removeList
            ArrayList<Integer> IDsToRemove = new ArrayList<>();
            for(Map.Entry <String, ArrayList<Integer>> entry : collisionMap.entrySet()){
                ArrayList<Integer> thisList = entry.getValue();
                if(thisList.size() > 1){
                    IDsToRemove.addAll(thisList);
                }
            }

            Iterator<SnowBall> iterator = snowBalls.iterator();
            while(iterator.hasNext()){
                if(IDsToRemove.contains(iterator.next().uniqueID())){
                    iterator.remove();
                }
            }
        }
        System.out.println(snowBalls.size());
    }
}