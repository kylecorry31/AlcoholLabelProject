package com.emeraldElves.alcohollabelproject.data;

import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.IDGenerator.ApplicationIDGenerator;
import com.emeraldElves.alcohollabelproject.IDGenerator.TTBIDGenerator;
import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.database.Storage;

import java.time.LocalDate;
import java.util.List;

public class COLASubmissionHandler {

    private Storage storage;
    private ApplicationIDGenerator idGenerator;

    public COLASubmissionHandler(){
        storage = Storage.getInstance();
        idGenerator = new TTBIDGenerator();
    }

    public synchronized void submitCOLA(COLA cola){
        COLA inDBCOLA = storage.getCOLA(cola.getId());
        cola.setStatus(ApplicationStatus.RECEIVED);
        if(inDBCOLA == null){
            LogManager.getInstance().log(getClass().getSimpleName(), "Submitted COLA " + cola.getId());
            storage.saveCOLA(cola);
        } else {
            LogManager.getInstance().log(getClass().getSimpleName(), "Updated COLA " + cola.getId());
            storage.updateCOLA(cola);
        }
    }

    public synchronized long getNextCOLAID(){
        ApplicationIDGenerator idGenerator = new TTBIDGenerator();
        return idGenerator.generateID();
    }

    public synchronized List<COLA> getSubmittedCOLAS(User user){
        return storage.getCOLAsByUser(user);
    }

    public long getSubmittedCOLACount(User user){
        return getSubmittedCOLAS(user).size();
    }

    public synchronized String getNextSerialNumber(User user){
        long submittedCount = getSubmittedCOLACount(user);
        long year = LocalDate.now().getYear() % 100;

        return String.valueOf(year) + String.format("%04x", submittedCount).toUpperCase();
    }

}
