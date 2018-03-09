package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class FuzzyFancifulNameRanking implements SearchRanking {

    private String targetFanciful;

    public FuzzyFancifulNameRanking(String targetFanciful) {
        this.targetFanciful = targetFanciful.toLowerCase();
    }

    @Override
    public int rank(COLA cola) {
        return FuzzySearch.ratio(cola.getFancifulName().toLowerCase(), targetFanciful);
    }
}
