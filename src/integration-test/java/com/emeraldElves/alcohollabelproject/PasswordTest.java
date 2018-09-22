package com.emeraldElves.alcohollabelproject;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PasswordTest {

    @Test
    public void testPasswordHashing(){
        String pw = "password";
        String hashed = Password.hash(pw);

        assertNotEquals(pw, hashed);

        assertTrue(Password.matches(pw, hashed));
        assertFalse(Password.matches("password1", hashed));
    }

}
