package com.emeraldElves.alcohollabelproject.IDGenerator;


import com.emeraldElves.alcohollabelproject.database.Storage;

import java.time.LocalDate;

/**
 * Created by Kylec on 4/9/2017.
 */
public class TTBFormatGenerator implements IDGenerator {

    private long fileMethod;

    private static final long YEAR_START = Math.round(Math.pow(10, 12));
    private static final long DAY_START = Math.round(Math.pow(10, 9));
    private static final long FILE_METHOD_START = Math.round(Math.pow(10, 6));
    private static final long COUNTER_START = 1L;

    /**
     * Create a generator which uses the TTB format.
     * @param fileMethod The method of submission: 1 for electronic
     */
    public TTBFormatGenerator(long fileMethod) {
        this.fileMethod = fileMethod;
    }

    @Override
    public long generateID() {

        IDCounter counter = Storage.getInstance().getCounter();

        LocalDate date = LocalDate.now();

        if(shouldRestartCount(counter.getLastModified())){
            counter.setCount(-1);
            counter.setLastModified(date);
        }

        long yearEnding = date.getYear() % 100;
        long dayOfYear = date.getDayOfYear();
        long count = counter.getCount() + 1;

        counter.setCount(count);
        counter.setLastModified(date);

        Storage.getInstance().updateCounter(counter);

        return yearEnding * YEAR_START + dayOfYear * DAY_START + fileMethod * FILE_METHOD_START + count * COUNTER_START;
    }

    private boolean shouldRestartCount(LocalDate lastModified){
        LocalDate current = LocalDate.now();
        return current.isAfter(lastModified);
    }

}
