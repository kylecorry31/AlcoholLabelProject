package com.emeraldElves.alcohollabelproject.database;

import com.emeraldElves.alcohollabelproject.Data.*;
import com.emeraldElves.alcohollabelproject.IDGenerator.IDCounter;
import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Storage {

    private IDatabase database;
    private final String ALCOHOL_VALUES = String.format("( %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s )",
            COLA.DB_ID, COLA.DB_BRAND_NAME, COLA.DB_ALCOHOL_TYPE,
            COLA.DB_SERIAL_NUMBER, COLA.DB_ORIGIN,
            COLA.DB_ALCOHOL_CONTENT, COLA.DB_FANCIFUL_NAME,
            COLA.DB_SUBMISSION_DATE, COLA.DB_STATUS,
            COLA.DB_APPROVAL_DATE, COLA.DB_LABEL_IMAGE,
            COLA.DB_APPLICANT_ID, COLA.DB_ASSIGNED_TO,
            COLA.DB_FORMULA, COLA.DB_WINE_PH,
            COLA.DB_WINE_VINTAGE_YEAR, COLA.DB_LAST_UPDATED,
            COLA.DB_EXPIRATION_DATE);
    private final String USER_VALUES = String.format("( %s, %s, %s, %s, %s, %s, %s, %s, %s, %s )",
            User.DB_NAME, User.DB_PASSWORD,
            User.DB_USER_TYPE, User.DB_APPROVED,
            User.DB_COMPANY, User.DB_ADDRESS,
            User.DB_PHONE, User.DB_EMAIL,
            User.DB_REP_ID, User.DB_PERMIT_NO);
    private final String COUNTER_VALUES = String.format("( %s, %s )", IDCounter.DB_COUNTER, IDCounter.DB_LAST_MODIFIED);


    private static final String DELIMITER = ":::";

    private Storage() {
    }

    public static Storage getInstance() {
        return StorageHolder.instance;
    }


    // Insert
    public void saveCOLA(COLA info) {
        database.insert(COLA.DB_TABLE + ALCOHOL_VALUES, new String[]{
                String.valueOf(info.getId()),
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
                String.valueOf(info.getApplicantID()),
                String.valueOf(info.getAssignedTo()),
                String.valueOf(info.getFormula()),
                String.valueOf(info.getWinePH()),
                String.valueOf(info.getVintageYear()),
                ApacheDerbyDatabase.addQuotes(info.getLastUpdated().toString()),
                ApacheDerbyDatabase.addQuotes(info.getExpirationDate().toString()),
        });
    }

    public void saveUser(User user){
        database.insert(User.DB_TABLE + USER_VALUES, new String[]{
                ApacheDerbyDatabase.addQuotes(user.getName()),
                ApacheDerbyDatabase.addQuotes(user.getPassword()),
                ApacheDerbyDatabase.addQuotes(user.getType().toString()),
                String.valueOf(user.isApproved()),
                ApacheDerbyDatabase.addQuotes(user.getCompany()),
                ApacheDerbyDatabase.addQuotes(user.getAddress()),
                ApacheDerbyDatabase.addQuotes(user.getPhoneNumber().getPhoneNumber()),
                ApacheDerbyDatabase.addQuotes(user.getEmail().getEmailAddress()),
                String.valueOf(user.getRepID()),
                ApacheDerbyDatabase.addQuotes(user.getPermitNo()),
        });
    }

    public void saveCounter(IDCounter counter){
        database.insert(IDCounter.DB_TABLE + COUNTER_VALUES, new String[]{
                String.valueOf(counter.getCount()),
                ApacheDerbyDatabase.addQuotes(counter.getLastModified().toString()),
        });
    }

    // Update
    public void updateCOLA(COLA info) {
        String values[] = new String[]{
                String.format("%s = %d", COLA.DB_ID, info.getId()),
                String.format("%s = '%s'", COLA.DB_BRAND_NAME, info.getBrandName().replaceAll("'", "''")),
                String.format("%s = '%s'", COLA.DB_ALCOHOL_TYPE, info.getType().toString()),
                String.format("%s = '%s'", COLA.DB_SERIAL_NUMBER, info.getSerialNumber()),
                String.format("%s = '%s'", COLA.DB_ORIGIN, info.getOrigin().toString()),
                String.format("%s = %f", COLA.DB_ALCOHOL_CONTENT, info.getAlcoholContent()),
                String.format("%s = '%s'", COLA.DB_FANCIFUL_NAME, info.getFancifulName().replaceAll("'", "''")),
                String.format("%s = '%s'", COLA.DB_SUBMISSION_DATE, info.getSubmissionDate().toString()),
                String.format("%s = '%s'", COLA.DB_STATUS, info.getStatus().toString()),
                String.format("%s = '%s'", COLA.DB_APPROVAL_DATE, info.getApprovalDate().toString()),
                String.format("%s = '%s'", COLA.DB_LABEL_IMAGE, info.getLabelImageFilename()),
                String.format("%s = %d", COLA.DB_APPLICANT_ID, info.getApplicantID()),
                String.format("%s = %d", COLA.DB_ASSIGNED_TO, info.getAssignedTo()),
                String.format("%s = %d", COLA.DB_FORMULA, info.getFormula()),
                String.format("%s = %f", COLA.DB_WINE_PH, info.getWinePH()),
                String.format("%s = %d", COLA.DB_WINE_VINTAGE_YEAR, info.getVintageYear()),
                String.format("%s = '%s'", COLA.DB_LAST_UPDATED, info.getLastUpdated()),
                String.format("%s = '%s'", COLA.DB_EXPIRATION_DATE, info.getExpirationDate()),
        };

        database.update(COLA.DB_TABLE, values, COLA.DB_ID + " = " + info.getId(), null);
    }

    public void updateUser(User user){
        String values[] = new String[]{
                String.format("%s = '%s'", User.DB_NAME, user.getName().replaceAll("'", "''")),
                String.format("%s = '%s'", User.DB_PASSWORD, user.getPassword().replaceAll("'", "''")),
                String.format("%s = '%s'", User.DB_USER_TYPE, user.getType().toString()),
                String.format("%s = %b", User.DB_APPROVED, user.isApproved()),
                String.format("%s = '%s'", User.DB_COMPANY, user.getCompany().replaceAll("'", "''")),
                String.format("%s = '%s'", User.DB_ADDRESS, user.getAddress().replaceAll("'", "''")),
                String.format("%s = '%s'", User.DB_PHONE, user.getPhoneNumber().getPhoneNumber()),
                String.format("%s = '%s'", User.DB_EMAIL, user.getEmail().getEmailAddress().replaceAll("'", "''")),
                String.format("%s = %d", User.DB_REP_ID, user.getRepID()),
                String.format("%s = '%s'", User.DB_PERMIT_NO, user.getPermitNo()),
        };

        database.update(User.DB_TABLE, values, User.DB_ID + " = " + user.getId(), null);
    }

    public void updateCounter(IDCounter counter){
        String values[] = new String[]{
            String.format("%s = %d", IDCounter.DB_COUNTER, counter.getCount()),
            String.format("%s = '%s'", IDCounter.DB_LAST_MODIFIED, counter.getLastModified().toString()),
        };

        database.update(IDCounter.DB_TABLE, values, IDCounter.DB_ID + " = " + IDCounter.ID, null);
    }

    // Delete
    public void deleteAlcoholInfo(COLA info) {
        database.delete(COLA.DB_TABLE, COLA.DB_ID + " = " + info.getId(), null);
    }

    public void deleteUser(User user){
        database.delete(User.DB_TABLE, User.DB_ID + " = " + user.getId(), null);
    }

    public void deleteCounter(IDCounter counter){
        database.delete(IDCounter.DB_TABLE, IDCounter.DB_COUNTER + " = " + IDCounter.ID, null);
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

    public List<COLA> getCOLAsByUser(User user){
        ResultSet resultSet = database.query(COLA.DB_TABLE, null, COLA.DB_APPLICANT_ID + " = " + user.getId(), null, null);
        return getCOLAs(resultSet);
    }

    public List<COLA> getAssignedCOLAs(User user){
        ResultSet resultSet = database.query(COLA.DB_TABLE, null, COLA.DB_ASSIGNED_TO + " = " + user.getId(), null, null);
        return getCOLAs(resultSet);
    }

    public User getUser(String email, String password) {
        ResultSet resultSet = database.query(User.DB_TABLE, null,
                User.DB_EMAIL + " = ? AND " + User.DB_PASSWORD + " = ?",
                new String[]{email, password},
                null
        );
        return getUser(resultSet);
    }

    public User getUser(String email) {
        ResultSet resultSet = database.query(User.DB_TABLE, null, User.DB_EMAIL + " = ?", new String[]{email}, null);
        User u = getUser(resultSet);
        if(u != null)
            u.setPassword("");
        return u;
    }

    public User getUser(long id) {
        ResultSet resultSet = database.query(User.DB_TABLE, null, User.DB_ID + " = " + id, null, null);
        User u = getUser(resultSet);
        if(u != null)
            u.setPassword("");
        return u;
    }

    public List<User> getAllUsers() {
        ResultSet resultSet = database.query(User.DB_TABLE, null, null, null, null);
        return getUsers(resultSet);
    }

    public IDCounter getCounter(){
        ResultSet resultSet = database.query(IDCounter.DB_TABLE, null, null, null, null);
        return getCounter(resultSet);
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
            long applicantID = resultSet.getLong(COLA.DB_APPLICANT_ID);
            long assignedTo = resultSet.getLong(COLA.DB_ASSIGNED_TO);
            long formula = resultSet.getLong(COLA.DB_FORMULA);
            int vintageYear = resultSet.getInt(COLA.DB_WINE_VINTAGE_YEAR);
            double winePH = resultSet.getDouble(COLA.DB_WINE_PH);
            LocalDate expirationDate = resultSet.getDate(COLA.DB_EXPIRATION_DATE).toLocalDate();
            LocalDate lastUpdated = resultSet.getDate(COLA.DB_LAST_UPDATED).toLocalDate();

            COLA cola = new COLA(id, brandName, type, serialNumber, origin);
            cola.setAlcoholContent(alcoholContent);
            cola.setFancifulName(fancifulName);
            cola.setSubmissionDate(submissionDate);
            cola.setStatus(status);
            cola.setApprovalDate(approvalDate);
            cola.setLabelImage(labelImage);
            cola.setApplicantID(applicantID);
            cola.setAssignedTo(assignedTo);
            cola.setFormula(formula);
            cola.setVintageYear(vintageYear);
            cola.setWinePH(winePH);
            cola.setExpirationDate(expirationDate);
            cola.setLastUpdated(lastUpdated);

            return cola;
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
            boolean approved = resultSet.getBoolean(User.DB_APPROVED);
            String company = resultSet.getString(User.DB_COMPANY);
            String address = resultSet.getString(User.DB_ADDRESS);
            PhoneNumber phoneNumber = new PhoneNumber(resultSet.getString(User.DB_PHONE));
            String emailAddress = resultSet.getString(User.DB_EMAIL);
            long repID = resultSet.getLong(User.DB_REP_ID);
            String permitNo = resultSet.getString(User.DB_PERMIT_NO);

            User user = new User(emailAddress, name, password, type);
            user.setId(id);
            user.setApproved(approved);
            user.setCompany(company);
            user.setAddress(address);
            user.setPhoneNumber(phoneNumber);
            user.setRepID(repID);
            user.setPermitNo(permitNo);

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IDCounter getCounter(ResultSet resultSet){
        try {
            if (resultSet == null || !resultSet.next()) {
                return new IDCounter(-1, LocalDate.now());
            }

            long count = resultSet.getLong(IDCounter.DB_COUNTER);
            LocalDate lastModified = resultSet.getDate(IDCounter.DB_LAST_MODIFIED).toLocalDate();

            return new IDCounter(count, lastModified);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new IDCounter(-1, LocalDate.now());
    }


    public void setDatabase(IDatabase database) {
        this.database = database;
        database.connect();
        if (!database.doesTableExist(COLA.DB_TABLE)) {
            database.createTable(COLA.DB_TABLE, new String[]{
                    String.format("%s BIGINT PRIMARY KEY NOT NULL", COLA.DB_ID),
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
                    String.format("%s BIGINT", COLA.DB_APPLICANT_ID),
                    String.format("%s BIGINT", COLA.DB_ASSIGNED_TO),
                    String.format("%s BIGINT", COLA.DB_FORMULA),
                    String.format("%s DOUBLE", COLA.DB_WINE_PH),
                    String.format("%s INT", COLA.DB_WINE_VINTAGE_YEAR),
                    String.format("%s DATE", COLA.DB_LAST_UPDATED),
                    String.format("%s DATE", COLA.DB_EXPIRATION_DATE),
            });
            LogManager.getInstance().log("Storage", "Created table " + COLA.DB_TABLE);
        }

        if (!database.doesTableExist(User.DB_TABLE)) {
            database.createTable(User.DB_TABLE, new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", User.DB_ID),
                    String.format("%s VARCHAR (128)", User.DB_NAME),
                    String.format("%s VARCHAR (128)", User.DB_PASSWORD),
                    String.format("%s VARCHAR (10)", User.DB_USER_TYPE),
                    String.format("%s BOOLEAN", User.DB_APPROVED),
                    String.format("%s VARCHAR (256)", User.DB_COMPANY),
                    String.format("%s VARCHAR (512)", User.DB_ADDRESS),
                    String.format("%s VARCHAR (11)", User.DB_PHONE),
                    String.format("%s VARCHAR (128) UNIQUE", User.DB_EMAIL),
                    String.format("%s BIGINT", User.DB_REP_ID),
                    String.format("%s VARCHAR (32)", User.DB_PERMIT_NO),
            });
            LogManager.getInstance().log("Storage", "Created table " + User.DB_TABLE);
        }

        if (!database.doesTableExist(IDCounter.DB_TABLE)) {
            database.createTable(IDCounter.DB_TABLE, new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", IDCounter.DB_ID),
                    String.format("%s BIGINT", IDCounter.DB_COUNTER),
                    String.format("%s DATE", IDCounter.DB_LAST_MODIFIED),
            });
            saveCounter(new IDCounter(-1, LocalDate.now()));
            LogManager.getInstance().log("Storage", "Created table " + IDCounter.DB_TABLE);
        }
    }


    private static class StorageHolder {
        private static final Storage instance = new Storage();
    }
}
