package com.emeraldElves.alcohollabelproject.database;

import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;

public class ApacheDerbyDatabase implements IDatabase {

    private Connection connection;
    private Statement statement;
    private String dbName;
    private boolean isOpen = false;

    public ApacheDerbyDatabase(String dbName){
        this.dbName = dbName;
    }

    @Override
    public void connect() {
        if(isOpen){
            return;
        }
        try {
            DriverManager.registerDriver(new EmbeddedDriver());
            connection = DriverManager.getConnection("jdbc:derby:" + dbName + ";create=true");
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            isOpen = true;
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + dbName + "\n" +
                    e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void connect(String username, String password) throws AuthenticationException {
        if(isOpen){
            return;
        }
        try {
            DriverManager.registerDriver(new EmbeddedDriver());
            int dbIndex = dbName.indexOf(".db");
            String newDbName = dbName;
            if(dbIndex == -1){
                newDbName += String.format("-%s.db", username);
            } else {
                newDbName = newDbName.replaceFirst(".db", String.format("-%s.db", username));
            }
            connection = DriverManager.getConnection(String.format("jdbc:derby:%s;create=true;dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;bootPassword=%s;", newDbName, password));
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            dbName = newDbName;
            isOpen = true;
        } catch (SQLException e) {
//            System.out.println("Could not connect to database: " + dbName + "\n" +
//                    e.getMessage());
            throw new AuthenticationException();
        }
    }

    @Override
    public void disconnect() {
        if(!isOpen){
            return;
        }
        try {
            statement.close();
            connection.close();
            DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
            isOpen = false;
        } catch (SQLException e) {
            // Empty
        }
    }

    @Override
    public boolean doesTableExist(String tableName) {
        if(!isOpen){
            return false;
        }
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                if (rs.getString(3).toLowerCase().equals(tableName.toLowerCase())) {
                    return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void createTable(String tableName, String[] columns) {
        if(!isOpen || doesTableExist(tableName)){
            return;
        }

        StringBuilder entityString = new StringBuilder();

        entityString.append(String.join(", ", columns));

        try {
            statement.execute("CREATE TABLE " + tableName + "(\n" + entityString + "\n)");
            connection.commit();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Could not create table");
        }
    }

    @Override
    public void dropTable(String tableName) {
        if(!isOpen || !doesTableExist(tableName)){
            return;
        }
        try {
            String s = String.format("DROP TABLE %s", tableName);
            statement.execute(s);
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet query(String table, String[] columns, String whereClause, String[] whereArgs, String orderBy) {
        if(!isOpen){
            return null;
        }
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT ");
        if(columns != null) {
            for (int i = 0; i < columns.length; i++) {
                queryString.append(columns[i]);
                if (i != (columns.length - 1)) {
                    queryString.append(",");
                }
            }
        } else {
            queryString.append("*");
        }

        queryString.append(" FROM ");
        queryString.append(table);

        if(whereClause != null) {
            queryString.append(" WHERE ");

            if(whereClause.contains("?") && whereArgs != null && whereArgs.length > 0){
                for (String whereArg : whereArgs) {
                    whereClause = whereClause.replaceFirst("\\?", "'" + whereArg + "'");
                }
            }

            queryString.append(whereClause);
        }

        if(orderBy != null){
            queryString.append(" ORDER BY ");
            queryString.append(orderBy);
        }

        try {
            return statement.executeQuery(queryString.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(String table, String[] values) {
        if(!isOpen){
            return;
        }
        StringBuilder queryString = new StringBuilder("INSERT INTO ");
        queryString.append(table);
        queryString.append(" VALUES ( ");
        for (int i = 0; i < values.length; i++) {
            queryString.append(values[i]);
            if (i != values.length - 1){
                queryString.append(", ");
            }
        }
        queryString.append(" )");

        try {
            statement.execute(queryString.toString());
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(String table, String[] values, String whereClause, String[] whereArgs) {
        if(!isOpen){
            return;
        }
        StringBuilder queryString = new StringBuilder("UPDATE ");
        queryString.append(table);
        queryString.append(" SET ");
        for (int i = 0; i < values.length; i++) {
            queryString.append(values[i]);
            if (i != values.length - 1){
                queryString.append(", ");
            }
        }

        if(whereClause != null) {
            queryString.append(" WHERE ");

            if(whereClause.contains("?") && whereArgs != null && whereArgs.length > 0){
                for (String whereArg : whereArgs) {
                    whereClause = whereClause.replaceFirst("\\?", "'" + whereArg + "'");
                }
            }

            queryString.append(whereClause);
        }


        try {
            statement.execute(queryString.toString());
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String table, String whereClause, String[] whereArgs) {
        if(!isOpen){
            return;
        }
        StringBuilder queryString = new StringBuilder("DELETE FROM ");
        queryString.append(table);

        if(whereClause != null) {
            queryString.append(" WHERE ");

            if(whereClause.contains("?") && whereArgs != null && whereArgs.length > 0){
                for (String whereArg : whereArgs) {
                    whereClause = whereClause.replaceFirst("\\?", "'" + whereArg + "'");
                }
            }

            queryString.append(whereClause);
        } else {
            queryString.append(" WHERE 1 = 1");
        }


        try {
            statement.execute(queryString.toString());
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String addQuotes(String s){
        return String.format("'%s'", s.replaceAll("'", "''"));
    }

}
