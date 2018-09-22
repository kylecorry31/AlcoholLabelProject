package com.emeraldElves.alcohollabelproject;

import org.mindrot.jbcrypt.BCrypt;

public class Password {

    public static String hash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static String hash(String password, int saltRounds){
        return BCrypt.hashpw(password, BCrypt.gensalt(saltRounds));
    }

    public static boolean matches(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }

}
