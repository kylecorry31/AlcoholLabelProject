package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class FuzzyFancifulNameFilter implements SearchFilter {

    private String targetFanciful;

    public FuzzyFancifulNameFilter(String targetFanciful) {
        this.targetFanciful = targetFanciful.toLowerCase();
    }

    @Override
    public boolean shouldRemove(COLA cola) {
        if(targetFanciful.isEmpty())
            return false;
        return FuzzySearch.partialRatio(cola.getFancifulName().toLowerCase(), targetFanciful) < 80;
    }
}
