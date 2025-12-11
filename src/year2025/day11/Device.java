package year2025.day11;

import java.text.DecimalFormat;
import java.util.ArrayList;

class Device {
    String name;
    long paths = 0;
    int visited = 0;
    ArrayList<Device> in = new ArrayList<>();
    ArrayList<Device> out = new ArrayList<>();
    boolean removeThis = false;
    int delay = 0;
}
