package com.emeraldElves.alcohollabelproject;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {

    @Test
    public void testHashing(){
        String pw = "password";
        String hashed = BCrypt.hashpw(pw, BCrypt.gensalt(14));

        assertNotEquals(pw, hashed);

        assertTrue(BCrypt.checkpw(pw, hashed));
        assertFalse(BCrypt.checkpw("password1", hashed));
    }

}
