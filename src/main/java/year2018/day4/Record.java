package year2018.day4;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Record {
    static final String MARK_SLEEP = "falls asleep";
    static final String MARK_WAKE_UP = "wakes up";
    static final String GUARD_MARKER = "Guard";
    static final Pattern GUARD_PATTERN = Pattern.compile("#(\\d+)");

    private TimeStamp timeStamp;
    private final String type;
    private final Guard guard;

    Record (String line, ArrayList<Guard> guards){
        if(line.contains(MARK_SLEEP)) type = MARK_SLEEP;
        else if(line.contains(MARK_WAKE_UP)) type = MARK_WAKE_UP;
        else if (line.contains(GUARD_MARKER)) type = GUARD_MARKER;
        else throw new IllegalStateException("Record not recognized, invalid input");

        timeStamp = new TimeStamp(line);

        if (type.equals(GUARD_MARKER)) {
            Matcher matcher = GUARD_PATTERN.matcher(line);
            if (matcher.find()) {
                int guardId = Integer.parseInt(matcher.group(1));
                guard = matchGuard(guards, guardId);
            } else {
                throw new IllegalStateException("guard not found");
            }
        }
        else {
            guard = null;
        }
    }

    Guard matchGuard(ArrayList<Guard> guards, int id){
        for (int i = 0; i < guards.size(); i++) {
            if(guards.get(i).getId() == id){
                return guards.get(i);
            }
        }
        throw new IllegalStateException("Record not recognized, invalid input");
    }

    TimeStamp getTimeStamp() {
        return timeStamp;
    }

    Guard getGuard() {
        return guard;
    }

    String getType(){
        return type;
    }

    @Override
    public String toString(){
        String timeStampString = timeStamp.toString();
        String addedString;
        if(type.equals(MARK_SLEEP)){
            addedString = " Guard falls asleep.";
        } else if (type.equals(MARK_WAKE_UP)){
            addedString = " Guard wakes up.";
        } else {
            addedString = " Guard #" + guard.getId() + " begins shift.";
        }
        return timeStampString + addedString;
    }
}
