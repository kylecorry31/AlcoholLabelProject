package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.data.COLA;

public class MaxSearchRanking implements SearchRanking {

    private SearchRanking[] rankings;

    public MaxSearchRanking(SearchRanking... rankings){
        this.rankings = rankings;
    }

    @Override
    public int rank(COLA cola) {
        int max = 0;
        for (SearchRanking ranking: rankings){
            max = Math.max(ranking.rank(cola), max);
        }
        return max;
    }
}
