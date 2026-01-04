package year2023.day13;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day13 {
    private final ReadLines reader = new ReadLines(2023, 13, 2);
    private final ArrayList<String> reflection = new ArrayList<>();
    private final ArrayList<ArrayList<String>> reflections = new ArrayList<>();
    private final ArrayList<Block> blocks = new ArrayList<>();

    private void prepare(){
        List<String> input = reader.readFile();
        reflection.clear();
        reflections.clear();
        blocks.clear();

        for (String line : input) {
            if (line.isEmpty()) {
                reflections.add(new ArrayList<>(reflection));
                reflection.clear();
            } else {
                reflection.add(line);
            }
        }

        if (!reflection.isEmpty()) {
            reflections.add(new ArrayList<>(reflection));
        }
    }

    public void part1(){
        prepare();

        ArrayList<Integer> horizontals = new ArrayList<>();
        ArrayList<Integer> verticals = new ArrayList<>();

        //check horizontal reflection:
        for (int k = 0; k < reflections.size(); k++) {
            for (int i = 1; i < reflections.get(k).size(); i++) {
                int loops = Math.min(i,reflections.get(k).size() - i);
                int tempInt = 0;
                for (int j = 0; j < loops; j++) { //assume that the i-th line is the reflection and check if true
                    if(reflections.get(k).get(i-j-1).equals(reflections.get(k).get(i+j))){
                        tempInt++;
                    }
                }
                if (tempInt == loops && loops != 0){
                    horizontals.add(i);
                    break;
                }
            }
        }

        // check vertical reflection:
        // first rearrange blocks:
        ArrayList<ArrayList<String>> reversedReflections = getArrayLists();

        //calculation with transposed blocks:
        for (int k = 0; k < reversedReflections.size(); k++) {
            for (int i = 1; i < reversedReflections.get(k).size(); i++) {
                int loops = Math.min(i,reversedReflections.get(k).size() - i);
                int tempInt = 0;
                for (int j = 0; j < loops; j++) { //assume that the i-th line is the reflection and check if true
                    if(reversedReflections.get(k).get(i-j-1).equals(reversedReflections.get(k).get(i+j))){
                        tempInt++;
                    }
                }
                if (tempInt == loops && loops != 0){
                    verticals.add(i);
                    break;
                }
            }
        }

        int solution = 0;
        for (int i = 0; i < horizontals.size(); i++) {
            solution += horizontals.get(i) * 100;
        }

        for (int i = 0; i < verticals.size(); i++) {
            solution += verticals.get(i);
        }

        System.out.println(solution);
    }

    private ArrayList<ArrayList<String>> getArrayLists() {
        ArrayList<ArrayList<String>> reversedReflections = new ArrayList<>();

        for (ArrayList<String> block : reflections) {
            int blockSize = block.size();
            int lineLength = block.getFirst().length();

            ArrayList<String> transposedBlock = new ArrayList<>();
            for (int i = 0; i < lineLength; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < blockSize; j++) {
                    sb.append(block.get(j).charAt(i));
                }
                transposedBlock.add(sb.toString());
            }

            reversedReflections.add(transposedBlock);
        }
        return reversedReflections;
    }

    public void part2(){
        for (int i = 0; i < reflections.size(); i++) {
            Block block = new Block();
            block.content = reflections.get(i);
            blocks.add(block);
        }

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).Vertical();
        }
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).Horizontal();
        }

        ArrayList<Integer> horizontals = new ArrayList<>();
        ArrayList<Integer> verticals = new ArrayList<>();

        for (int i = 0; i < blocks.size(); i++) {
            horizontals.add(blocks.get(i).CheckSmudgeHorizontal());
        }

        for (int i = 0; i < blocks.size(); i++) {
            verticals.add(blocks.get(i).CheckSmudgeVertical());
        }

        int solution = 0;
        for (int i = 0; i < horizontals.size(); i++) {
            solution += horizontals.get(i) * 100;
        }
        for (int i = 0; i < verticals.size(); i++) {
            solution += verticals.get(i);
        }

        System.out.println(solution);
    }
}