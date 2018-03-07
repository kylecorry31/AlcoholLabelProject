package com.emeraldElves.alcohollabelproject.IDGenerator;

import java.time.LocalDate;

public class IDCounter {

    public static final long ID = 1;

    public static final String DB_TABLE = "counter";
    public static final String DB_ID = "id";
    public static final String DB_COUNTER = "counter";
    public static final String DB_LAST_MODIFIED = "last_modified";


    private long count;
    private LocalDate lastModified;

    public IDCounter(long count, LocalDate lastModified) {
        this.count = count;
        this.lastModified = lastModified;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }
}
