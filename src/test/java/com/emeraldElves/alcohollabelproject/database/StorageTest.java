package com.emeraldElves.alcohollabelproject.database;

import com.emeraldElves.alcohollabelproject.Data.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StorageTest {

    private Storage storage;
    private IDatabase database;

    @Before
    public void setup(){
        database = new ApacheDerbyDatabase("colaTest.db");
        storage = Storage.getInstance();
        storage.setDatabase(database);
    }

    @After
    public void teardown(){
        database.dropTable(AlcoholInfo.DB_TABLE);
        database.dropTable(User.DB_TABLE);
        database.disconnect();
    }

    @Test
    public void testAlcohol(){
        assertEquals(0, storage.getAllAlcoholInfo().size());

        AlcoholInfo test = new AlcoholInfo("Brand", AlcoholType.BEER, "12345678", ProductSource.DOMESTIC);
        test.setAlcoholContent(2);
        storage.saveAlcoholInfo(test);

        test.setId(1);

        List<AlcoholInfo> infos = storage.getAllAlcoholInfo();

        assertEquals(1, infos.size());
        assertTrue(infos.contains(test));

        AlcoholInfo test2 = new AlcoholInfo("Brand2", AlcoholType.WINE, "87654321", ProductSource.IMPORTED);
        test2.setAlcoholContent(5);
        storage.saveAlcoholInfo(test2);

        test2.setId(2);

        infos = storage.getAllAlcoholInfo();

        assertEquals(2, infos.size());
        assertTrue(infos.contains(test));
        assertTrue(infos.contains(test2));

        test2.setAlcoholContent(10);
        test.setFancifulName("Fanciful");

        storage.updateAlcoholInfo(test2);
        storage.updateAlcoholInfo(test);

        infos = storage.getAllAlcoholInfo();

        assertEquals(2, infos.size());
        assertTrue(infos.contains(test));
        assertTrue(infos.contains(test2));

        AlcoholInfo info = storage.getAlcoholInfo(test.getId());
        assertEquals(test, info);

        storage.deleteAlcoholInfo(test);

        infos = storage.getAllAlcoholInfo();

        assertEquals(1, infos.size());
        assertTrue(infos.contains(test2));
    }

    @Test
    public void testUsers(){
        User u1 = new User("test", "password", UserType.APPLICANT);
        User u2 = new User("test2", "pword", UserType.TTBAGENT);

        List<User> users = storage.getAllUsers();
        assertTrue(users.isEmpty());

        u1.setId(1);

        storage.saveUser(u1);

        users = storage.getAllUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(u1));

        storage.saveUser(u2);
        u2.setId(2);

        users = storage.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(u1));
        assertTrue(users.contains(u2));

        User u = storage.getUser("test2", "pword");
        assertEquals(u, u2);

        u = storage.getUser("testing2", "pword");
        assertNull(u);

        u = storage.getUser("test2", "paword");
        assertNull(u);

        u2 = new User("test3", "paword", UserType.SUPERAGENT);
        u2.setId(2);



        storage.updateUser(u2);
        storage.deleteUser(u1);

        users = storage.getAllUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(u2));



    }



}