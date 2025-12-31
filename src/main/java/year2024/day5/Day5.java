package year2024.day5;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 {
    private final ReadLines reader = new ReadLines(2024, 5, 2);
    private final ArrayList<int[]> rules = new ArrayList<>();
    private final ArrayList<int[]> updates = new ArrayList<>();

    private void prepare(){
        List<String> lines = reader.readFile();
        rules.clear();
        updates.clear();

        int counter = 0;
        for (int i = 0; i < lines.size(); i++) {
            counter++;
            if (lines.get(i).isEmpty()) {
                break;
            }
        }
        for (int i = 0; i < counter - 1; i++) {
            int[] rule = {Integer.parseInt(lines.get(i).substring(0, 2)), Integer.parseInt(lines.get(i).substring(3, 5))};
            rules.add(rule);
        }

        for (int i = counter; i < lines.size(); i++) {
            // Pattern to match numbers
            String oneLine = lines.get(i);
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(oneLine);
            ArrayList<Integer> numbersList = new ArrayList<>();

            while (matcher.find()) {
                numbersList.add(Integer.parseInt(matcher.group()));
            }
            int[] numbersArray = new int[numbersList.size()];
            for (int j = 0; j < numbersList.size(); j++) {
                numbersArray[j] = numbersList.get(j);
            }
            updates.add(numbersArray);
        }
    }

    public void part1(){
        prepare();
        int result = 0;

        for (int i = 0; i < updates.size(); i++) {
            int[] current = updates.get(i);
            int counter = 0;

            for (int j = 0; j < rules.size(); j++) {
                boolean isOK = isIsOK(current, j);
                if(isOK){
                    counter ++;
                }
            }

            if(counter == rules.size()){
                result = result + current[current.length / 2];
            }
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();

        //get good solutions:
        Set<Integer> goodSolutions = new HashSet<>();
        for (int i = 0; i < updates.size(); i++) {
            int[] current = updates.get(i);
            int counter = 0;

            for (int j = 0; j < rules.size(); j++) {
                boolean isOK = isIsOK(current, j);
                if(isOK){
                    counter ++;
                }
            }

            if(counter == rules.size()){
                goodSolutions.add(i);
            }
        }

        int result = 0;
        // only good solutions:
        for (int i = 0; i < updates.size(); i++) {

            //get the only applicable rules for good solutions:
            if(goodSolutions.contains(i)){
                continue;
            }

            ArrayList<int[]> onlyApplicableRules = new ArrayList<>();
            for (int j = 0; j < rules.size(); j++) {
                if(containsBoth(rules.get(j), updates.get(i))){
                    onlyApplicableRules.add(rules.get(j));
                }
            }

            ArrayList<Integer> numbers = new ArrayList<>();
            for(int j = 0; j < onlyApplicableRules.size(); j ++){
                if(!numbers.contains(onlyApplicableRules.get(j)[0])) {
                    numbers.add(onlyApplicableRules.get(j)[0]);
                }
                if(!numbers.contains(onlyApplicableRules.get(j)[1])) {
                    numbers.add(onlyApplicableRules.get(j)[1]);
                }
            }

            // make numbers based on applicable rules only
            Queue<Integer> pq = new PriorityQueue<>();
            ArrayList<int[]> copiedRules = new ArrayList<>();
            for (int j = 0; j < onlyApplicableRules.size(); j++) {
                copiedRules.add(onlyApplicableRules.get(j).clone());
            }

            int[][] helperVariable = new int[numbers.size()][2];
            for (int l = 0; l < numbers.size(); l++) {
                pq.add(numbers.get(l));
                int counter = 0;
                while(!pq.isEmpty()){
                    boolean found = false;
                    for(int j = 0; j < onlyApplicableRules.size(); j ++){
                        if(onlyApplicableRules.get(j)[1] == pq.peek()){
                            pq.add(onlyApplicableRules.get(j)[0]);
                            onlyApplicableRules.remove(j);
                            j --;
                            counter ++;
                            found = true;
                        }
                    }
                    if (!found){
                        pq.remove();
                    }
                }

                onlyApplicableRules = new ArrayList<>();
                for (int j = 0; j < copiedRules.size(); j++) {
                    onlyApplicableRules.add(copiedRules.get(j).clone());
                }
                helperVariable[l][0] = numbers.get(l);
                helperVariable[l][1] = counter;
            }

            Arrays.sort(helperVariable, Comparator.comparingInt(a -> a[1]));

            ArrayList<Integer> helperList = new ArrayList<>();
            for (int[] ints : helperVariable) {
                for (int k = 0; k < updates.get(i).length; k++) {
                    if (updates.get(i)[k] == ints[0]) {
                        helperList.add(updates.get(i)[k]);
                    }
                }
            }
            result = result + helperList.get(updates.get(i).length / 2);

            //end of i
        }
        System.out.println(result);
    }

    private boolean isIsOK(int[] current, int j) {
        boolean isOK = true;
        int index1 = - 1;
        int index2 = - 1;
        for (int k = 0; k < current.length; k++) {
            if(current[k] == rules.get(j)[0]){
                index1 = k;
            }
            if(current[k] == rules.get(j)[1]){
                index2 = k;
            }
        }
        if(index1 != -1 && index2 != -1){
            if(index1 > index2){
                isOK = false;
            }
        }
        return isOK;
    }

    private static boolean containsBoth(int[] rulesArray1, int[] updatesArray){
        boolean contains1 = false;
        boolean contains2 = false;

        for (int j : updatesArray) {
            if (j == rulesArray1[0]) {
                contains1 = true;
            } else if (j == rulesArray1[1]) {
                contains2 = true;
            }
        }
        return contains2 && contains1;
    }
}