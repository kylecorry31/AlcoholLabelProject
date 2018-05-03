package com.emeraldElves.alcohollabelproject.ui.validation;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class TextEqualValidator extends ValidatorBase{

    private TextInputControl otherText;

    public TextEqualValidator(TextInputControl otherText){
        this.otherText = otherText;
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl){
            TextInputControl textField = (TextInputControl) srcControl.get();
            if(textField.getText() == null){
                hasErrors.set(true);
            } else {
                hasErrors.set(!textField.getText().equals(otherText.getText()));
            }

        }
    }
}
