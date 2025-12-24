package year2016.day10;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    private List<String> fileLines;
    private final ArrayList<BotOrOutput> vertices = new ArrayList<>();
    private final ArrayList<BotOrOutput> bots = new ArrayList<>();
    private final ArrayList<BotOrOutput> outputs = new ArrayList<>();
    private final ArrayList<Integer> chips = new ArrayList<>();
    private final ReadLines reader = new ReadLines(2016, 10, 2);
    private void readData(){
        // READ INPUT
        fileLines = reader.readFile();
    }

    private void processData(){
        readData();
        Pattern pattern = Pattern.compile("\\d+");
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher matcher = pattern.matcher(line);
            ArrayList<Integer> numbers = new ArrayList<>();
            while(matcher.find()){
                numbers.add(Integer.valueOf(matcher.group()));
            }
            //allInputs.add(numbers);
            if(numbers.size() == 2){
                BotOrOutput myBot = findBotByNumber(numbers.get(1));
                addChips(numbers.get(0));
                if(myBot.uncertainValue1 == -1){
                    myBot.uncertainValue1 = numbers.getFirst();
                } else if (myBot.uncertainValue2  == -1){
                    myBot.uncertainValue2 = numbers.getFirst();
                } else {
                    System.out.println("problem");
                }
            } else {
                line = line.substring(3);
                BotOrOutput boo1 = findBotByNumber(numbers.getFirst());

                // 1st output 2nd to bot
                if(line.contains("output") && line.contains("bot") ){

                    BotOrOutput boo2 = findOutputByNumber(numbers.get(1));
                    BotOrOutput boo3 = findBotByNumber(numbers.get(2));
                    boo1.lower = boo2;
                    boo1.higher = boo3;

                } // 1st output 2nd output
                else if (line.contains("output") && !line.contains("bot")){

                    BotOrOutput boo2 = findOutputByNumber(numbers.get(1));
                    BotOrOutput boo3 = findOutputByNumber(numbers.get(2));
                    boo1.lower = boo2;
                    boo1.higher = boo3;

                } // 1st bot 2nd bot
                else if( line.contains("bot") && !line.contains("output")){
                    BotOrOutput boo2 = findBotByNumber(numbers.get(1));
                    BotOrOutput boo3 = findBotByNumber(numbers.get(2));
                    boo1.lower = boo2;
                    boo1.higher = boo3;

                } else {
                    System.out.println("problem");
                }
            }
        }
    }

    public void part1(){
        /*
        after investigating the input data is clear that there shouldn't be circles in the graph
        this means that any each input will eventually end up in a different output, there should be 21 inputs and 21 outputs
         */
        processData();
        chips.sort(Integer::compareTo);

        for (int i = 0; i < chips.size(); i++) {
            Integer myInteger = chips.get(i);
            middleLoop:
            for (int j = 0; j < vertices.size(); j++) {
                if(vertices.get(j).uncertainValue1 == myInteger || vertices.get(j).uncertainValue2 == myInteger){
                    BotOrOutput vertex = vertices.get(j);
                    //vertex = vertex.lower;
                    if(vertex.uncertainValue1 == myInteger){
                        if(vertex.uncertainValue2 > myInteger || vertex.uncertainValue2 == -1){
                            vertex = vertex.lower;
                        } else if(vertex.uncertainValue2 < myInteger){
                            vertex = vertex.higher;
                        } else {
                            System.out.println("shouldn't happen");
                        }
                    } else if (vertex.uncertainValue2 == myInteger){
                        if(vertex.uncertainValue1 > myInteger || vertex.uncertainValue1 == -1){
                            vertex = vertex.lower;
                        } else if(vertex.uncertainValue1 < myInteger){
                            vertex = vertex.higher;
                        } else {
                            System.out.println("shouldn't happen 2");
                        }
                    } else {
                        System.out.println("shouldn't happen 3");
                    }

                    int counter = 0;
                    while(true){
                        counter ++;
                        if(vertex.uncertainValue1 != -1 && vertex.uncertainValue2 != -1){
                            System.out.println("shouldn't be printed");
                            printVertex(vertex);
                            break middleLoop;
                        }
                        if(!vertex.isBot){
                            vertex.uncertainValue1 = myInteger;
                            break middleLoop;
                        }
                        if(vertex.uncertainValue1 == -1){
                            vertex.uncertainValue1 = myInteger;
                            if(vertex.uncertainValue2 > myInteger || vertex.uncertainValue2  == -1){
                                vertex = vertex.lower;
                            } else if (vertex.uncertainValue2 < myInteger){
                                vertex = vertex.higher;
                            }

                        }
                        // always true but left here for semantic clarity:
                        else if (vertex.uncertainValue2 == -1){
                            vertex.uncertainValue2 = myInteger;
                            if(vertex.uncertainValue1 > myInteger || vertex.uncertainValue1 == -1){
                                vertex = vertex.lower;
                            } else if (vertex.uncertainValue1 < myInteger){
                                vertex = vertex.higher;
                            }
                        }
                        if(counter > 1000){
                            System.out.println("circle in graph?");
                            break;
                        }
                    }
                }
            }

        }
        for (int i = 0; i < bots.size(); i++) {
            BotOrOutput current = vertices.get(i);
            if((current.uncertainValue1 == 61 && current.uncertainValue2 == 17) || (current.uncertainValue1 == 17 && current.uncertainValue2 == 61)){
                System.out.println(current.name);
            }
        }
    }

    private void addChips(Integer number){
        for (int i = 0; i < chips.size(); i++) {
            if(chips.get(i).equals(number)){
                return;
            }
        }
        chips.add(number);
    }

    private BotOrOutput findOutputByNumber(int number){
        for (int i = 0; i < outputs.size() ; i++) {
            BotOrOutput current = outputs.get(i);
            if(current.name == number){
                return current;
            }
        }
        BotOrOutput vertex = new BotOrOutput();
        vertex.name = number;
        vertex.isBot = false;
        outputs.add(vertex);
        vertices.add(vertex);
        return vertex;
    }

    private BotOrOutput findBotByNumber(int number){
        for (int i = 0; i < bots.size() ; i++) {
            BotOrOutput current = bots.get(i);
            if(current.name == number){
                return current;
            }
        }

        BotOrOutput vertex = new BotOrOutput();
        vertex.name = number;
        vertex.isBot = true;
        bots.add(vertex);
        vertices.add(vertex);
        return vertex;
    }

    public void part2(){
        int result = 1;
        for (int i = 0; i < outputs.size(); i++) {
            if(outputs.get(i).name == 0 || outputs.get(i).name == 1 || outputs.get(i).name == 2){
                result = result * outputs.get(i).uncertainValue1;
            }
        }
        System.out.println(result);
    }


    /**
     * DEBUG METHOD
     * prints vertex status
     */
    private void printVertex(BotOrOutput vertex){
        String isThisABot;
        if(vertex.isBot){
            isThisABot = "bot";
        } else {
            isThisABot = "output";
        }
        System.out.println(isThisABot + vertex.name + " " + vertex.uncertainValue1 + " " + vertex.uncertainValue2);
    }
}
