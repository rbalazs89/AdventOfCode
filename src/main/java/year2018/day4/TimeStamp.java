package year2018.day4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeStamp implements Comparable<TimeStamp>{
    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    // example input string: [1518-11-01 00:00] Guard #10 begins shift
    private static final Pattern TIME_PATTERN = Pattern.compile("\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})]");

    TimeStamp(String line){
        Matcher matcher = TIME_PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid timestamp format: " + line);
        }
        this.year = Integer.parseInt(matcher.group(1));
        this.month = Integer.parseInt(matcher.group(2));
        this.day = Integer.parseInt(matcher.group(3));
        this.hour = Integer.parseInt(matcher.group(4));
        this.minute = Integer.parseInt(matcher.group(5));
    }

    @Override
    public int compareTo(TimeStamp other) {
        if (this.year != other.year) return Integer.compare(this.year, other.year);
        if (this.month != other.month) return Integer.compare(this.month, other.month);
        if (this.day != other.day) return Integer.compare(this.day, other.day);
        if (this.hour != other.hour) return Integer.compare(this.hour, other.hour);
        return Integer.compare(this.minute, other.minute);
    }

    // task description states that only the 00:00 -> 01:00 one hour duration matters
    void normalizeTime() {
        if (hour != 23) {
            return;
        }

        int newYear = year;
        int newMonth = month;
        int newDay = day + 1;

        int daysInCurrentMonth = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> 28; // 1518 not leap year
            default -> throw new IllegalStateException("Invalid month");
        };

        if (newDay > daysInCurrentMonth) {
            newDay = 1;
            newMonth++;
            if (newMonth > 12) {
                newMonth = 1;
                newYear++;
            }
        }

        // normalize
        this.year = newYear;
        month = newMonth;
        day = newDay;
        hour = 0;
        minute = 0;
    }

    int getMinute() {
        return minute;
    }

    int getHour(){
        return hour;
    }

    @Override
    public String toString(){
        return String.format("[%04d-%02d-%02d %02d:%02d]", year, month, day, hour, minute);
    }

    static int getMaxMinute(){
        return 60;
    }

}