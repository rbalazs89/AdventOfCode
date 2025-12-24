package year2017.day15;

import main.ReadLines;

import java.util.List;

public class Day15 {
    private final ReadLines reader = new ReadLines(2017, 15, 2);
    private List<String> fileLines; // Lines from txt file

    // Puzzle values given by task description:
    private static final int genAFactor = 16807;
    private static final int genBFactor = 48271;
    private static final int divider = 2147483647;
    private static final long calculations = 40000000;
    private static final int digitsToInvestigate = 16;
    private static final int calculationsPart2 = 5000000;

    // field variable
    private long genA;
    private long genB;
    private boolean booleanA = false;
    private boolean booleanB = false;
    private int globalCounter;

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        String[] parts = fileLines.get(0).split(" ");
        genA = Long.parseLong(parts[parts.length - 1]);

        parts = fileLines.get(1).split(" ");
        genB = Long.parseLong(parts[parts.length - 1]);
    }

    public void part1(){
        prepare();

        int result = 0;
        for (int i = 0; i < calculations; i++) {
            // One step calculation for both generators:
            genA = (genA * genAFactor) % divider;
            genB = (genB * genBFactor) % divider;

            // Make sure its 16 length string:
            String A = fixString(genA);
            String B = fixString(genB);

            // Increment result if strings are matching
            if(A.equals(B)){
                result ++;
            }
        }
        System.out.println(result);
    }


    public void part2(){
        prepare();

        int result = 0;

        while (globalCounter < calculationsPart2) {
            // stops if result is available, updates class variable
            getA();
            getB();

            // result only compared if counter is still within the limit, the last result we get from A and B could be wrong
            if(!booleanA && !booleanB){
                // global counter only increment when both generators supply a number
                globalCounter ++;

                // compares class variables
                String A = fixString(genA);
                String B = fixString(genB);

                if(A.equals(B)){
                    result ++;
                }
            }
        }
        System.out.println(result);
    }

    // generate next number for A
    private void getA(){
        while (globalCounter < calculationsPart2){
            genA = (genA * genAFactor) % divider;
            if(genA % 4 == 0){
                return;
            }
        }
        booleanA = true;
    }

    // generate next number for B
    private void getB(){
        while (globalCounter < calculationsPart2){
            genB = (genB * genBFactor) % divider;
            if(genB % 8 == 0){
                return;
            }
        }
        booleanB = true;
    }

    // Make 16 digit string, prepend 0s
    private static String fixString(Long bigNumber){
        String s = Long.toBinaryString(bigNumber);
        if(s.length() >= digitsToInvestigate){
            s = s.substring(s.length() - digitsToInvestigate);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < digitsToInvestigate - s.length() - 1; j++) {
                sb.append("0");
                if(j == digitsToInvestigate - s.length() - 2){
                    sb.append(s);
                }
            }
            s = sb.toString();
        }
        return s;
    }
}
