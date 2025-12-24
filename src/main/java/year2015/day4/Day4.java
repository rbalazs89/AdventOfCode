package year2015.day4;

import main.ReadLines;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Day4 {

    private final ReadLines reader = new ReadLines(2015, 4, 2);
    private String input;

    private void readData(){
        input = reader.readFile().getFirst();
    }

    public void part1(){
        readData();
        System.out.println(input);
        String secretKey = input;
        String prefix = "00000";
        int number = 1;

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        while(true){
            String input = secretKey + number;
            md.update(input.getBytes());
            byte[] digest = md.digest();
            String hexHash = HexFormat.of().formatHex(digest);
            if (hexHash.startsWith(prefix)) {
                System.out.println(number);
                break;
            }
            number++;
        }
    }

    public void part2(){
        readData();

        String secretKey = input;
        String prefix = "000000";
        int number = 1;

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        while(true){
            String input = secretKey + number;
            md.update(input.getBytes());
            byte[] digest = md.digest();
            String hexHash = HexFormat.of().formatHex(digest);

            if (hexHash.startsWith(prefix)) {
                System.out.println(number);
                break;
            }

            number++;
        }
    }
}
