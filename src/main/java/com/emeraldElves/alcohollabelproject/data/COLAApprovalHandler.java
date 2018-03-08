package com.emeraldElves.alcohollabelproject.data;

import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.database.Storage;

import java.time.LocalDate;
import java.util.List;

public class COLAApprovalHandler {

    private Storage storage;

    public COLAApprovalHandler(){
        storage = Storage.getInstance();
    }

    public synchronized List<COLA> getAssignedApplications(User user){
        return storage.getAssignedCOLAs(user);
    }

    public synchronized List<COLA> assignCOLAs(User user, int count){
        User nullUser = new User("", "", UserType.TTBAGENT);
        nullUser.setId(-1);

        List<COLA> unassigned = getAssignedApplications(nullUser);

        for (COLA cola: unassigned){
            if(count > 0){
                cola.setAssignedTo(user.getId());
                storage.updateCOLA(cola);
                count--;
            } else {
                break;
            }
        }

        return getAssignedApplications(user);
    }

    public void approveCOLA(COLA cola, String message){
        cola.setStatus(ApplicationStatus.APPROVED);
        cola.setApprovalDate(LocalDate.now());
        // Email user
        storage.updateCOLA(cola);
    }

    public void rejectCOLA(COLA cola, String message){
        cola.setStatus(ApplicationStatus.REJECTED);
        // Email user
        storage.updateCOLA(cola);
    }

    public void setNeedsCorrections(COLA cola, String message){
        cola.setStatus(ApplicationStatus.NEEDS_CORRECTION);
        // Email user
        storage.updateCOLA(cola);
    }

}
