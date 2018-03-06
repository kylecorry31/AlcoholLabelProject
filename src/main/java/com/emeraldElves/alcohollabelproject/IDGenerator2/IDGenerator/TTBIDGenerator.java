package com.emeraldElves.alcohollabelproject.IDGenerator2.IDGenerator;

/**
 * Created by Kylec on 4/9/2017.
 */
public class TTBIDGenerator extends ApplicationIDGenerator {

    /**
     * Create a generator which produces application IDs in the TTB format of year, julian date, submission type, and application count for the day
     */
    public TTBIDGenerator() {
        super(new TTBFormatGenerator(1));
    }

}
