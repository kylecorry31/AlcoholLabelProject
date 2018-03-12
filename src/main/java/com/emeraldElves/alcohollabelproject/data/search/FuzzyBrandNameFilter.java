package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class FuzzyBrandNameFilter implements SearchFilter {

    private String targetBrand;

    public FuzzyBrandNameFilter(String targetBrand) {
        this.targetBrand = targetBrand.toLowerCase();
    }

    @Override
    public boolean matches(COLA cola) {
        if(targetBrand.isEmpty())
            return true;
        return FuzzySearch.partialRatio(cola.getBrandName().toLowerCase(), targetBrand) >= 80;
    }
}
