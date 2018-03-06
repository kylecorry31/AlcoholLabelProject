package com.emeraldElves.alcohollabelproject.Data;

import com.emeraldElves.alcohollabelproject.data.COLA;

/**
 * Created by Dan on 4/23/2017.
 */
public class SavedApplication {

    private ApplicationType applicationType;
    private COLA alcoholInfo;
    private String extraInfo;
    private LabelImage image;
    private long applicationID = -1;

    public SavedApplication(ApplicationType applicationType, COLA COLA, String extraInfo, LabelImage imageURL) {
        this.applicationType = applicationType;
        this.alcoholInfo = COLA;
        this.extraInfo = extraInfo;
        this.image = imageURL;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public COLA getAlcoholInfo() {
        return alcoholInfo;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public LabelImage getImage() {
        return image;
    }

    public long getApplicationID(){ return applicationID;}

    public void setApplicationID(long id){ applicationID = id;}
}
