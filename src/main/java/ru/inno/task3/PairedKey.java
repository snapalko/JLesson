package ru.inno.task3;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

class PairedKey {
    private Method method;
    private long timeLive;

    // Конструктор
    public PairedKey(Method method) {
        this.method = method;
        this.timeLive = 0;
    }

    public PairedKey(Method method, long timeout) {
        this.method = method;
        this.timeLive = System.currentTimeMillis() + timeout;
    }

    public Method getMethod() {
        return method;
    }

    public long getTimeLive() {
        return timeLive;
    }

    public void setTimeLife(long timeLive) {
        this.timeLive = timeLive;
    }

    public boolean isLive(long currentTimeMillis) {
//        System.out.println("isLife="+(currentTimeMillis <= this.timeLife));
        return currentTimeMillis <= this.timeLive;
    }

    public static int compare(PairedKey p1, PairedKey p2) {
        if (p1.timeLive > p2.timeLive)
            return 1;
        return -1;
    }

    // Показать время long time в виде строки "HH:mm:ss-SS"
    public String getStrTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss-SS");
        Date date = new Date(time);
        return formatter.format(date);
    }

    public String toString() {
        return "{"+this.method+", "+getStrTime(this.timeLive)+"}";
    }

}
