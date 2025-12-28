package year2018.day7;


import java.util.*;

// Step class represents a Vertex here
class Step implements Comparable<Step>{
    private final char id;
    private final ArrayList<Step> incoming = new ArrayList<>();
    private final ArrayList<Step> outgoing = new ArrayList<>();
    private int workTime;
    static final int BASE_WORK_TIME = 61; // specified by task description

    // working time starts with 61 for A, and increases by one for each letter
    void prepareWorkTime(){
        workTime = BASE_WORK_TIME + getId() - 'A';
    }

    Step(char id){
        this.id = id;
    }

    @Override
    public int compareTo(Step other) {
        return Integer.compare(this.id, other.id);
    }

    void addOutgoing(Step s){
        if(s.id == this.id) return;
        for (int i = 0; i < outgoing.size(); i++) {
            if(outgoing.get(i).id == s.id) return;
        }
        outgoing.add(s);
    }

    void addIncoming(Step s){
        if(s.id == this.id) return;
        for (int i = 0; i < incoming.size(); i++) {
            if(incoming.get(i).id == s.id) return;
        }
        incoming.add(s);
    }

    char getId() {
        return id;
    }

    List<Step> getIncoming() {
        return Collections.unmodifiableList(incoming);
    }

    List<Step> getOutgoing() {
        return Collections.unmodifiableList(outgoing);
    }

    int getWorkTime(){
        return workTime;
    }
}
