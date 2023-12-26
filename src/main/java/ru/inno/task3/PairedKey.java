package ru.inno.task3;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

class PairedKey {
    private final Method method;
    private long timeLive;

    public PairedKey(Method method, long timeout) {
        this.method = method;
        this.timeLive = System.currentTimeMillis() + timeout;
//        this.timeLive = System.nanoTime() + timeout;
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
        if(this.timeLive == 0) return true;
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
