package com.emeraldElves.alcohollabelproject.database;

import com.emeraldElves.alcohollabelproject.Data.*;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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
        database.dropTable(COLA.DB_TABLE);
        database.dropTable(User.DB_TABLE);
        database.disconnect();
    }

    @Test
    public void testAlcohol(){
        assertEquals(0, storage.getAllCOLAs().size());

        COLA test = new COLA("Brand", AlcoholType.BEER, "345678", ProductSource.DOMESTIC);
        test.setAlcoholContent(2);
        test.setStatus(ApplicationStatus.NEEDS_CORRECTION);
        test.setSubmissionDate(LocalDate.of(2018, 2, 24));
        test.setApprovalDate(LocalDate.of(2018, 3, 2));
        storage.saveCOLA(test);

        test.setId(1);

        List<COLA> infos = storage.getAllCOLAs();

        assertEquals(1, infos.size());
        assertTrue(infos.contains(test));

        COLA test2 = new COLA("Brand2", AlcoholType.WINE, "654321", ProductSource.IMPORTED);
        test2.setAlcoholContent(5);
        storage.saveCOLA(test2);

        test2.setId(2);

        infos = storage.getAllCOLAs();

        assertEquals(2, infos.size());
        assertTrue(infos.contains(test));
        assertTrue(infos.contains(test2));

        test2.setAlcoholContent(10);
        test.setFancifulName("Fanciful");

        storage.updateCOLA(test2);
        storage.updateCOLA(test);

        infos = storage.getAllCOLAs();

        assertEquals(2, infos.size());
        assertTrue(infos.contains(test));
        assertTrue(infos.contains(test2));

        COLA info = storage.getCOLA(test.getId());
        assertEquals(test, info);

        storage.deleteAlcoholInfo(test);

        infos = storage.getAllCOLAs();

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