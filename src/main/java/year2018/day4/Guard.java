package year2018.day4;

class Guard {
    private int id;
    int[] sumTimeSleepingTime = new int[60];

    Guard (int id){
        this.id = id;
    }

    int getId(){
        return id;
    }

    void incrementSumSleepingTime(int start, int end){
        for (int i = start; i < end; i++) {
            sumTimeSleepingTime[i] ++;
        }
    }

    int getSumSleepingSumTime(){
        int sum = 0;
        for (int i = 0; i < sumTimeSleepingTime.length; i++) {
            sum += sumTimeSleepingTime[i];
        }
        return sum;
    }

    int getWorstSleepingMinuteValue(){
        int worstIndex = -1;
        int worstMinuteValue = -1;
        for (int i = 0; i < sumTimeSleepingTime.length; i++) {
            if(worstMinuteValue < sumTimeSleepingTime[i]){
                worstIndex = i;
                worstMinuteValue = sumTimeSleepingTime[i];
            }
        }
        if(worstIndex == -1){
            System.out.println("This guard never slept: Guard #" + id);
        }
        return worstMinuteValue;
    }

    int getWorstSleepingMinuteIndex(){
        int worstIndex = -1;
        int worstMinuteValue = -1;
        for (int i = 0; i < sumTimeSleepingTime.length; i++) {
            if(worstMinuteValue < sumTimeSleepingTime[i]){
                worstIndex = i;
                worstMinuteValue = sumTimeSleepingTime[i];
            }
        }
        if(worstIndex == -1){
            System.out.println("This guard never slept: Guard #" + id);
        }
        return worstIndex;
    }
}
