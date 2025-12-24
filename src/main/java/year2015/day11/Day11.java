package year2015.day11;

import main.ReadLines;

import java.util.List;

public class Day11 {

    private List<String> fileLines;
    private char[] part1result;

    private final ReadLines reader = new ReadLines(2015, 11, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();
        // 0 -> 26
        // a = 97 | z = 122
        // i = 105 | l = 108 | o = 111

        char[] pw = new char[8];
        for (int i = 0; i < pw.length; i++) {
            pw[i] = fileLines.getFirst().charAt(i);
        }

        while(true){
            pw = incrementPW(pw);
            if(checkFirstRule(pw) && checkSecondRule(pw) && checkThirdRule(pw)){
                for (char c : pw) {
                    System.out.print(((char) c));
                }
                System.out.println();
                part1result = pw;
                break;
            }
        }
    }

    public void part2(){
        readData();
        // 0 -> 26
        // a = 97 | z = 122
        // i = 105 | l = 108 | o = 111

        char[] pw = part1result;

        while(true){
            pw = incrementPW(pw);
            if(checkFirstRule(pw) && checkSecondRule(pw) && checkThirdRule(pw)){
                for (char c : pw) {
                    System.out.print(((char) c));
                }
                break;
            }
        }
    }

    public char addOne(char c){
        if (c == 122){
            return 97;
        } else {
            return (char)(c + 1);
        }
    }

    public char[] incrementPW(char[] c){
        c[c.length - 1] = addOne(c[c.length - 1]);
        for (int i = c.length - 1; i > 0; i--) {
            if(c[i] == 97){
                c[i - 1] = addOne(c[i - 1]);
            } else {
                break;
            }
        }
        return c;
    }

    /**
     * DEBUG METHOD
     */
    public void printPW(char[] c){
        for (char value : c) {
            System.out.print((char) value);
        }
        System.out.println();
    }

    public boolean checkFirstRule(char[] pw){
        for (int i = 0; i < pw.length - 2; i++) {
            if(pw[i] + 2 == pw[i + 1] + 1 && pw[i + 1] + 1 == pw[i + 2]){
                return true;
            }
        }
        return false;
    }

    public boolean checkSecondRule(char[] pw){
        for (char c : pw) {
            if (c == 105 || c == 108 || c == 111) {
                return false;
            }
        }
        return true;
    }

    public boolean checkThirdRule(char[] pw){
        int location = -1;
        int first = -1;

        for (int i = 0; i < pw.length - 3; i++) {
            if(pw[i] == pw[i + 1]){
                location = i;
                first = pw[i];
                break;
            }
        }

        if(location == -1){
            return false;
        } else {
            for (int i = location + 2; i < pw.length - 1; i++) {
                if(pw[i] == pw[i + 1] && pw[i] != first){
                    return true;
                }
            }
        }
        return false;
    }
}
