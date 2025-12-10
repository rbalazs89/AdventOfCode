package year2015.day4;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Day4 {

    String input = "bgvyzdsv";

    public void part1(){
        String secretKey = input;
        String prefix = "00000";
        int number = 1;

        MessageDigest md = null;
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
                System.out.println("part 1 result: " + number);
                break;
            }

            number++;
        }
    }

    public void part2(){

        String secretKey = input;
        String prefix = "000000";
        int number = 1;

        MessageDigest md = null;
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
                System.out.println("part 2 result: " + number);
                break;
            }

            number++;
        }
    }
}
