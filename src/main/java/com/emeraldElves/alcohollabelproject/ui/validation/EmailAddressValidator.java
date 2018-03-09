package com.emeraldElves.alcohollabelproject.ui.validation;

import com.emeraldElves.alcohollabelproject.Data.EmailAddress;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class EmailAddressValidator extends ValidatorBase{

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl){
            TextInputControl textField = (TextInputControl) srcControl.get();
            if(textField.getText() == null){
                hasErrors.set(true);
            } else {
                EmailAddress email = new EmailAddress(textField.getText());

                if(email.isValid()){
                    hasErrors.set(false);
                } else {
                    hasErrors.set(true);
                }
            }

        }
    }
}
