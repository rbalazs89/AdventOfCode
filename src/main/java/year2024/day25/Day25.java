package year2024.day25;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day25 {
    private static final ReadLines reader = new ReadLines(2024, 25, 2);
    private final static ArrayList<Lock> locks = new ArrayList<>();
    private final static ArrayList<Key> keys = new ArrayList<>();

    private void prepare(){
        List<String> input = reader.readFile();

        keys.clear();
        locks.clear();

        int counter = 0;
        while(counter < input.size()){
            if(input.get(counter).equals("#####")){
                Lock lock = new Lock();
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 5; j++) {
                        if(input.get(counter + i).charAt(j) == '#'){
                            lock.field[i][j] = 1;
                        } else {
                            lock.field[i][j] = 0;
                        }
                    }
                }
                locks.add(lock);
            } else {
                Key key = new Key();
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 5; j++) {
                        if(input.get(counter + i).charAt(j) == '#'){
                            key.field[i][j] = 1;
                        } else {
                            key.field[i][j] = 0;
                        }
                    }
                }
                keys.add(key);
            }
            counter = counter + 8;
        }
    }

    public void part1(){
        prepare();
        int result = 0;
        for (int i = 0; i < locks.size(); i++) {
            for (int j = 0; j < keys.size(); j++) {
                if(doesKeyFitIntoLock(locks.get(i), keys.get(j))){
                    result ++;
                }
            }
        }
        System.out.println(result);
    }

    public void part2(){
        // no part 2, nothing to do here
    }

    private boolean doesKeyFitIntoLock(Lock lock, Key key){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if(lock.field[i][j] == 1 && key.field[i][j] == 1){
                    return false;
                }
            }
        }
        return true;
    }
}
