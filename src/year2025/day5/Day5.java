package year2025.day5;

import main.ReadLines;
import java.util.*;

public class Day5 {

    List<String> fileLines;
    long[][] ranges;
    long[] numbers;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(1);
    }

    public void processData(){
        int rangesSize = 0;
        int numbersSize = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            if(fileLines.get(i).isEmpty()){
                break;
            }
            rangesSize ++;
        }

        for (int i = rangesSize + 1; i < fileLines.size(); i++) {
            numbersSize ++;
        }

        ranges = new long[rangesSize][2];
        numbers = new long[numbersSize];

        for (int i = 0; i < rangesSize; i++) {
            String line = fileLines.get(i);
            String[] parts = line.split("-");
            ranges[i][0] = Long.parseLong(parts[0]);
            ranges[i][1] = Long.parseLong(parts[1]);
        }

        int counter = 0;
        for (int i = rangesSize + 1; i < fileLines.size(); i++) {
            numbers[counter] = Long.parseLong(fileLines.get(i));
            counter ++;
        }
    }

    public void part1(){
        readData();
        processData();

        int result = 0;

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < ranges.length; j++) {
                if(numbers[i] >= ranges[j][0] && numbers[i] <= ranges[j][1]){
                    result ++;
                    break;
                }
            }
        }
        System.out.println("part 1 result: " + result);
    }

    public void part2didntwork() {
        readData();
        processData();

        Arrays.sort(ranges, Comparator.comparingLong(a -> a[0]));

        List<long[]> merged = new ArrayList<>();

        for (int j = 0; j < ranges.length; j++) {
            long start = ranges[j][0];
            long end = ranges[j][1];

            if (merged.isEmpty()) {
                merged.add(new long[]{start, end});

            } else {
                long[] last = merged.get(merged.size() - 1);

                if (start <= last[1] + 1) {
                    last[1] = Math.max(last[1], end);
                } else {
                    merged.add(new long[]{start, end});
                }
            }
        }

        long result = 0;
        //merged.sort(Comparator.comparingLong(a -> a[0]));
        for (int i = 0; i < merged.size(); i++) {
            result = result + merged.get(i)[1] - merged.get(i)[0] + 1;
        }

        System.out.println("part 2 result: " + result);
    }

    public void part2(){
        readData();
        processData();

        ArrayList<Long[]> fresh = new ArrayList<>();

        for (int i = 0; i < ranges.length; i++) {

            /*System.out.println("loop number: " + i + " checking: " + ranges[i][0] + " " + ranges[i][1]);
            for (int j = 0; j < fresh.size(); j++) {
                System.out.println(fresh.get(j)[0] + " " + fresh.get(j)[1]);
            }*/

            //determine interval position in the existing list
            Long first = ranges[i][0];
            Long second = ranges[i][1];

            if(fresh.isEmpty()){
                fresh.add(new Long[] {first, second});
                continue;
            }

            int positions = fresh.size() * 2;
            int location1 = -1;
            int location2 = -1;
            boolean firstLocationFound = false;
            boolean secondLocationFound = false;

            Long[] numbers = new Long[positions];

            for (int j = 0; j < fresh.size(); j++) {
                numbers[2 * j] = fresh.get(j)[0];
                numbers[2 * j + 1] = fresh.get(j)[1];
            }

            for (int j = 0; j < numbers.length; j++) {
                if(!firstLocationFound) {
                    if(first < numbers[j]){
                        firstLocationFound = true;
                        location1 = j;
                    }
                }
                if(!secondLocationFound) {
                    if(second <= numbers[j]){
                        secondLocationFound = true;
                        location2 = j;
                    }
                }
            }
            if(!firstLocationFound){
                location1 = positions;
            }
            if(!secondLocationFound){
                location2 = positions;
            }

            // add interval to the existing list
            if(location1 == location2 && location1 % 2 == 0){
                fresh.add(new Long[] {first, second});
                fresh.sort(Comparator.comparing(array -> array[0]));

                for (int j = 0; j < fresh.size() - 1; j++) {
                    if(fresh.get(j)[1].equals(fresh.get(j + 1)[0]) || fresh.get(j)[1] + 1 == fresh.get(j + 1)[0]){
                        fresh.get(j)[1] = fresh.get(j + 1)[1];
                        fresh.remove(j + 1);
                        j--;
                    }
                }
                fresh.sort(Comparator.comparing(array -> array[0]));
                continue;
            }
            if(location1 == location2){
                continue;
            }

            //OVERWRITE
            if(fresh.get(location1 / 2)[0] > first) {
                fresh.get(location1 / 2)[0] = first;
            }

            ArrayList<Integer> toDeleteFromFresh = new ArrayList<>();
            for (int j = location1 / 2; j < location2 / 2; j++) {
                if(j == location1 / 2){
                    continue;
                }
                toDeleteFromFresh.add(j);
            }

            if(location2 % 2 == 0) {
                //OVERWRITE
                fresh.get(location1/2)[1] = second;
            } else if(location2 - location1 > 1){
                fresh.get(location1/2)[1] = fresh.get(location2/2)[1];
                toDeleteFromFresh.add(location2 / 2);
            }

            //handle delete from arraylist in loop carefully:
            for (int j = 0; j < toDeleteFromFresh.size(); j++) {
                fresh.remove((int)(toDeleteFromFresh.get(0)));
            }
            toDeleteFromFresh.clear();

            //delete if intervals just "touch"
            for (int j = 0; j < fresh.size() - 1; j++) {
                if(fresh.get(j)[1].equals(fresh.get(j + 1)[0]) || fresh.get(j)[1] + 1 == fresh.get(j + 1)[0]){
                    fresh.get(j)[1] = fresh.get(j + 1)[1];
                    fresh.remove(j + 1);
                    j--;
                }
            }

            fresh.sort(Comparator.comparing(array -> array[0]));
        }

        for (int i = 0; i < fresh.size(); i++) {
            System.out.println(fresh.get(i)[0] + " " + fresh.get(i)[1]);
        }

        Long result = 0L;
        for (int i = 0; i < fresh.size(); i++) {
            result = result + fresh.get(i)[1] - fresh.get(i)[0] + 1; // add +1 because ranges are inclusive
        }

        System.out.println("part 2 result: " + result);
    }
}