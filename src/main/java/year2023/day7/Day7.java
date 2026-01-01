package year2023.day7;

import main.ReadLines;

import java.util.*;

public class Day7 {
    private final ReadLines reader = new ReadLines(2023, 7, 2);

    public void part1(){
        List<String> input = reader.readFile();

        //process txt file:
        List<String> cards = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        for(int i = 0; i < input.size(); i ++){
            cards.add(input.get(i).substring(0,5));
            points.add(Integer.valueOf(input.get(i).substring(6)));
        }

        //hashMap with values:
        List<String> values = new ArrayList<>(Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"));
        HashMap<String, Integer> cardValues = new HashMap<>();
        for(int i = 0; i < values.size(); i++){
            cardValues.put(values.get(i),values.size() - i);
        }
        values.clear();

        // Compare two hands
        //Five of a kind  -> 7
        //Four of a kind -> 6
        //Full house -> 5
        //Three of a kind -> 4
        //Two pair -> 3
        //One pair -> 2
        //High card -> 1

        for (int n = 0; n < input.size(); n++) {
            for(int k = 0; k < input.size() - 1; k ++){
                boolean switchOrder = false;
                int hand1value;
                int hand2value;
                ArrayList<Integer> hand1 = new ArrayList<>();
                HashMap<String, Integer> hand1Freq = new HashMap<>();
                ArrayList<Integer> hand2 = new ArrayList<>();
                HashMap<String, Integer> hand2Freq = new HashMap<>();
                for(int i = 0; i < cards.getFirst().length(); i ++){ // all hands same length

                    if(hand1Freq.containsKey(cards.get(k).substring(i,i+1))){
                        hand1Freq.put(cards.get(k).substring(i,i+1), hand1Freq.get(cards.get(k).substring(i,i+1)) + 1);
                    }else {
                        hand1Freq.put(cards.get(k).substring(i,i+1), 1);
                    }

                    if(hand2Freq.containsKey(cards.get(k + 1).substring(i,i+1))){
                        hand2Freq.put(cards.get(k + 1).substring(i,i+1), hand2Freq.get(cards.get(k + 1).substring(i,i+1)) + 1);
                    }else {
                        hand2Freq.put(cards.get(k + 1).substring(i,i+1), 1);
                    }

                }
                for (Map.Entry<String, Integer> entry : hand1Freq.entrySet()){
                    hand1.add(entry.getValue());
                }
                for (Map.Entry<String, Integer> entry : hand2Freq.entrySet()){
                    hand2.add(entry.getValue());
                }

                hand1.sort(Collections.reverseOrder());
                hand2.sort(Collections.reverseOrder());

                if(hand1.get(0) == 5){//Five of a kind
                    hand1value = 7;
                } else if(hand1.get(0) == 4){//Four of a kind
                    hand1value = 6;
                } else if(hand1.get(0) == 3 && hand1.get(1) == 2){//Full house
                    hand1value = 5;
                } else if (hand1.get(0) == 3 && hand1.get(1) != 2){//Three of a kind
                    hand1value = 4;
                } else if (hand1.get(0).equals(hand1.get(1)) && hand1.get(0) == 2){//Two pair
                    hand1value = 3;
                } else if (hand1.get(0) == 2){//One pair
                    hand1value = 2;
                } else {
                    hand1value = 1;
                }

                if(hand2.get(0) == 5){
                    hand2value = 7;
                } else if(hand2.get(0) == 4){
                    hand2value = 6;
                } else if(hand2.get(0) == 3 && hand2.get(1) == 2){
                    hand2value = 5;
                } else if (hand2.get(0) == 3 && hand2.get(1) != 2){
                    hand2value = 4;
                } else if (hand2.get(0).equals(hand2.get(1)) && hand2.get(0) == 2){
                    hand2value = 3;
                } else if (hand2.get(0) == 2){
                    hand2value = 2;
                } else {
                    hand2value = 1;
                }

                if(hand2value > hand1value){
                    switchOrder = true;
                }

                if(hand1value == hand2value){
                    for(int i = 0; i < cards.getFirst().length(); i ++){
                        if(cardValues.get(cards.get(k + 1).substring(i,i+1)) > cardValues.get(cards.get(k).substring(i,i+1))){
                            switchOrder = true;
                            break;
                        }
                        if(!(cardValues.get(cards.get(k + 1).substring(i, i + 1)).equals(cardValues.get(cards.get(k).substring(i, i + 1))))){
                            break;
                        }
                    }
                }

                if(switchOrder){
                    String tempString = cards.get(k);
                    cards.set(k, cards.get(k+1));
                    cards.set(k+1, tempString);

                    int tempInt = points.get(k);
                    points.set(k, points.get(k+1));
                    points.set(k+1, tempInt);
                }
            }
        }

        //determine final result after order is correct:
        long solution = 0L;
        for(int i = 0; i < input.size(); i ++){
            solution = solution + (long) points.get(i) * (input.size() - i);
        }

        System.out.println(solution);
    }

    public void part2(){
        List<String> input = reader.readFile();

        //process txt file:
        List<String> cards = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        for(int i = 0; i < input.size(); i ++){
            cards.add(input.get(i).substring(0,5));
            points.add(Integer.valueOf(input.get(i).substring(6)));
        }

        //hashMap with values:
        List<String> values = new ArrayList<>(Arrays.asList("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J"));
        HashMap<String, Integer> cardValues = new HashMap<>();
        for(int i = 0; i < values.size(); i++){
            cardValues.put(values.get(i),values.size() - i);
        }
        values.clear();

        // Compare two hands
        //Five of a kind  -> 7
        //Four of a kind -> 6
        //Full house -> 5
        //Three of a kind -> 4
        //Two pair -> 3
        //One pair -> 2
        //High card -> 1

        for (int n = 0; n < input.size(); n++) {
            for(int k = 0; k < input.size() - 1; k ++){
                boolean switchOrder = false;
                int hand1value = 0;
                int hand2value = 0;
                String hand1NoJ = cards.get(k).replace("J", "");
                String hand2NoJ = cards.get(k + 1).replace("J", "");
                ArrayList<Integer> hand1 = new ArrayList<>();
                HashMap<String, Integer> hand1Freq = new HashMap<>();
                ArrayList<Integer> hand2 = new ArrayList<>();
                HashMap<String, Integer> hand2Freq = new HashMap<>();
                for(int i = 0; i < hand1NoJ.length(); i ++){ // all hands same length

                    if(hand1Freq.containsKey(hand1NoJ.substring(i,i+1))){
                        hand1Freq.put(hand1NoJ.substring(i,i+1), hand1Freq.get(hand1NoJ.substring(i,i+1)) + 1);
                    }else {
                        hand1Freq.put(hand1NoJ.substring(i,i+1), 1);
                    }
                }

                for(int i = 0; i < hand2NoJ.length(); i ++){ // all hands same length

                    if(hand2Freq.containsKey(hand2NoJ.substring(i,i+1))){
                        hand2Freq.put(hand2NoJ.substring(i,i+1), hand2Freq.get(hand2NoJ.substring(i,i+1)) + 1);
                    }else {
                        hand2Freq.put(hand2NoJ.substring(i,i+1), 1);
                    }
                }

                for (Map.Entry<String, Integer> entry : hand1Freq.entrySet()){
                    hand1.add(entry.getValue());
                }
                for (Map.Entry<String, Integer> entry : hand2Freq.entrySet()){
                    hand2.add(entry.getValue());
                }

                hand1.sort(Collections.reverseOrder());
                hand2.sort(Collections.reverseOrder());

                if(hand1NoJ.length() == cards.get(k).length()) {
                    if (hand1.get(0) == 5) {//Five of a kind
                        hand1value = 7;
                    } else if (hand1.get(0) == 4) {//Four of a kind
                        hand1value = 6;
                    } else if (hand1.get(0) == 3 && hand1.get(1) == 2) {//Full house
                        hand1value = 5;
                    } else if (hand1.get(0) == 3 && hand1.get(1) != 2) {//Three of a kind
                        hand1value = 4;
                    } else if (hand1.get(0).equals(hand1.get(1)) && hand1.get(0) == 2) {//Two pair
                        hand1value = 3;
                    } else if (hand1.get(0) == 2) {//One pair
                        hand1value = 2;
                    } else {
                        hand1value = 1;
                    }
                }

                if(!(hand1NoJ.length() == cards.get(k).length())) {
                    if(hand1.isEmpty()){
                        hand1value = 7;
                    }
                    else if(hand1.getFirst() == 1){
                        if(hand1NoJ.length() == 1){
                            hand1value = 7;
                        } else if(hand1NoJ.length() == 2){
                            hand1value = 6;
                        } else if(hand1NoJ.length() == 3){
                            hand1value = 4;
                        } else if(hand1NoJ.length() == 4){
                            hand1value = 2;
                        }
                    } else if(hand1.getFirst() == 2 && hand1.size() == 3){
                        hand1value = 4;
                    } else if(hand1.getFirst() == 2 && hand1.size() == 2 && hand1NoJ.length() == 3){
                        hand1value = 6;
                    } else if(hand1.getFirst() == 2 && hand1.size() == 2 && hand1NoJ.length() == 4) {
                        hand1value = 5;
                    }
                    else if(hand1.getFirst() == 2 && hand1.size() == 1){
                        hand1value = 7;
                    }
                    else if(hand1.getFirst() == 3 && hand1.size() == 1){
                        hand1value = 7;
                    } else if(hand1.getFirst() == 3 && hand1.size() == 2){
                        hand1value = 6;
                    } else if(hand1.getFirst() == 4){
                        hand1value = 7;
                    }
                }

                if(hand2NoJ.length() == cards.get(k + 1).length()) {
                    if (hand2.get(0) == 5) {
                        hand2value = 7;
                    } else if (hand2.get(0) == 4) {
                        hand2value = 6;
                    } else if (hand2.get(0) == 3 && hand2.get(1) == 2) {
                        hand2value = 5;
                    } else if (hand2.get(0) == 3 && hand2.get(1) != 2) {
                        hand2value = 4;
                    } else if (hand2.get(0).equals(hand2.get(1)) && hand2.get(0) == 2) {
                        hand2value = 3;
                    } else if (hand2.get(0) == 2) {
                        hand2value = 2;
                    } else {
                        hand2value = 1;
                    }
                }

                if(!(hand2NoJ.length() == cards.get(k + 1).length())) {
                    if(hand2.isEmpty()){
                        hand2value = 7;
                    }
                    else if(hand2.getFirst() == 1){
                        if(hand2NoJ.length() == 1){
                            hand2value = 7;
                        } else if(hand2NoJ.length() == 2){
                            hand2value = 6;
                        } else if(hand2NoJ.length() == 3){
                            hand2value = 4;
                        } else if(hand2NoJ.length() == 4){
                            hand2value = 2;
                        }
                    } else if(hand2.getFirst() == 2 && hand2.size() == 3){
                        hand2value = 4;
                    } else if(hand2.getFirst() == 2 && hand2.size() == 2 && hand2NoJ.length() == 3){
                        hand2value = 6;
                    } else if(hand2.getFirst() == 2 && hand2.size() == 2 && hand2NoJ.length() == 4) {
                        hand2value = 5;
                    } else if(hand2.getFirst() == 2 && hand2.size() == 1){
                        hand2value = 7;
                    }
                    else if(hand2.getFirst() == 3 && hand2.size() == 1){
                        hand2value = 7;
                    } else if(hand2.getFirst() == 3 && hand2.size() == 2){
                        hand2value = 6;
                    } else if(hand2.getFirst() == 4){
                        hand2value = 7;
                    }
                }

                if(hand2value > hand1value){
                    switchOrder = true;
                }


                if(hand1value == hand2value){
                    for(int i = 0; i < cards.getFirst().length(); i ++){
                        if(cardValues.get(cards.get(k + 1).substring(i,i+1)) > cardValues.get(cards.get(k).substring(i,i+1))){
                            switchOrder = true;
                            break;
                        }
                        if(!(cardValues.get(cards.get(k + 1).substring(i, i + 1)).equals(cardValues.get(cards.get(k).substring(i, i + 1))))){
                            break;
                        }
                    }
                }

                if(switchOrder){
                    String tempString = cards.get(k);
                    cards.set(k, cards.get(k+1));
                    cards.set(k+1, tempString);

                    int tempInt = points.get(k);
                    points.set(k, points.get(k+1));
                    points.set(k+1, tempInt);
                }
            }
        }

        //determine final result after order is correct:
        long solution = 0L;
        for(int i = 0; i < input.size(); i ++){
            solution = solution + (long) points.get(i) * (input.size() - i);
        }

        System.out.println(solution);
    }
}