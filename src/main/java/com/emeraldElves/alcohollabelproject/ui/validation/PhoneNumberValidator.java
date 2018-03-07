package com.emeraldElves.alcohollabelproject.ui.validation;

import com.emeraldElves.alcohollabelproject.Data.PhoneNumber;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class PhoneNumberValidator extends ValidatorBase{

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl){
            TextInputControl textField = (TextInputControl) srcControl.get();
            if(textField.getText() == null){
                hasErrors.set(true);
            } else {
                PhoneNumber phoneNumber = new PhoneNumber(textField.getText());

                if(phoneNumber.isValid()){
                    hasErrors.set(false);
                } else {
                    hasErrors.set(true);
                }
            }

        }
    }
}
