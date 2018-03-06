package com.emeraldElves.alcohollabelproject.Data;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Created by keionbis on 4/11/17.
 */
public class ProxyLabelImage implements ILabelImage {

    private LabelImage realImage;
    private String fileName;

    public ProxyLabelImage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }


    public Image display() {
        if (realImage == null) { // Lazy Loading
            realImage = new LabelImage(fileName);
        }
        return realImage.display();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyLabelImage that = (ProxyLabelImage) o;
        return Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fileName);
    }
}
