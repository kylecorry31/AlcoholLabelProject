package com.emeraldElves.alcohollabelproject.UserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RecyclerView<T> extends ScrollPane {

    @FXML
    private VBox vBox;

    private Adapter<T> adapter;

    public RecyclerView(Adapter<T> adapter){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RecyclerView.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.adapter = adapter;
        addElements();
    }

    private void addElements(){
        if(adapter == null)
            return;

        vBox.getChildren().clear();

        for (int i = 0; i < adapter.getItemCount(); i++) {
            Pane view = adapter.onCreateItemPane(adapter.getItem(i));
            vBox.getChildren().add(view);
        }
    }

    public interface Adapter<T> {
        int getItemCount();
        Pane onCreateItemPane(T item);
        T getItem(int i);
    }

}
