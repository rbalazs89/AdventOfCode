package year2017.day1;


import main.ReadLines;
import java.util.List;

public class Day1 {
    private final ReadLines reader = new ReadLines(2017, 1, 2);
    private List<String> fileLines; // lines from txt file
    private String textToInvestigate;

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        textToInvestigate = fileLines.getFirst();
    }

    public void part1(){
        prepare();

        int solution = 0;
        for (int i = 0; i < textToInvestigate.length(); i++) {
            if(i == textToInvestigate.length() - 1){
                if(textToInvestigate.charAt(i) == textToInvestigate.charAt(0)){
                    int digit = textToInvestigate.charAt(i) - '0';
                    solution += digit;
                }
            } else {
                if(textToInvestigate.charAt(i) == textToInvestigate.charAt(i + 1)){
                    int digit = textToInvestigate.charAt(i) - '0';
                    solution += digit;
                }
            }
        }
        System.out.println(solution);
    }

    public void part2(){
        prepare();
        int solution = 0;

        // input has even number of characters according to task description
        for (int i = 0; i < textToInvestigate.length(); i++) {
            int investigate = (i + textToInvestigate.length() / 2) % textToInvestigate.length();
            if (textToInvestigate.charAt(i) == textToInvestigate.charAt(investigate)) {
                int digit = textToInvestigate.charAt(i) - '0';
                solution += digit;
            }
        }
        System.out.println(solution);
    }
}
