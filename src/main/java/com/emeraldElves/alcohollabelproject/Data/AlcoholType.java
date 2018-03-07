package com.emeraldElves.alcohollabelproject.Data;

/**
 * Created by keionbis on 4/3/17.
 */
public enum AlcoholType {
    BEER(0, "Malt Beverages"),
    WINE(1, "Wine"),
    DISTILLEDSPIRITS(2, "Distilled Spirits");

    private int value;
    private String displayName;

    AlcoholType(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static AlcoholType fromInt(int val) {
        switch (val) {
            case 0:
                return BEER;
            case 1:
                return WINE;
            default:
                return DISTILLEDSPIRITS;
        }
    }
}
