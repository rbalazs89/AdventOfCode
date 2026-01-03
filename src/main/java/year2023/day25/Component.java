package year2023.day25;

import java.util.ArrayList;
import java.util.List;

class Component {
    String name;
    List<Wire> wires = new ArrayList<>();

    Component(String name){
        this.name = name;
    }

    void doubleConnect(Component other){
        for (int i = 0; i < wires.size(); i++) {
            Wire w = wires.get(i);
            if((w.component1.name.equals(name) && w.component2.name.equals(other.name))
                    ||(w.component2.name.equals(name) && w.component1.name.equals(other.name))){
                return;
            }
        }

        // create wire:
        Wire w = new Wire(this, other);
        wires.add(w);
        other.wires.add(w);
    }

    Component getConnectedFromWire(Wire w){
        Component other = null;
        if(w.component1 == this){
            other = w.component2;
        } else if (w.component2 == this){
            other = w.component1;
        }
        return other;
    }

    void deleteWire(Wire wire1, Wire wire2, Wire wire3){
        for (int i = 0; i < wires.size(); i++) {
            Wire w = wires.get(i);
            if(wire1 == w){
                wires.remove(wire1);
                return;
            }
            if(wire2 == w){
                wires.remove(wire2);
                return;
            }
            if(wire3 == w){
                wires.remove(wire3);
                return;
            }
        }
    }
}