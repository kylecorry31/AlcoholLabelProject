package com.emeraldElves.alcohollabelproject.ui.validation;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class TextLengthValidator extends ValidatorBase{

    private int reqLength;

    public TextLengthValidator(int reqLength) {
        this.reqLength = reqLength;
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl){
            TextInputControl textField = (TextInputControl) srcControl.get();
            if(textField.getText() == null || textField.getText().trim().length() != reqLength){
                hasErrors.set(true);
            } else {
                hasErrors.set(false);
            }
        }
    }
}
