package com.emeraldElves.alcohollabelproject.ui.validation;

import com.jfoenix.validation.NumberValidator;
import javafx.scene.control.TextInputControl;

public class OptionalNumberValidator extends NumberValidator {

    @Override
    protected void eval() {
        super.eval();
        if (srcControl.get() instanceof TextInputControl) {
            TextInputControl textField = (TextInputControl) srcControl.get();
            if(textField.getText() == null || textField.getText().isEmpty()){
                hasErrors.set(false);
            }
        }
    }
}
