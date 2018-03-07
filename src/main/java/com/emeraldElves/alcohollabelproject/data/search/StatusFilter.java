package com.emeraldElves.alcohollabelproject.data.search;

import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.data.COLA;

public class StatusFilter implements SearchFilter {

    private ApplicationStatus status;

    public StatusFilter(ApplicationStatus status) {
        this.status = status;
    }

    @Override
    public boolean shouldRemove(COLA cola) {
        return cola.getStatus() != status;
    }
}
