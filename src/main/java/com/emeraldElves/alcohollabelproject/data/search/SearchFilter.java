package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;

public interface SearchFilter {
    boolean matches(COLA cola);

    /**
     * Removes only if both are true
     * @param filter
     * @return
     */
     default SearchFilter and(SearchFilter filter){
        return cola -> (SearchFilter.this.matches(cola) && filter.matches(cola));
     }

     default SearchFilter or(SearchFilter filter){
         return cola -> (SearchFilter.this.matches(cola) || filter.matches(cola));
     }

     default SearchFilter not(){
         return cola -> !SearchFilter.this.matches(cola);
     }
}