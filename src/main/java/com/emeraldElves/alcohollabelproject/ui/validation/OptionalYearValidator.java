package com.emeraldElves.alcohollabelproject.ui.validation;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class OptionalYearValidator extends ValidatorBase{


    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl){
            TextInputControl textField = (TextInputControl) srcControl.get();

            if(textField.getText() == null || textField.getText().trim().isEmpty()){
                hasErrors.set(false);
                return;
            }

            if(textField.getText().trim().length() != 4){
                hasErrors.set(true);
            } else {
                try {
                    Integer.valueOf(textField.getText().trim());
                    hasErrors.set(false);
                } catch (Exception e){
                    hasErrors.set(true);
                }
            }
        }
    }
}
