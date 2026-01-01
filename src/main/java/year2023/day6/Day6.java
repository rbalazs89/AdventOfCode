package year2023.day6;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {
    private final ReadLines reader = new ReadLines(2023, 6, 2);
    private final ArrayList<Integer> timeValues = new ArrayList<>();
    private final ArrayList<Integer> distanceValues = new ArrayList<>();

    private void prepare(){
        timeValues.clear();
        distanceValues.clear();

        List<String> lines = reader.readFile();
        Pattern pattern = Pattern.compile("\\d+");
        for (int i = 0; i < lines.size(); i ++) {
            Matcher m = pattern.matcher(lines.get(i));
            while (m.find()){
                if(i == 0){
                    timeValues.add(Integer.parseInt(m.group()));
                } else if (i == 1){
                    distanceValues.add(Integer.parseInt(m.group()));
                }
            }
        }
    }

    public void part1(){
        prepare();
        ArrayList<Integer> solutionCounter = new ArrayList<>();
        for(int i = 0; i < timeValues.size(); i ++){
            int tempCounter = 0;
            for(int j = 0; j < timeValues.get(i); j ++){
                if((timeValues.get(i) - j) * j > distanceValues.get(i)){
                    tempCounter++;
                }
            }
            solutionCounter.add(tempCounter);
        }
        int solution = 1;

        for(int i = 0; i < solutionCounter.size(); i ++){
            solution = solution * solutionCounter.get(i);
        }

        System.out.println(solution);
    }

    public void part2(){
        prepare();
        int solution = 0;
        ArrayList<Long> timeValues = new ArrayList<>(List.of(48938466L));
        ArrayList<Long> distanceValues = new ArrayList<>(List.of(261119210191063L));
        for(int i = 0; i < timeValues.size(); i ++){
            for(int j = 0; j < timeValues.get(i); j ++){
                if((timeValues.get(i) - j) * j > distanceValues.get(i)){
                    solution++;
                }
            }
        }

        System.out.println(solution);
    }
}