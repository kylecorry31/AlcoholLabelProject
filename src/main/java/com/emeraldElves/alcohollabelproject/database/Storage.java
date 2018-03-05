package com.emeraldElves.alcohollabelproject.database;

import com.emeraldElves.alcohollabelproject.Data.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Storage {

    private IDatabase database;
    private final String ALCOHOL_VALUES = String.format("( %s, %s, %s, %s, %s, %s )", AlcoholInfo.DB_BRAND_NAME, AlcoholInfo.DB_ALCOHOL_TYPE,
            AlcoholInfo.DB_SERIAL_NUMBER, AlcoholInfo.DB_ORIGIN,
            AlcoholInfo.DB_ALCOHOL_CONTENT, AlcoholInfo.DB_FANCICUL_NAME);
    private final String USER_VALUES = String.format("( %s, %s, %s )", User.DB_NAME, User.DB_PASSWORD, User.DB_USER_TYPE);

    private static final String DELIMITER = ":::";

    private Storage() {
    }

    public static Storage getInstance() {
        return StorageHolder.instance;
    }


    // Insert
    public void saveAlcoholInfo(AlcoholInfo info) {
        database.insert(AlcoholInfo.DB_TABLE + ALCOHOL_VALUES, new String[]{
                ApacheDerbyDatabase.addQuotes(info.getBrandName()),
                ApacheDerbyDatabase.addQuotes(info.getType().toString()),
                ApacheDerbyDatabase.addQuotes(info.getSerialNumber()),
                ApacheDerbyDatabase.addQuotes(info.getOrigin().toString()),
                String.valueOf(info.getAlcoholContent()),
                ApacheDerbyDatabase.addQuotes(info.getFancifulName())
        });
    }

    public void saveUser(User user){
        database.insert(User.DB_TABLE + USER_VALUES, new String[]{
                ApacheDerbyDatabase.addQuotes(user.getName()),
                ApacheDerbyDatabase.addQuotes(user.getPassword()),
                ApacheDerbyDatabase.addQuotes(user.getType().toString())
        });
    }

    // Update
    public void updateAlcoholInfo(AlcoholInfo info) {
        String values[] = new String[]{
                String.format("%s = '%s'", AlcoholInfo.DB_BRAND_NAME, info.getBrandName()),
                String.format("%s = '%s'", AlcoholInfo.DB_ALCOHOL_TYPE, info.getType().toString()),
                String.format("%s = '%s'", AlcoholInfo.DB_SERIAL_NUMBER, info.getSerialNumber()),
                String.format("%s = '%s'", AlcoholInfo.DB_ORIGIN, info.getOrigin().toString()),
                String.format("%s = %f", AlcoholInfo.DB_ALCOHOL_CONTENT, info.getAlcoholContent()),
                String.format("%s = '%s'", AlcoholInfo.DB_FANCICUL_NAME, info.getFancifulName()),
        };

        database.update(AlcoholInfo.DB_TABLE, values, AlcoholInfo.DB_ID + " = " + info.getId(), null);
    }

    public void updateUser(User user){
        String values[] = new String[]{
                String.format("%s = '%s'", User.DB_NAME, user.getName()),
                String.format("%s = '%s'", User.DB_PASSWORD, user.getPassword()),
                String.format("%s = '%s'", User.DB_USER_TYPE, user.getType().toString()),
        };

        database.update(User.DB_TABLE, values, User.DB_ID + " = " + user.getId(), null);
    }

    // Delete
    public void deleteAlcoholInfo(AlcoholInfo info) {
        database.delete(AlcoholInfo.DB_TABLE, AlcoholInfo.DB_ID + " = " + info.getId(), null);
    }

    public void deleteUser(User user){
        database.delete(User.DB_TABLE, User.DB_ID + " = " + user.getId(), null);
    }


    // Get
    public List<AlcoholInfo> getAllAlcoholInfo() {
        ResultSet resultSet = database.query(AlcoholInfo.DB_TABLE, null, null, null, null);
        return getAlcoholInfos(resultSet);
    }

    public AlcoholInfo getAlcoholInfo(long id) {
        ResultSet resultSet = database.query(AlcoholInfo.DB_TABLE, null, AlcoholInfo.DB_ID + " = " + id, null, null);
        return getAlcoholInfo(resultSet);
    }

    public User getUser(String username, String password) {
        ResultSet resultSet = database.query(User.DB_TABLE, null,
                User.DB_NAME + " = ? AND " + User.DB_PASSWORD + " = ?",
                new String[]{username, password},
                null
        );
        return getUser(resultSet);
    }

    public User getUser(long id) {
        ResultSet resultSet = database.query(User.DB_TABLE, null, User.DB_ID + " = " + id, null, null);
        return getUser(resultSet);
    }

    public List<User> getAllUsers() {
        ResultSet resultSet = database.query(User.DB_TABLE, null, null, null, null);
        return getUsers(resultSet);
    }

    // Result set parsing
    private List<AlcoholInfo> getAlcoholInfos(ResultSet resultSet) {
        List<AlcoholInfo> alcohol = new LinkedList<>();
        if (resultSet == null) {
            return alcohol;
        }

        AlcoholInfo info;

        while ((info = getAlcoholInfo(resultSet)) != null) {
            alcohol.add(info);
        }

        return alcohol;
    }

    private AlcoholInfo getAlcoholInfo(ResultSet resultSet) {
        try {
            if (resultSet == null || !resultSet.next()) {
                return null;
            }

            String brandName = resultSet.getString(AlcoholInfo.DB_BRAND_NAME);
            AlcoholType type = AlcoholType.valueOf(resultSet.getString(AlcoholInfo.DB_ALCOHOL_TYPE));
            String serialNumber = resultSet.getString(AlcoholInfo.DB_SERIAL_NUMBER);
            ProductSource origin = ProductSource.valueOf(resultSet.getString(AlcoholInfo.DB_ORIGIN));
            long id = resultSet.getLong(AlcoholInfo.DB_ID);
            double alcoholContent = resultSet.getDouble(AlcoholInfo.DB_ALCOHOL_CONTENT);
            String fancifulName = resultSet.getString(AlcoholInfo.DB_FANCICUL_NAME);

            AlcoholInfo alcoholInfo = new AlcoholInfo(brandName, type, serialNumber, origin);
            alcoholInfo.setId(id);
            alcoholInfo.setAlcoholContent(alcoholContent);
            alcoholInfo.setFancifulName(fancifulName);

            return alcoholInfo;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<User> getUsers(ResultSet resultSet) {
        List<User> users = new LinkedList<>();
        if (resultSet == null) {
            return users;
        }

        User user;

        while ((user = getUser(resultSet)) != null) {
            users.add(user);
        }

        return users;
    }

    private User getUser(ResultSet resultSet) {
        try {
            if (resultSet == null || !resultSet.next()) {
                return null;
            }

            String name = resultSet.getString(User.DB_NAME);
            String password = resultSet.getString(User.DB_PASSWORD);
            UserType type = UserType.valueOf(resultSet.getString(User.DB_USER_TYPE));
            long id = resultSet.getLong(User.DB_ID);

            User user = new User(name, password, type);
            user.setId(id);

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setDatabase(IDatabase database) {
        this.database = database;
        database.connect();
        if (!database.doesTableExist(AlcoholInfo.DB_TABLE)) {
            database.createTable(AlcoholInfo.DB_TABLE, new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", AlcoholInfo.DB_ID),
                    String.format("%s VARCHAR (128)", AlcoholInfo.DB_BRAND_NAME),
                    String.format("%s VARCHAR (16)", AlcoholInfo.DB_ALCOHOL_TYPE),
                    String.format("%s VARCHAR (8)", AlcoholInfo.DB_SERIAL_NUMBER),
                    String.format("%s VARCHAR (8)", AlcoholInfo.DB_ORIGIN),
                    String.format("%s DOUBLE", AlcoholInfo.DB_ALCOHOL_CONTENT),
                    String.format("%s VARCHAR (128)", AlcoholInfo.DB_FANCICUL_NAME),
            });
            System.out.println("Created table " + AlcoholInfo.DB_TABLE);
        }

        if (!database.doesTableExist(User.DB_TABLE)) {
            database.createTable(User.DB_TABLE, new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", User.DB_ID),
                    String.format("%s VARCHAR (128)", User.DB_NAME),
                    String.format("%s VARCHAR (128)", User.DB_PASSWORD),
                    String.format("%s VARCHAR (10)", User.DB_USER_TYPE),
            });
            System.out.println("Created table " + User.DB_TABLE);
        }
    }


    private static class StorageHolder {
        private static final Storage instance = new Storage();
    }
}
