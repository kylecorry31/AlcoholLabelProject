package com.emeraldElves.alcohollabelproject.IDGenerator2.IDGenerator;

/**
 * Created by Kylec on 4/9/2017.
 */
public class TimeGenerator implements IDGenerator {
    @Override
    /**
     * Generate an ID based on the current system time.
     */
    public long generateID() {
        return getTime();
    }

    private long getTime() {
        return System.currentTimeMillis();
    }
}
