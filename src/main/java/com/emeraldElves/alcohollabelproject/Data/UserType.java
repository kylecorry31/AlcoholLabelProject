package com.emeraldElves.alcohollabelproject.Data;

/**
 * Created by Kylec on 4/4/2017.
 */
public enum UserType {
    TTBAGENT(0),
    APPLICANT(1),
    BASIC(2),
    SUPERAGENT(3);

    private int value;

    UserType(int value) {
        this.value = value;
    }
};
