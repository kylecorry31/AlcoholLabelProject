package com.emeraldElves.alcohollabelproject.UserInterface;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class DialogFileSelector {
    public File openDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        return chooser.showDialog(null);
    }

    public File openFile() {
        return createFileChooser().showOpenDialog(null);
    }

    public File openFile(String fileTypePrompt, String... fileExtensions) {
        FileChooser fileChooser = createFileChooser(fileTypePrompt, fileExtensions);
        return fileChooser.showOpenDialog(null);
    }

    public File saveFile() {
        return createFileChooser().showSaveDialog(null);
    }

    public File saveFile(String fileTypePrompt, String... fileExtensions) {
        FileChooser fileChooser = createFileChooser(fileTypePrompt, fileExtensions);
        return fileChooser.showSaveDialog(null);
    }

    private FileChooser createFileChooser(String fileTypePrompt, String... fileExtensions) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(fileTypePrompt, fileExtensions);
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    private  FileChooser createFileChooser(){
        return new FileChooser();
    }
}