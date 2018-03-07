package com.emeraldElves.alcohollabelproject.Data;

/**
 * Created by Kylec on 4/4/2017.
 */
public enum UserType {
    TTBAGENT(0, "TTB Agent"),
    APPLICANT(1, "Alcohol Provider"),
    BASIC(2, "Basic User"),
    SUPERAGENT(3, "Admin");

    private int value;
    private String displayName;

    UserType(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UserType fromInt(int val) {
        switch (val) {
            case 0:
                return TTBAGENT;
            case 1:
                return APPLICANT;

            case 2:
                return BASIC;
            case 3:
                return SUPERAGENT;
            default:
                return BASIC;
        }
    }
}
