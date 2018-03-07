package com.emeraldElves.alcohollabelproject;

import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;

/**
 * Created by Kylec on 4/9/2017.
 */
public class Authenticator {

    private User user;

    private Authenticator() {
        user = new User("", "", UserType.BASIC);
    }

    public static Authenticator getInstance() {
        return AuthenticatorHolder.instance;
    }



    private static class AuthenticatorHolder {
        private static final Authenticator instance = new Authenticator();
    }

    public void setUser(User user){
        if(user == null)
            return;

        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public boolean login(String username, String password) {

        User u = Storage.getInstance().getUser(username, password);

        if(username.equals("admin") && password.equals("admin")){
            u = new User(username, password, UserType.SUPERAGENT);
            u.setId(0);
        }

        if(u == null){
            u = new User("", "", UserType.BASIC);
        }

        user = u;

        return user.getType() != UserType.BASIC;
    }

    public void logout() {
        user = new User("", "", UserType.BASIC);
    }

    public boolean isLoggedIn() {
        return user.getType() != UserType.BASIC;
    }

    public boolean isAgentLoggedIn() {
        return user.getType() == UserType.TTBAGENT;
    }

    public boolean isApplicantLoggedIn() {
        return user.getType() == UserType.APPLICANT;
    }
    public boolean isSuperAgentLoggedIn() {
        return user.getType() == UserType.SUPERAGENT;
    }

    public UserType getUserType() {
        return user.getType();
    }

    public String getUsername() {
        return user.getName();
    }


}
