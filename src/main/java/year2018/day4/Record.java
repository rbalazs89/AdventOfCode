package year2018.day4;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Record {
    static final String MARK_SLEEP = "falls asleep";
    static final String MARK_WAKE_UP = "wakes up";
    static final String GUARD_MARKER = "Guard";

    private final TimeStamp timeStamp;
    private final String type;
    private final Guard id;
    private static final Pattern PATTERN = Pattern.compile("\\d+#");

    Record (String line, ArrayList<Guard> guards){
        if(line.contains(MARK_SLEEP)) type = MARK_SLEEP;
        else if(line.contains(MARK_WAKE_UP)) type = MARK_WAKE_UP;
        else if (line.contains(GUARD_MARKER)) type = GUARD_MARKER;
        else throw new IllegalStateException("Record not recognized, invalid input");

        timeStamp = new TimeStamp(line);
        if(type.equals(GUARD_MARKER)){
            Matcher matcher = PATTERN.matcher(line);
            id = matchGuard(guards, Integer.parseInt(matcher.group()));
        } else {
            id = null;
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
}
