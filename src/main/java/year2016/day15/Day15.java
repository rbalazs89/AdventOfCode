package year2016.day15;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 15, 2);
    private boolean prepared = false;

    private final ArrayList<Disc> discs = new ArrayList<>();

    // new disc from task description:
    private final static int part2NewDiscPositions = 11; // added from task description
    private final static int part2NewDiscStartPosition = 0; // added from task description

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        if(prepared){
            return;
        }
        readData();

        // safe to assume input format: Disc #1 has 5 positions; at time=0, it is at position 4.
        Pattern p = Pattern.compile("\\d+"); // capture all positive integers
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);
            ArrayList<Integer> numbers = new ArrayList<>();

            while (m.find()){
                numbers.add(Integer.parseInt(m.group()));
            }

            // from puzzle input:
            // disc number -> numbers.get(0);
            // positions -> numbers.get(1);
            // start position -> numbers.get(3);
            // numbers.get(2) skipped, every data is captured time = 0;
            Disc disc = new Disc(numbers.get(0), numbers.get(1), numbers.get(3));
            discs.add(disc);
        }
        prepared = true;
    }

    public void part1(){
        prepare();

        int time = 0;

        whileLoop:
        while(true){
            time ++;
            for (int i = 0; i < discs.size(); i++) {
                int fallTime = time + i + 1;  // +1 for each level down
                if(getCurrentPosition(fallTime, discs.get(i)) != 0){ // discs are in order from the AoC puzzle input
                    continue whileLoop;
                }
            }
            break;
        }
        System.out.println(time);
    }

    private int getCurrentPosition(int time, Disc disc){
        int start = disc.positionAtStart();
        int numberOfPositions = disc.positions();
        return (start + time) % numberOfPositions;
    }

    public void part2(){
        prepare();
        ArrayList<Disc> part2Discs = new ArrayList<>(discs);
        Disc part2Disc = new Disc(discs.size(), part2NewDiscPositions, part2NewDiscStartPosition);
        part2Discs.add(part2Disc);

        int time = 0;

        while(true){
            time ++;
            boolean aligned = true;
            for (int i = 0; i < part2Discs.size(); i++) {
                int fallTime = time + i + 1;  // +1 for each level down
                if(getCurrentPosition(fallTime, part2Discs.get(i)) != 0){ // discs are in order from the AoC puzzle input
                    aligned = false;
                    break;
                }
            }
            if(aligned){
                break;
            }

        }
        System.out.println(time);
    }
}
