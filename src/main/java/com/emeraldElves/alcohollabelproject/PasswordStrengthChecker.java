package com.emeraldElves.alcohollabelproject;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

public class PasswordStrengthChecker {

    private Zxcvbn strengthChecker;

    public enum Strength {
        WEAK, FAIR, GOOD, STRONG, VERY_STRONG
    }


    public PasswordStrengthChecker(){
        strengthChecker = new Zxcvbn();
    }

    public Strength check(String password){
        com.nulabinc.zxcvbn.Strength strength = strengthChecker.measure(password);
        switch (strength.getScore()){
            case 1:
                return Strength.FAIR;
            case 2:
                return Strength.GOOD;
            case 3:
                return Strength.STRONG;
            case 4:
                return Strength.VERY_STRONG;
            default:
                return Strength.WEAK;
        }
    }

}
