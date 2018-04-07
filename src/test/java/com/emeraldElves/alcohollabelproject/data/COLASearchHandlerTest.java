package com.emeraldElves.alcohollabelproject.data;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.Data.ProductSource;
import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.data.search.*;
import com.emeraldElves.alcohollabelproject.database.ApacheDerbyDatabase;
import com.emeraldElves.alcohollabelproject.database.IDatabase;
import com.emeraldElves.alcohollabelproject.database.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class COLASearchHandlerTest {

    private Storage storage;
    private IDatabase database;
    private List<COLA> colas;
    private COLASearchHandler searchHandler;

    @Before
    public void setup(){
        database = new ApacheDerbyDatabase("colaTest.db");
        storage = Storage.getInstance();
        storage.setDatabase(database);
        COLA c1 = new COLA(1, "TestBrand", AlcoholType.BEER, "180001", ProductSource.DOMESTIC);
        c1.setAlcoholContent(10);
        c1.setFancifulName("TestFanciful");
        c1.setStatus(ApplicationStatus.APPROVED);
        COLA c2 = new COLA(2, "B1", AlcoholType.WINE, "180002", ProductSource.IMPORTED);
        c2.setAlcoholContent(20);
        c2.setFancifulName("F1");
        c2.setStatus(ApplicationStatus.RECEIVED);
        COLA c3 = new COLA(3, "B2", AlcoholType.BEER, "180003", ProductSource.DOMESTIC);
        c3.setAlcoholContent(30);
        c3.setFancifulName("F3");
        c3.setStatus(ApplicationStatus.APPROVED);
        COLA c4 = new COLA(4, "Brand", AlcoholType.DISTILLEDSPIRITS, "180004", ProductSource.IMPORTED);
        c4.setAlcoholContent(40);
        c4.setFancifulName("Next");
        c4.setStatus(ApplicationStatus.REJECTED);
        COLA c5 = new COLA(5, "Other", AlcoholType.BEER, "180005", ProductSource.DOMESTIC);
        c5.setAlcoholContent(50);
        c5.setFancifulName("Fanciful");
        c5.setStatus(ApplicationStatus.NEEDS_CORRECTION);
        colas = Arrays.asList(c1, c2, c3, c4, c5);
        for (COLA c: colas) {
            storage.saveCOLA(c);
        }
        searchHandler = new COLASearchHandler();
        LogManager.getInstance().setShouldWrite(false);
    }

    @After
    public void teardown(){
        database.dropTable(COLA.DB_TABLE);
        database.dropTable(User.DB_TABLE);
        database.disconnect();
    }


    @Test
    public void receiveAllCOLAs() {
        searchHandler.receiveAllCOLAs();
        List<COLA> all = searchHandler.filteredSearch(null);
        assertEquals(colas, all);
    }

    @Test
    public void filteredSearch() {
        SearchFilter filter = new FuzzyBrandNameFilter("Brand");
        List<COLA> results = searchHandler.filteredSearch(filter);
        assertEquals(2, results.size());
        assertTrue(results.contains(colas.get(0)));
        assertTrue(results.contains(colas.get(3)));

        filter = filter.or(new FuzzyFancifulNameFilter("Fanciful"));
        results = searchHandler.filteredSearch(filter);
        assertEquals(3, results.size());
        assertTrue(results.contains(colas.get(0)));
        assertTrue(results.contains(colas.get(3)));
        assertTrue(results.contains(colas.get(4)));

        filter = filter.and(new StatusFilter(ApplicationStatus.APPROVED));
        results = searchHandler.filteredSearch(filter);
        assertEquals(1, results.size());
        assertTrue(results.contains(colas.get(0)));

        filter = new StatusFilter(ApplicationStatus.APPROVED);
        results = searchHandler.filteredSearch(filter);
        assertEquals(2, results.size());
        assertTrue(results.contains(colas.get(0)));
        assertTrue(results.contains(colas.get(2)));

        filter = new TypeFilter(AlcoholType.BEER);
        results = searchHandler.filteredSearch(filter);
        assertEquals(3, results.size());
        assertTrue(results.contains(colas.get(0)));
        assertTrue(results.contains(colas.get(2)));
        assertTrue(results.contains(colas.get(4)));
    }
}