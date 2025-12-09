package year2025.day8;

import main.ReadLines;

import java.util.*;

public class Day8 {
    // 1000 too low
    // 16000 too low


    List<String> fileLines;
    int inputFileIndex = 2;
    int boxesToConnect = 1000; // 10 for sample input, 1000 for real input

    Box[] boxes;
    ArrayList<HashSet> circuits = new ArrayList<>();


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

    boolean connectBox(int[] d){
        for (int i = 0; i < circuits.size(); i++) {
            if(circuits.get(i).contains(d[0])){
                if(!circuits.get(i).contains(d[1])){
                    // box1 and box2 are not the part of the same circuit
                    // check if box2 is part of any circuit
                    // // if yes -> the circuits, if no -> just add
                    for (int j = 0; j < circuits.size(); j++) {
                        if(circuits.get(j).contains(d[1])){
                            HashSet<Integer> MergeThis = circuits.get(j);
                            HashSet<Integer> mergeInto = circuits.get(i);
                            for (Integer element : MergeThis) {
                                mergeInto.add(element);
                            }
                            circuits.remove(j);
                            return true;
                        }
                    }
                    // not part of same circuit -> just add circuit
                    circuits.get(i).add((d[1]));
                    return true;
                }
                return true;
                //return false; // boxes are already in the same circuit
            }
        }
        // box 1 is not party of any circuit:
        for (int i = 0; i < circuits.size(); i++) {
            if(circuits.get(i).contains(d[1])){
                circuits.get(i).add(d[0]);
                return true;
            }
        }
        // neither of the boxes are part of any circuit:
        HashSet<Integer> set = new HashSet<>();
        set.add(d[0]);
        set.add(d[1]);
        circuits.add(set);
        return true;
    }

    public void part1(){
        processInput();
        PriorityQueue<int[]> distances = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[2], b[2]);  // sort by the 3rd element
            }
        });

        // get distances in boxes collection
        for (int i = 0; i < boxes.length; i++) {
            for (int j = i; j < boxes.length; j++) {
                if(i == j){
                    continue;
                }
                Box b = boxes[i];
                Box b2 = boxes[j];

                int[] d = {i, j, (int)(b.squaredDistance(b2))};
                distances.add(d);
            }
        }

        // go until required connections made
        int counter = 0;
        while(counter < boxesToConnect){
            if(connectBox(distances.poll())){
                counter++;
            }
        }

        getResults();
    }


    public void getResults() {
        // sort circuits by size in descending order
        circuits.sort((a, b) -> Integer.compare(b.size(), a.size()));

        // multiply the sizes of the THREE LARGEST circuits
        long result = 1;
        for (int i = 0; i < Math.min(3, circuits.size()); i++) {
            result *= circuits.get(i).size();
        }
        System.out.println(result);
    }


    public void part2(){

    }
}
