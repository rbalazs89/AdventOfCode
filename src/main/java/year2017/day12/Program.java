package year2017.day12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// vertex representing programs
class Program {

    private final int value;
    ArrayList<Program> connected = new ArrayList<>();

    Program(int value){
        this.value = value;
    }

    void addConnection(Program program){
        if(connected.contains(program)){
            return;
        }

        connected.add(program);
    }

    List<Program> getConnected() {
        return Collections.unmodifiableList(connected);
    }

   int getValue() {
        return value;
    }
}
