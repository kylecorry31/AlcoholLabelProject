package com.emeraldElves.alcohollabelproject.Data;

/**
 * Represents whether the alcohol is domestic or imported
 */
public enum ProductSource {
    DOMESTIC(0, "Domestic"),
    IMPORTED(1, "Imported");

    private int value;
    private String displayName;

    ProductSource(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getValue() {
        return value;
    }

    public static ProductSource fromInt(int val) {
        return val == 0 ? DOMESTIC : IMPORTED;
    }
}
