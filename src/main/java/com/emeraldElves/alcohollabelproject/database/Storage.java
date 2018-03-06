package com.emeraldElves.alcohollabelproject.database;

import com.emeraldElves.alcohollabelproject.Data.*;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Storage {

    private IDatabase database;
    private final String ALCOHOL_VALUES = String.format("( %s, %s, %s, %s, %s, %s, %s, %s, %s, %s )",
            COLA.DB_BRAND_NAME, COLA.DB_ALCOHOL_TYPE,
            COLA.DB_SERIAL_NUMBER, COLA.DB_ORIGIN,
            COLA.DB_ALCOHOL_CONTENT, COLA.DB_FANCIFUL_NAME,
            COLA.DB_SUBMISSION_DATE, COLA.DB_STATUS,
            COLA.DB_APPROVAL_DATE, COLA.DB_LABEL_IMAGE);
    private final String USER_VALUES = String.format("( %s, %s, %s )", User.DB_NAME, User.DB_PASSWORD, User.DB_USER_TYPE);

    private static final String DELIMITER = ":::";

    private Storage() {
    }

    public static Storage getInstance() {
        return StorageHolder.instance;
    }


    // Insert
    public void saveCOLA(COLA info) {
        database.insert(COLA.DB_TABLE + ALCOHOL_VALUES, new String[]{
                ApacheDerbyDatabase.addQuotes(info.getBrandName()),
                ApacheDerbyDatabase.addQuotes(info.getType().toString()),
                ApacheDerbyDatabase.addQuotes(info.getSerialNumber()),
                ApacheDerbyDatabase.addQuotes(info.getOrigin().toString()),
                String.valueOf(info.getAlcoholContent()),
                ApacheDerbyDatabase.addQuotes(info.getFancifulName()),
                ApacheDerbyDatabase.addQuotes(info.getSubmissionDate().toString()),
                ApacheDerbyDatabase.addQuotes(info.getStatus().toString()),
                ApacheDerbyDatabase.addQuotes(info.getApprovalDate().toString()),
                ApacheDerbyDatabase.addQuotes(info.getLabelImageFilename()),
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
    public void updateCOLA(COLA info) {
        String values[] = new String[]{
                String.format("%s = '%s'", COLA.DB_BRAND_NAME, info.getBrandName()),
                String.format("%s = '%s'", COLA.DB_ALCOHOL_TYPE, info.getType().toString()),
                String.format("%s = '%s'", COLA.DB_SERIAL_NUMBER, info.getSerialNumber()),
                String.format("%s = '%s'", COLA.DB_ORIGIN, info.getOrigin().toString()),
                String.format("%s = %f", COLA.DB_ALCOHOL_CONTENT, info.getAlcoholContent()),
                String.format("%s = '%s'", COLA.DB_FANCIFUL_NAME, info.getFancifulName()),
                String.format("%s = '%s'", COLA.DB_SUBMISSION_DATE, info.getSubmissionDate().toString()),
                String.format("%s = '%s'", COLA.DB_STATUS, info.getStatus().toString()),
                String.format("%s = '%s'", COLA.DB_APPROVAL_DATE, info.getApprovalDate().toString()),
                String.format("%s = '%s'", COLA.DB_LABEL_IMAGE, info.getLabelImageFilename()),
        };

        database.update(COLA.DB_TABLE, values, COLA.DB_ID + " = " + info.getId(), null);
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
    public void deleteAlcoholInfo(COLA info) {
        database.delete(COLA.DB_TABLE, COLA.DB_ID + " = " + info.getId(), null);
    }

    public void deleteUser(User user){
        database.delete(User.DB_TABLE, User.DB_ID + " = " + user.getId(), null);
    }


    // Get
    public List<COLA> getAllCOLAs() {
        ResultSet resultSet = database.query(COLA.DB_TABLE, null, null, null, null);
        return getCOLAs(resultSet);
    }

    public COLA getCOLA(long id) {
        ResultSet resultSet = database.query(COLA.DB_TABLE, null, COLA.DB_ID + " = " + id, null, null);
        return getCOLA(resultSet);
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
    private List<COLA> getCOLAs(ResultSet resultSet) {
        List<COLA> alcohol = new LinkedList<>();
        if (resultSet == null) {
            return alcohol;
        }

        COLA info;

        while ((info = getCOLA(resultSet)) != null) {
            alcohol.add(info);
        }

        return alcohol;
    }

    private COLA getCOLA(ResultSet resultSet) {
        try {
            if (resultSet == null || !resultSet.next()) {
                return null;
            }

            String brandName = resultSet.getString(COLA.DB_BRAND_NAME);
            AlcoholType type = AlcoholType.valueOf(resultSet.getString(COLA.DB_ALCOHOL_TYPE));
            String serialNumber = resultSet.getString(COLA.DB_SERIAL_NUMBER);
            ProductSource origin = ProductSource.valueOf(resultSet.getString(COLA.DB_ORIGIN));
            long id = resultSet.getLong(COLA.DB_ID);
            double alcoholContent = resultSet.getDouble(COLA.DB_ALCOHOL_CONTENT);
            String fancifulName = resultSet.getString(COLA.DB_FANCIFUL_NAME);
            LocalDate submissionDate = resultSet.getDate(COLA.DB_SUBMISSION_DATE).toLocalDate();
            ApplicationStatus status = ApplicationStatus.valueOf(resultSet.getString(COLA.DB_STATUS));
            LocalDate approvalDate = resultSet.getDate(COLA.DB_APPROVAL_DATE).toLocalDate();
            ILabelImage labelImage = new ProxyLabelImage(resultSet.getString(COLA.DB_LABEL_IMAGE));

            COLA COLA = new COLA(brandName, type, serialNumber, origin);
            COLA.setId(id);
            COLA.setAlcoholContent(alcoholContent);
            COLA.setFancifulName(fancifulName);
            COLA.setSubmissionDate(submissionDate);
            COLA.setStatus(status);
            COLA.setApprovalDate(approvalDate);
            COLA.setLabelImage(labelImage);

            return COLA;
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
        if (!database.doesTableExist(COLA.DB_TABLE)) {
            database.createTable(COLA.DB_TABLE, new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", COLA.DB_ID),
                    String.format("%s VARCHAR (128)", COLA.DB_BRAND_NAME),
                    String.format("%s VARCHAR (16)", COLA.DB_ALCOHOL_TYPE),
                    String.format("%s VARCHAR (6)", COLA.DB_SERIAL_NUMBER),
                    String.format("%s VARCHAR (8)", COLA.DB_ORIGIN),
                    String.format("%s DOUBLE", COLA.DB_ALCOHOL_CONTENT),
                    String.format("%s VARCHAR (128)", COLA.DB_FANCIFUL_NAME),
                    String.format("%s DATE", COLA.DB_SUBMISSION_DATE),
                    String.format("%s VARCHAR (16)", COLA.DB_STATUS),
                    String.format("%s DATE", COLA.DB_APPROVAL_DATE),
                    String.format("%s VARCHAR (256)", COLA.DB_LABEL_IMAGE),
            });
            System.out.println("Created table " + COLA.DB_TABLE);
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
