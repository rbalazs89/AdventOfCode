package year2023.day20;

import java.util.HashMap;

public class Conjunction extends Modules {
    HashMap<Modules, Boolean> memory = new HashMap<>();
    public Conjunction(){
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("&");
        string.append(super.name);
        string.append(" -> ");
        for (int i = 0; i < sendingTo.size(); i++) {
            string.append(sendingTo.get(i).name).append(", ");
        }
        string = new StringBuilder(string.substring(0, string.length() - 2));
        return string.toString();
    }
}