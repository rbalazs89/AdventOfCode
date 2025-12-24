package year2015.day23;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day23 {
    private List<String> fileLines;

    private final ArrayList<Register> registers = new ArrayList<>();
    private final ArrayList<Instruction> instructions = new ArrayList<>();

    //doesn't work on example input, it has no register b
    private final ReadLines reader = new ReadLines(2015, 23, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    private void processData() {
        readData();
        registers.clear();
        instructions.clear();
        // get registers:
        Set<String> tempSet = new HashSet<>();
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            String[] parts = line.split(" ");
            if (parts.length > 1) {
                char c = parts[1].charAt(0);
                if (Character.isLetter(c)) {
                    if (tempSet.add(String.valueOf(c))) {
                        Register r = new Register();
                        r.name = String.valueOf(c);
                        registers.add(r);
                    }
                }
            }
        }

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            String[] parts = line.split(" ");

            Instruction inst = new Instruction();
            // jump type:
            if (parts[0].equals("jmp")) {
                boolean positive;
                if(parts[1].charAt(0) == '+'){
                    positive = true;
                } else {
                    positive = false;
                }
                int num = Integer.parseInt(parts[1].substring(1));
                if(!positive){
                    num = num * -1;
                }

                inst.index = i;
                inst.name = "jmp";
                inst.value = num;
            }

            if (parts.length == 2){

                inst.index = i;
                inst.name = parts[0];
                inst.register = findRegisterByName(parts[1]);
            }
            if(parts.length == 3){

                inst.index = i;
                inst.name = parts[0];
                inst.register = findRegisterByName(parts[1].substring(0,1));

                boolean positive;
                if(parts[2].charAt(0) == '+'){
                    positive = true;
                } else {
                    positive = false;
                }
                int num = Integer.parseInt(parts[2].substring(1));
                if(!positive){
                    num = num * -1;
                }
                inst.value = num;
            }
            instructions.add(inst);
        }
    }

    private Register findRegisterByName(String name){
        for (int i = 0; i < registers.size(); i++) {
            if(registers.get(i).name.equals(name)){
                return registers.get(i);
            }
        }
        return null;
    }

    public void part1(){
        processData();
        int index = 0;
        while(true){
            if(index >= instructions.size()){
                break;
            }
            index = processOneInstruction(index);

        }
        System.out.println(findRegisterByName("b").value);

    }

    private int processOneInstruction(int index){
        Instruction instr = instructions.get(index);
        if(instr.name.equals("hlf")){
            instr.register.value = instr.register.value / 2;
            return index + 1;
        }

        if(instr.name.equals("tpl")){
            instr.register.value = instr.register.value * 3;
            return index + 1;
        }

        if(instr.name.equals("inc")){
            instr.register.value ++;
            return index + 1;
        }

        if(instr.name.equals("jmp")){
            return index + instr.value;
        }

        if(instr.name.equals("jie")){
            if(instr.register.value % 2 == 0){
                return index + instr.value;
            } else {
                return index + 1;
            }
        }
        if(instr.name.equals("jio")){
            if(instr.register.value == 1){
                return index + instr.value;
            } else {
                return index + 1;
            }
        }
        System.out.println("problem" + instructions.get(index).name);
        return -1;
    }

    public void part2(){
        processData();
        int index = 0;
        findRegisterByName("a").value = 1;
        while(true){
            if(index >= instructions.size()){
                break;
            }
            index = processOneInstruction(index);

        }
        System.out.println(findRegisterByName("b").value);
    }
}
