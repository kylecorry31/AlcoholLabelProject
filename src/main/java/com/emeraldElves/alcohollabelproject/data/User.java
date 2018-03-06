package com.emeraldElves.alcohollabelproject.data;

import com.emeraldElves.alcohollabelproject.Data.UserType;

import java.util.Objects;

public class User {

    public static final String DB_TABLE = "users";
    public static final String DB_NAME = "name";
    public static final String DB_PASSWORD = "password";
    public static final String DB_USER_TYPE = "user_type";
    public static final String DB_ID = "id";

    private String name;
    private String password;
    private UserType type;
    private long id;

    public User(String name, String password, UserType type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                type == user.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, password, type, id);
    }
}
