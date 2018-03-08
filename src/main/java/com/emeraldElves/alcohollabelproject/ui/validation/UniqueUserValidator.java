package com.emeraldElves.alcohollabelproject.ui.validation;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.EmailAddress;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class UniqueUserValidator extends ValidatorBase{

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl){
            TextInputControl textField = (TextInputControl) srcControl.get();
            if(textField.getText() == null){
                hasErrors.set(true);
            } else {
                if(Authenticator.getInstance().isEmailTaken(textField.getText())){
                    hasErrors.set(true);
                } else {
                    hasErrors.set(false);
                }
            }

        }
    }
}
