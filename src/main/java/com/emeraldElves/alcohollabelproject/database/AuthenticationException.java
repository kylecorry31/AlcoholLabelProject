package com.emeraldElves.alcohollabelproject.database;

public class AuthenticationException extends Exception {

    public AuthenticationException(){
        super("Invalid credentials");
    }

}
