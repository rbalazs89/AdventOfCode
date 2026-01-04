package year2023.day20;

public class FlipFlop extends Modules{
    boolean isOn = false;
    public FlipFlop(){}

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("%");
        string.append(super.name);
        string.append(" -> ");
        for (int i = 0; i < sendingTo.size(); i++) {
            string.append(sendingTo.get(i).name).append(", ");
        }
        string = new StringBuilder(string.substring(0, string.length() - 2));
        return string.toString();
    }
}