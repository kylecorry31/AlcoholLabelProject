package com.emeraldElves.alcohollabelproject;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.Data.ProductSource;
import com.emeraldElves.alcohollabelproject.IDGenerator.*;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PopulateDatabase {

    private static final String FILE_NAME = "/ttbsample.tsv";

    public static void main(String[] args) {

        LogManager.getInstance().setShouldWrite(false);

        InputStream ttbFile = PopulateDatabase.class.getResourceAsStream("/ttbsample.tsv");

        Scanner scanner = new Scanner(ttbFile);

        StructuredFileReader reader = new TSVReader(scanner);

        IDatabase database = new ApacheDerbyDatabase("cola.db");
        database.connect();
        database.dropTable(COLA.DB_TABLE);
        database.dropTable(IDCounter.DB_TABLE);
        Storage.getInstance().setDatabase(database);
        ApplicationIDGenerator generator = new TTBIDGenerator();

        for (int i = 0; i < reader.getCount(); i++) {
            long id = reader.getLong("CFM_APPL_ID", i, generator.generateID());
            String brandName = reader.getString("PRODUCT_NAME", i).replace("\"\"\"", "");
            AlcoholType type = getType(reader.getString("PRODUCT_TYPE", i));
            String serialNo = reader.getString("SERIAL_NUM", i).replace("-", "");
            ProductSource origin = getSource(reader.getString("ORIGIN_CODE", i));
            String fanciful = reader.getString("FANCIFUL_NAME", i).replace("\"\"\"", "");
            double percent = reader.getDouble("ALCOHOL_PCT", i, 0);

            COLA cola = new COLA(id, brandName, type, serialNo, origin);
            cola.setStatus(ApplicationStatus.APPROVED);
            cola.setFancifulName(fanciful);
            cola.setAlcoholContent(percent);
            Storage.getInstance().saveCOLA(cola);
            System.out.println(String.format("Saved COLA %d (%d/%d)", id, i, reader.getCount()));
        }

        database.disconnect();
    }

    private static ProductSource getSource(String sourceStr){
        try {
            int val = Integer.valueOf(sourceStr);
            if(val <= 49){
                return ProductSource.DOMESTIC;
            }
        } catch (Exception e){
            List<String> domestic = Arrays.asList("4A", "4B", "4E");
            if(domestic.contains(sourceStr.toUpperCase())){
                return ProductSource.DOMESTIC;
            }
        }
        return ProductSource.IMPORTED;
    }

    private static AlcoholType getType(String typeStr){
        if(typeStr.equalsIgnoreCase("Malt Beverage")){
            return AlcoholType.BEER;
        } else if (typeStr.equalsIgnoreCase("Distilled Spirits")){
            return AlcoholType.DISTILLEDSPIRITS;
        } else {
            return AlcoholType.WINE;
        }
    }

}
