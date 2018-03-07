package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;

public class FuzzyBrandNameFilter implements SearchFilter {

    private String targetBrand;

    public FuzzyBrandNameFilter(String targetBrand) {
        this.targetBrand = targetBrand.toLowerCase();
    }

    @Override
    public boolean shouldRemove(COLA cola) {
        return !cola.getBrandName().toLowerCase().contains(targetBrand);
    }
}
