package year2018.day4;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;

import static year2018.day4.Record.GUARD_PATTERN;

import static year2018.day4.Record.GUARD_MARKER;
import static year2018.day4.Record.MARK_SLEEP;
import static year2018.day4.Record.MARK_WAKE_UP;

public class Day4 {

    private final ReadLines reader = new ReadLines(2018, 4, 2);
    private final ArrayList<Guard>  guards = new ArrayList<>();
    private final ArrayList<Record> records = new ArrayList<>();

    private void prepare(){
        // prepare class variables
        prepareGuards();
        prepareRecords();
        addIntervals();
    }

    private void prepareRecords(){
        records.clear();
        List<String> lines = reader.readFile();

        for (String line : lines) {
            records.add(new Record(line, guards));
        }

        records.sort(Comparator.comparing(Record::getTimeStamp));

        // must be called after ordering
        for (int i = 0; i < records.size(); i++) {
            records.get(i).getTimeStamp().normalizeTime();
        }
    }

    private void prepareGuards(){
        guards.clear();
        List<String> lines = reader.readFile();
        for (int i = 0; i < lines.size(); i++) {
            Matcher m = GUARD_PATTERN.matcher(lines.get(i));
            while (m.find()){
                guards.add(new Guard(Integer.parseInt(m.group(1))));
            }
        }
    }

    private void addIntervals(){
        assert records.getFirst().getType().equals(GUARD_MARKER);

        Guard currentGuard = records.getFirst().getGuard();
        int intervalStart = 0;
        int intervalEnd;
        boolean isSleeping = false;

        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            if(record.getType().equals(GUARD_MARKER) && i != 0){

                // in case guard change happens while guard is sleeping, weird, but could happen (?)
                if(isSleeping){
                    System.out.println("change happened while sleeping " + records.get(i));
                    isSleeping = false;
                    currentGuard.incrementSumSleepingTime(intervalStart, TimeStamp.getMaxMinute());
                }

                // add time from previous guard
                currentGuard = record.getGuard();

            } else if(record.getType().equals(MARK_SLEEP)) {
                intervalStart = record.getTimeStamp().getMinute();
                isSleeping = true;

            } else if(record.getType().equals(MARK_WAKE_UP)){
                intervalEnd = record.getTimeStamp().getMinute();
                if(record.getTimeStamp().getHour() != 0){
                    System.out.println("hour not 0");
                }
                if(!isSleeping){
                    throw new IllegalStateException("sum algorithm problem " + records.get(i) + " " + records.get(i));
                }
                isSleeping = false;
                currentGuard.incrementSumSleepingTime(intervalStart, intervalEnd);
            }
        }
    }

    public void part1(){
        prepare();
        System.out.println(getPart1Result());
    }

    public void part2(){
        prepare();

        Guard worstGuard = null;
        int worstSingleMinuteValue = 0;
        int worstSingleMinuteIndex = -1;
        for (Guard guard : guards) {
            int minutes = guard.getWorstSleepingMinuteValue();
            if(minutes > worstSingleMinuteValue){
                worstSingleMinuteValue = minutes;
                worstGuard = guard;
                worstSingleMinuteIndex = guard.getWorstSleepingMinuteIndex();
            }
        }

        assert worstGuard != null;
        System.out.println(worstGuard.getId() * worstSingleMinuteIndex);

    }
    
    private int getPart1Result(){
        Guard worstGuard = null;
        int worstSumSleepTime = -1;

        for (Guard guard : guards) {
            int currentSleepTime = guard.getSumSleepingSumTime();
            if(currentSleepTime > worstSumSleepTime){
                worstSumSleepTime = currentSleepTime;
                worstGuard = guard;
            }
        }
        assert worstGuard != null;
        return worstGuard.getWorstSleepingMinuteIndex() * worstGuard.getId();
    }
}
