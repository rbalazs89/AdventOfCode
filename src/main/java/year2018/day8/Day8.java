package year2018.day8;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day8 {
    private final ReadLines reader = new ReadLines(2018, 8, 2);

    private int[] getTree(){
        String line = reader.readFile().getFirst(); // this puzzle has one line input
        String[] parts = line.split(" ");
        int[] tree = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            tree[i] = Integer.parseInt(parts[i]);
        }

        return tree;
    }

    public void part1(){
        int[] tree = getTree();
        Node root = processNode(0, tree);
        System.out.println(root.metadataSum);
    }

    private Node processNode(int pos, int[] tree) {
        int childCount = tree[pos];
        int metaCount = tree[pos + 1];
        pos += 2;

        List<Integer> childValues = new ArrayList<>();
        int totalMetadataSum = 0;

        // Process all children
        for (int i = 0; i < childCount; i++) {
            Node child = processNode(pos, tree);
            pos = child.newPosition;
            totalMetadataSum += child.metadataSum;
            childValues.add(child.value);
        }

        // Read metadata
        int nodeValue = 0;
        for (int i = 0; i < metaCount; i++) {
            int meta = tree[pos];
            totalMetadataSum += meta;

            // if no children, value = sum of metadata
            // if children, value = sum of child values at metadata index
            if (childCount == 0) {
                nodeValue += meta;
            } else if (meta > 0 && meta <= childValues.size()) {
                nodeValue += childValues.get(meta - 1);
            }
            pos++;
        }

        return new Node(totalMetadataSum, nodeValue, pos);
    }

    public void part2(){
        int[] tree = getTree();
        Node root = processNode(0, tree);
        System.out.println(root.value);
    }
}
