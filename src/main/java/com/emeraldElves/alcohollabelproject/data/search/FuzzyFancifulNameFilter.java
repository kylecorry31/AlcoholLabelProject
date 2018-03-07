package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;

public class FuzzyFancifulNameFilter implements SearchFilter {

    private String targetFanciful;

    public FuzzyFancifulNameFilter(String targetFanciful) {
        this.targetFanciful = targetFanciful.toLowerCase();
    }

    @Override
    public boolean shouldRemove(COLA cola) {
        return !cola.getFancifulName().toLowerCase().contains(targetFanciful);
    }
}
