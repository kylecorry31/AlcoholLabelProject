package com.emeraldElves.alcohollabelproject.database;

import com.emeraldElves.alcohollabelproject.Data.*;
import com.emeraldElves.alcohollabelproject.IDGenerator.ApplicationIDGenerator;
import com.emeraldElves.alcohollabelproject.IDGenerator.TTBIDGenerator;
import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class StorageTest {

    private Storage storage;
    private IDatabase database;

    @Before
    public void setup(){
        database = new ApacheDerbyDatabase("colaTest.db");
        database.connect();
        database.dropTable(COLA.DB_TABLE);
        database.dropTable(User.DB_TABLE);
        storage = Storage.getInstance();
        storage.setDatabase(database);
        LogManager.getInstance().setShouldWrite(false);
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

        ApplicationIDGenerator idGenerator = new TTBIDGenerator();

        COLA test = new COLA(idGenerator.generateID(), "Brand", AlcoholType.BEER, "345678", ProductSource.DOMESTIC);
        test.setAlcoholContent(2);
        test.setStatus(ApplicationStatus.NEEDS_CORRECTION);
        test.setSubmissionDate(LocalDate.of(2018, 2, 24));
        test.setApprovalDate(LocalDate.of(2018, 3, 2));
        test.setApplicantID(10);
        test.setFormula(1798);
        test.setExpirationDate(LocalDate.of(2018, 7, 31));
        storage.saveCOLA(test);

        List<COLA> infos = storage.getAllCOLAs();

        assertEquals(1, infos.size());
        assertTrue(infos.contains(test));

        COLA test2 = new COLA(idGenerator.generateID(),"Brand2", AlcoholType.WINE, "654321", ProductSource.IMPORTED);
        test2.setAlcoholContent(5);
        test2.setWinePH(10);
        test2.setVintageYear(2018);
        test2.setAppellation("test");
        test2.setVarietals("testing, testing2");
        storage.saveCOLA(test2);

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
        User u1 = new User("mail@mail.com","test", "password", UserType.APPLICANT);
        User u2 = new User("test@gmail.com","test2", "pword", UserType.TTBAGENT);

        List<User> users = storage.getAllUsers();
        assertTrue(users.isEmpty());

        u1.setId(1);

        storage.saveUser(u1);

        users = storage.getAllUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(u1));

        // Hashing
        assertTrue(BCrypt.checkpw("password", users.get(0).getPassword()));

        storage.saveUser(u2);
        u2.setId(2);

        users = storage.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(u1));
        assertTrue(users.contains(u2));


        User u = storage.getUser("test@gmail.com", "pword");
        assertEquals(u, u2);

        u = storage.getUser("testing@gmail.com", "pword");
        assertNull(u);

        u = storage.getUser("test@gmail.com", "paword");
        assertNull(u);

        u2 = new User("test3@gmail.com","test3", "paword", UserType.SUPERAGENT);
        u2.setId(2);



        storage.updateUser(u2);
        storage.deleteUser(u1);

        users = storage.getAllUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(u2));



    }



}