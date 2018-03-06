package com.emeraldElves.alcohollabelproject.UserInterface;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class AlphaNumericValidator extends ValidatorBase{

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl){
            TextInputControl textField = (TextInputControl) srcControl.get();
            if(textField.getText() == null){
                hasErrors.set(true);
            } else {
                String text = textField.getText().trim();

                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    if (!Character.isAlphabetic(c) && !Character.isDigit(c)){
                        hasErrors.set(true);
                        return;
                    }
                }
                hasErrors.set(false);
            }

        }
    }
}
