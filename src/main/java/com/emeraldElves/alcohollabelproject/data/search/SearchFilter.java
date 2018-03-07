package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;

public interface SearchFilter {
    boolean shouldRemove(COLA cola);

    /**
     * Removes only if both are true
     * @param filter
     * @return
     */
     default SearchFilter and(SearchFilter filter){
        return cola -> (SearchFilter.this.shouldRemove(cola) && filter.shouldRemove(cola));
     }

     default SearchFilter or(SearchFilter filter){
         return cola -> (SearchFilter.this.shouldRemove(cola) || filter.shouldRemove(cola));
     }

     default SearchFilter not(){
         return cola -> !SearchFilter.this.shouldRemove(cola);
     }
}