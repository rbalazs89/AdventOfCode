package year2016.day14;

import main.ReadLines;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
    private final ReadLines reader = new ReadLines(2016, 14, 2);
    private String input;

    private final MessageDigest md5;{
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void readData(){
        input = reader.readFile().getFirst();
    }

    private String getHash(String input) {
        md5.update(input.getBytes());
        byte[] digest = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private String makeMD5(String input) {
        byte[] bytes = md5.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private String makeStretchedHash(String input) {
        String hash = makeMD5(input);
        for (int i = 0; i < 2016; i++) {
            hash = makeMD5(hash);
        }
        return hash;
    }

    
    public void part1(){
        readData();

        int keysFound = 0;
        int index = 0;
        List<Integer> keyIndexes = new ArrayList<>();

        // catch any three characters in a row
        Pattern triplePattern = Pattern.compile("(.)\\1\\1");

        Map<Integer, String> myMap = new HashMap<>();

        while (keysFound < 64) {
            String hash;
            if (myMap.containsKey(index)) {
                hash = myMap.get(index);
            } else {
                hash = getHash(input + index);
                myMap.put(index, hash);
            }

            Matcher tripleMatcher = triplePattern.matcher(hash);
            if (tripleMatcher.find()) {
                char repeatedChar = tripleMatcher.group(1).charAt(0);

                // catch five characters of repeatedChar in a row
                String fiveCharPattern = String.format("(%c)\\1\\1\\1\\1", repeatedChar);

                Pattern p = Pattern.compile(fiveCharPattern);

                // Check next 1000 hashes
                boolean foundFive = false;
                for (int i = 1; i <= 1000; i++) {
                    int checkIndex = index + i;
                    String checkHash;

                    if (myMap.containsKey(checkIndex)) {
                        checkHash = myMap.get(checkIndex);
                    } else {
                        checkHash = getHash(input + checkIndex);
                        myMap.put(checkIndex, checkHash);
                    }

                    if (p.matcher(checkHash).find()) {
                        foundFive = true;
                        break;
                    }
                }

                if (foundFive) {
                    keysFound++;
                    keyIndexes.add(index);
                }
            }
            index++;
        }

        System.out.println(keyIndexes.get(63));
    }

    public void part2() {
        readData();

        int keysFound = 0;
        int index = 0;
        List<Integer> keyIndices = new ArrayList<>();
        
        // catch any three characters in a row
        Pattern triplePattern = Pattern.compile("(.)\\1\\1");
        Map<Integer, String> stretchedHash = new HashMap<>();
        while (keysFound < 64) {
            String hash;
            if (stretchedHash.containsKey(index)) {
                hash = stretchedHash.get(index);
            } else {
                hash = makeStretchedHash(input + index);
                stretchedHash.put(index, hash);
            }

            Matcher tripleMatcher = triplePattern.matcher(hash);
            if (tripleMatcher.find()) {
                char repeatedChar = tripleMatcher.group(1).charAt(0);
                String quintuplePattern = String.format("(%c)\\1\\1\\1\\1", repeatedChar);
                Pattern p = Pattern.compile(quintuplePattern);

                // Check next 1000 hashes
                boolean foundFive = false;
                for (int i = 1; i <= 1000; i++) {
                    int checkIndex = index + i;
                    String checkHash;

                    if (stretchedHash.containsKey(checkIndex)) {
                        checkHash = stretchedHash.get(checkIndex);
                    } else {
                        checkHash = makeStretchedHash(input + checkIndex);
                        stretchedHash.put(checkIndex, checkHash);
                    }
                    if (p.matcher(checkHash).find()) {
                        foundFive = true;
                        break;
                    }
                }
                if (foundFive) {
                    keysFound++;
                    keyIndices.add(index);
                }
            }
            index++;
        }
        System.out.println(keyIndices.get(63));
    }
}