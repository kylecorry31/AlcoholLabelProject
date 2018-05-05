package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import javafx.scene.Scene;

import java.util.List;
import java.util.Random;

public class RandomCOLAController {

    public void openRandomCOLA(Scene scene, List<COLA> alcohol){
        if (!alcohol.isEmpty()) {
            Random random = new Random();
            int pos = random.nextInt(alcohol.size());
            COLA app = alcohol.get(pos);
            UIManager.Page page = UIManager.getInstance().loadPage(UIManager.APPLICATION_DETAIL_PAGE);
            ApplicationDetailController controller = page.getController();
            controller.setAlcohol(app);
            UIManager.getInstance().displayPage(scene, page);
        }
    }

}
