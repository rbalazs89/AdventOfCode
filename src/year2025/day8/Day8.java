package year2025.day8;

import main.ReadLines;

import java.util.*;

public class Day8 {
    // 1377623342 too low

    List<String> fileLines;
    int inputFileIndex = 2;
    int boxesToConnect = 1000; // 10 for sample input, 1000 for real input
    long part2X1 = 0;
    long part2X2 = 0;

    Box[] boxes;
    ArrayList<HashSet<Integer>> circuits = new ArrayList<>();

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }

    public void processInput(){
        readData();
        boxes = new Box[fileLines.size()];
        for (int i = 0; i < fileLines.size(); i++) {
            Box box = new Box();
            String[] line = fileLines.get(i).split(",");
            box.x = Integer.parseInt(line[0]);
            box.y = Integer.parseInt(line[1]);
            box.z = Integer.parseInt(line[2]);
            boxes[i] = box;
        }
    }

    boolean connectBox(long[] d){
        part2X1 = boxes[(int)d[0]].x;
        part2X2 = boxes[(int)d[1]].x;
        for (int i = 0; i < circuits.size(); i++) {
            if(circuits.get(i).contains((int)(d[0]))){
                if(!circuits.get(i).contains((int)d[1])){
                    // box0 and box1 are not the part of the same circuit
                    // check if box1 is part of any circuit
                    // // if yes -> the circuits, if no -> just add
                    for (int j = 0; j < circuits.size(); j++) {
                        if(circuits.get(j).contains((int)d[1])){
                          HashSet<Integer> MergeThis = circuits.get(j);
                            HashSet<Integer> mergeInto = circuits.get(i);
                            for (Integer element : MergeThis) {
                                mergeInto.add(element);
                            }
                            circuits.remove(j);

                            return true;
                        }
                    }
                    // not part of same circuit -> just add to circuit
                    circuits.get(i).add((int)(d[1]));
                    return true;
                } else {
                    return true;
                    // boxes are already in the same circuit, just skip
                }
            }
        }
        // box 0 is not party of any circuit, but box1 could be:
        for (int i = 0; i < circuits.size(); i++) {
            if(circuits.get(i).contains((int)d[1])){
                circuits.get(i).add((int)d[0]);
                return true;
            }
        }

        // neither of the boxes are part of any circuit:
        HashSet<Integer> set = new HashSet<>();
        set.add((int)d[0]);
        set.add((int)d[1]);
        circuits.add(set);
        return true;
    }

    public void part1(){
        processInput();
        PriorityQueue<long[]> distances = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] a, long[] b) {
                return Long.compare(a[2], b[2]);  // sort by the 3rd element
            }
        });

        // get distances in boxes collection
        for (int i = 0; i < boxes.length; i++) {
            for (int j = i + 1; j < boxes.length; j++) {
                Box b = boxes[i];
                Box b2 = boxes[j];

                long[] d = {i, j, (b.squaredDistance(b2))};
                distances.add(d);
            }
        }

        // go until required connections made
        int counter = 0;
        while(counter < boxesToConnect){
            connectBox(distances.poll());
            counter++;
        }
        getResults();
    }

    public void getResults() {
        // sort circuits by size in descending order
        circuits.sort((a, b) -> Integer.compare(b.size(), a.size()));

        // multiply the sizes of the 3 LARGEST circuits
        long result = 1;
        for (int i = 0; i < Math.min(3, circuits.size()); i++) {
            result *= circuits.get(i).size();
        }

        System.out.println(result);
    }

    public void part2(){
        circuits.clear();
        circuits = new ArrayList<>();
        processInput();
        PriorityQueue<long[]> distances = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] a, long[] b) {
                return Long.compare(a[2], b[2]);  // sort by the 3rd element
            }
        });

        // get distances in boxes collection
        for (int i = 0; i < boxes.length; i++) {
            for (int j = i + 1; j < boxes.length; j++) {
                Box b = boxes[i];
                Box b2 = boxes[j];

                long[] d = {i, j, (b.squaredDistance(b2))};
                distances.add(d);
            }
        }

        // go until required connections made
        int counter = 0;
        while(counter < boxesToConnect){
            connectBox(distances.poll());
            counter++;
        }

        while(circuits.get(0).size() < 1000){
            connectBox(distances.poll());
        }
        getResults2();
    }

    public void getResults2() {
        System.out.println(part2X1 * part2X2);
    }
}
