package year2023.day19;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    private final ReadLines reader = new ReadLines(2023, 19, 2);
    private final static Workflow A = new Workflow("A"); // represents accepted
    private final static Workflow R = new Workflow("R"); // represents rejected

    private final List<Workflow> workflows = new ArrayList<>();
    private final List<Part> parts = new ArrayList<>();
    private final List<Part> acceptedParts = new ArrayList<>();

    private void prepare(){
        workflows.clear();
        parts.clear();
        acceptedParts.clear();

        // create workflow names
        int partsStartAtThisLine = -1;
        List<String> lines = reader.readFile();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if(line.isEmpty()) {
                partsStartAtThisLine = i + 1;
                break;
            }
            workflows.add(new Workflow(line.substring(0, line.indexOf("{"))));
        }
        workflows.add(A);
        workflows.add(R);


        // create rules to workflows
        Pattern rules = Pattern.compile("([a-z])[<>](\\d+):([a-z]+)");
        for (int i = 0; i < partsStartAtThisLine - 1; i++) {
            String line = lines.get(i);
            int start = line.indexOf("{");
            Workflow wf = getWorkFlowByName(line.substring(0, start));
            line = line.substring(start, line.length() - 1);

            String[] parts = line.split(",");
            for (String part : parts) {
                Rule r = new Rule();

                if (part.contains("<")) {
                    r.type = Type.SMALLER;
                    Matcher m = rules.matcher(part);
                    while (m.find()) {
                        r.category = getCategory(m.group(1));
                        r.value = Integer.parseInt(m.group(2));
                        r.sendPartHere = getWorkFlowByName(m.group(3));
                    }
                } else if (part.contains(">")) {
                    r.type = Type.BIGGER;
                    Matcher m = rules.matcher(part);
                    while (m.find()) {
                        r.category = getCategory(m.group(1));
                        r.value = Integer.parseInt(m.group(2));
                        r.sendPartHere = getWorkFlowByName(m.group(3));
                    }
                } else {
                    r.type = Type.NO_CHECK;
                    r.sendPartHere = getWorkFlowByName(part);
                }

                wf.rules.add(r);
            }
        }

        // add parts:
        Pattern digits = Pattern.compile("\\d+");
        for (int i = partsStartAtThisLine; i < lines.size(); i++) {
            Matcher matcher = digits.matcher(lines.get(i));
            List<Integer> n = new ArrayList<>();
            while (matcher.find()){
                n.add(Integer.parseInt(matcher.group()));
            }
            parts.add(new Part(n.get(0), n.get(1), n.get(2), n.get(3)));
        }
    }

    public void part1(){
        prepare();

    }

    public void part2(){

    }

    private Workflow getWorkFlowByName(String name){
        for (int i = 0; i < workflows.size(); i++) {
            if(workflows.get(i).name.equals(name)){
                return workflows.get(i);
            }
        }
        throw new IllegalStateException("Workflow with this name not found: " + name + ".");
    }

    private Category getCategory(String s){
        return switch (s) {
            case "x" -> Category.X;
            case "m" -> Category.M;
            case "a" -> Category.A;
            case "s" -> Category.S;
            default -> throw new IllegalStateException("Category not recognized: " + s + ".");
        };
    }
}