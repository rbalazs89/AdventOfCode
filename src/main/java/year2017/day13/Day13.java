package year2017.day13;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {

    // setup:
    private final ReadLines reader = new ReadLines(2017, 13, 2);
    private List<String> fileLines; // lines from txt file

    // puzzle:
    private final HashMap<Integer, Layer> firewall = new HashMap<>();

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();

        // note for this puzzle: all numbers are below 100
        firewall.clear();
        Pattern p = Pattern.compile("\\d+");
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);
            ArrayList<Integer> numbers = new ArrayList<>();

            while (m.find()){
                numbers.add(Integer.parseInt(m.group()));
            }

            firewall.put(numbers.get(0), new Layer(numbers.get(0), numbers.get(1)));
        }
    }

    public void part1() {
        prepare();

        // find max depth to use it as maximum range for loop:
        int maxDepth = 0;
        for(Map.Entry<Integer, Layer> entry : firewall.entrySet()){
            maxDepth = Math.max(maxDepth, entry.getValue().getDepth());
        }

        int severity = 0;

        for (int depth = 0; depth <= maxDepth; depth++) {

            // detect if there is a layer on the current depth
            // ignore depths where there is no layer
            Layer currentLayer = firewall.get(depth);

            if(currentLayer == null){
                continue;
            }

            // if scanner is exactly on the 0th position, then detection occurs:
            int cycleLength = currentLayer.getRange() * 2 - 2;
            if((depth) % (cycleLength) == 0){
                // *beep beep* nasty intruder detected
                severity = severity + currentLayer.getDepth() * currentLayer.getRange();
            }
        }
        System.out.println(severity);
    }

    public void part2(){
        prepare();

        // find max depth to use it as maximum range for loop:
        int maxDepth = 0;
        for(Map.Entry<Integer, Layer> entry : firewall.entrySet()){
            maxDepth = Math.max(maxDepth, entry.getValue().getDepth());
        }

        int delay = 0;
        boolean noHitRun;

        while (true){
            noHitRun = true;

            // go through all the depths
            for (int depth = 0; depth <= maxDepth; depth++) {
                // detect if there is a layer on the current depth
                // ignore depths where there is no layer
                Layer currentLayer = firewall.get(depth);

                if(currentLayer == null){
                    continue;
                }

                // if scanner is exactly on the 0th position, then detection occurs:
                int cycleLength = currentLayer.getRange() * 2 - 2;
                if((depth + delay) % (cycleLength) == 0){
                    noHitRun = false; // set flag so while loop doesn't exit
                    break; // but break current for loop, so no further needless investigation
                }
            }
            if(noHitRun){
                break;
            }
            delay ++;
        }
        System.out.println(delay);
    }
}
