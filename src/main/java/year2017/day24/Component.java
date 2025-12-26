package year2017.day24;

import java.util.Collections;

class Component {
    private final int portNumber1;
    private final int portNumber2;
    private final int uniqueID;

    public Component(int portNumber1, int portNumber2, int uniqueID) {
        this.portNumber1 = portNumber1;
        this.portNumber2 = portNumber2;
        this.uniqueID = uniqueID;
    }

    int getUniqueID(){
        return uniqueID;
    }

    int getPortNumber1(){
        return portNumber1;
    }

    int getPortNumber2() {
        return portNumber2;
    }
}
