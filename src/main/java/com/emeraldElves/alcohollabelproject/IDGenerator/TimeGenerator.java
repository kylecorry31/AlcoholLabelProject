package com.emeraldElves.alcohollabelproject.IDGenerator;

/**
 * Created by Kylec on 4/9/2017.
 */
public class TimeGenerator implements IDGenerator {
    /**
     * Generate an ID based on the current system time.
     */
    @Override
    public long generateID() {
        return getTime();
    }

    private long getTime() {
        return System.currentTimeMillis();
    }
}
