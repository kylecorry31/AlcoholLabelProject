package com.emeraldElves.alcohollabelproject.database;

import java.sql.ResultSet;

public interface IDatabase {

    void connect();

    void connect(String username, String password) throws AuthenticationException;

    void disconnect();

    boolean doesTableExist(String tableName);

    void createTable(String tableName, String[] columns);

    void dropTable(String tableName);

    ResultSet query(String table, String[] columns, String whereClause, String[] whereArgs, String orderBy);

    void insert(String table, String[] values);

    void update(String table, String[] values, String whereClause, String[] whereArgs);

    void delete(String table, String whereClause, String[] whereArgs);

}
