package year2023.day23;

import java.util.HashSet;
import java.util.Set;

class Path {
    Intersection latestIntersection;
    int currentSteps;
    Set<String> visited = new HashSet<>();
}