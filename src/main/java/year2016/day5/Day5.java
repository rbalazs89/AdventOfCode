package year2016.day5;

import main.ReadLines;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;

public class Day5 {
    private final ReadLines reader = new ReadLines(2016, 5, 2);
    private String input;

    private void readData(){
        input = reader.readFile().getFirst();
    }

    public void part1() {
        readData();
        String doorId = input;
        StringBuilder password = new StringBuilder();

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        int index = 0;

        while (password.length() < 8) {
            String s = doorId + index;
            byte[] digest = md.digest(s.getBytes());
            String hash = HexFormat.of().formatHex(digest);

            if (hash.startsWith("00000")) {
                password.append(hash.charAt(5));
            }

            index++;
        }

        System.out.println(password.toString());
    }

    public void part2() {
        readData();
        String secretKey = input;
        String prefix = "00000";

        char[] password = new char[8];
        Arrays.fill(password, '_');
        int filled = 0;
        int number = 0;

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        while (filled < 8) {
            String s = secretKey + number;
            md.update(s.getBytes());
            byte[] digest = md.digest();
            String hexHash = HexFormat.of().formatHex(digest);

            if (hexHash.startsWith(prefix)) {
                char posChar = hexHash.charAt(5);

                if (posChar >= '0' && posChar <= '7') {
                    int pos = posChar - '0';

                    if (password[pos] == '_') {
                        password[pos] = hexHash.charAt(6);
                        filled++;
                    }
                }
            }

            number++;
        }

        System.out.println("part 2 " + new String(password));
    }
}
