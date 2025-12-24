package year2016.day20;

import main.ReadLines;

import java.util.*;

public class Day20 {
    private final ReadLines reader = new ReadLines(2016, 20, 1);
    private List<String> fileLines; // lines from txt file
    private boolean prepared = false;

    private final static long MAXIMUM_IP = 4294967295L; // maximum value we need to investigate from task description
    private long[][] invalidRanges; // filled from input
    ArrayList<long[]> invalidIntervals = new ArrayList<>(); // cleaned up not overlapping ranges

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        if(prepared){
            return;
        }

        readData();
        // first element is the minimum range, second element is the maximum range, both inclusive

        invalidRanges = new long[fileLines.size()][2];
        for (int i = 0; i < fileLines.size(); i++) {
            String[] parts = fileLines.get(i).split( "-");
            invalidRanges[i][0] = Long.parseLong(parts[0]);
            invalidRanges[i][1] = Long.parseLong(parts[1]);
        }

        Arrays.sort(invalidRanges, Comparator.comparingLong(o -> o[0]));
        prepared = true;
    }

    public void part1() {
        prepare();
        makeNonOverlappingInvalidRanges();

        // find first available IP, invalid ranges are inclusive:
        if(invalidIntervals.getFirst()[0] == 0){
            System.out.println(invalidIntervals.getFirst()[1] + 1);
        } else {
            System.out.println(0);
        }
    }

    private void makeNonOverlappingInvalidRanges(){
       invalidIntervals.clear();

       long start = invalidRanges[0][0];
       long end = invalidRanges[0][1];
        if(start > MAXIMUM_IP){
            return;
        }
       invalidIntervals.add(new long[]{start,end});

       for (int i = 1; i < invalidRanges.length; i++) {
            long[] currentOrganized = invalidIntervals.getLast();
            end = currentOrganized[1]; // end of the current intervals

            // safety check, if any intervals are above the range we need to investigate
            if(invalidRanges[i][0] > MAXIMUM_IP){
               break;
            }

            // add without merging
            if(invalidRanges[i][0] > end + 1){
                invalidIntervals.add(new long[]{invalidRanges[i][0], Math.min(invalidRanges[i][1], MAXIMUM_IP)});
                continue;
            }

            // ignore
            if(invalidRanges[i][1] <= end) {
                continue;
            }

            // merge
            if(invalidRanges[i][0] <= end + 1) {
                invalidIntervals.getLast()[1] = Math.min(invalidRanges[i][1], MAXIMUM_IP);
            }
       }
    }

    public void part2(){
        // count valid ranges, all invalid ranges are inclusive on both sides
        prepare();
        makeNonOverlappingInvalidRanges();

        // handle first element:
        long counter = 0;
        if(invalidIntervals.getFirst()[0] != 0){
            counter = invalidIntervals.getFirst()[0];
        }

        int arrayListLength = invalidIntervals.size();
        for (int i = 0; i < arrayListLength - 1; i++) {
            counter += invalidIntervals.get(i + 1)[0] - invalidIntervals.get(i)[1] - 1;
        }

        //handle last element
        if(invalidIntervals.getLast()[1] != MAXIMUM_IP){
            counter += MAXIMUM_IP - invalidIntervals.getLast()[1];
        }

        System.out.println(counter);
    }
}
