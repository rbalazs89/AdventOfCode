package year2018.day4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeStamp {
    private final int year;
    private final int month;
    private final int day;

    private final int hour;
    private final int minute;

    // example input string: [1518-11-01 00:00] Guard #10 begins shift
    private static final Pattern PATTERN = Pattern.compile("\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})]");

    TimeStamp(String line){
        Matcher matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid timestamp format: " + line);
        }
        this.year = Integer.parseInt(matcher.group(1));
        this.month = Integer.parseInt(matcher.group(2));
        this.day = Integer.parseInt(matcher.group(3));
        this.hour = Integer.parseInt(matcher.group(4));
        this.minute = Integer.parseInt(matcher.group(5));
    }

    int compareTo(TimeStamp other) {
        if (this.year != other.year) return Integer.compare(this.year, other.year);
        if (this.month != other.month) return Integer.compare(this.month, other.month);
        if (this.day != other.day) return Integer.compare(this.day, other.day);
        if (this.hour != other.hour) return Integer.compare(this.hour, other.hour);
        return Integer.compare(this.minute, other.minute);
    }
}