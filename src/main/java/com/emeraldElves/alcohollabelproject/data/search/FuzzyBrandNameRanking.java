package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class FuzzyBrandNameRanking implements SearchRanking {

    private String targetBrand;

    public FuzzyBrandNameRanking(String targetBrand) {
        this.targetBrand = targetBrand.toLowerCase();
    }

    @Override
    public int rank(COLA cola) {
        return FuzzySearch.ratio(cola.getBrandName().toLowerCase(), targetBrand);
    }
}
