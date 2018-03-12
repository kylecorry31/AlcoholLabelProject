package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.data.COLA;

public class TypeFilter implements SearchFilter {

    private AlcoholType type;

    public TypeFilter(AlcoholType type) {
        this.type = type;
    }

    @Override
    public boolean matches(COLA cola) {
        return cola.getType() == type;
    }
}
