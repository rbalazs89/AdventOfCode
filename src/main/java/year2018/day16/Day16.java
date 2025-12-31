package year2018.day16;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 {
    private final ReadLines reader = new ReadLines(2018, 16, 2);
    private final ArrayList<TestValue> testValues = new ArrayList<>();
    private static final Pattern PATTERN = Pattern.compile("\\d+");

    private void prepare(){
        testValues.clear();
        List<String> lines = reader.readFile();

        int firstPart = 0;
        for (int i = 0; i < lines.size(); i++) {
            if(lines.get(i).isEmpty()){
                continue;
            }
            if(lines.get(i).isEmpty() && i + 1 < lines.size()){
                if(lines.get(i + 1).isEmpty()){
                    firstPart = i + 1;
                    break;
                }
            }

            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = i; j < i + 3; j++) {
                Matcher m = PATTERN.matcher(lines.get(j));
                while(m.find()){
                    numbers.add(Integer.parseInt(m.group()));
                }
            }
            int[] before = {numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3)};
            int[] instruction = {numbers.get(4), numbers.get(5), numbers.get(6), numbers.get(7)};
            int[] after = {numbers.get(8), numbers.get(9), numbers.get(10), numbers.get(11)};

            testValues.add(new TestValue(before,instruction,after));
        }
    }

    public void part1(){
        prepare();

        for (int i = 0; i < testValues.size(); i++) {
            processTestValue(testValues.get(i));
        }
    }

    public void part2(){

    }

    private void processTestValue(TestValue t){
        // addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr

        // addr: stores into register C the result of adding register A and register B.
        int add1 = t.getBefore()[t.getInstruction()[1]];
    }
}
