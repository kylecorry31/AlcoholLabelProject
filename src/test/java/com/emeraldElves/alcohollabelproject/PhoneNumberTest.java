package com.emeraldElves.alcohollabelproject;

import com.emeraldElves.alcohollabelproject.Data.PhoneNumber;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kylec on 4/1/2017.
 */
public class PhoneNumberTest {

    @Test
    public void testPhoneNumber() {
        PhoneNumber number = new PhoneNumber("5555555555");
        assertEquals(number.getPhoneNumber(), "5555555555");
        assertTrue(number.isValid());

        PhoneNumber number1 = new PhoneNumber("1-555-555-5555");
        assertEquals(number1.getPhoneNumber(), "15555555555");
        assertTrue(number1.isValid());

        PhoneNumber number2 = new PhoneNumber("555.555.5555");
        assertEquals(number2.getPhoneNumber(), "5555555555");
        assertTrue(number2.isValid());


        PhoneNumber number3 = new PhoneNumber("55.555.5555");
        assertEquals(number3.getPhoneNumber(), "555555555");
        assertFalse(number3.isValid());
    }
}