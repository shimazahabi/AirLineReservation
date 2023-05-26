package data;

import datamanager.WritableReadable;

/**
 * BaseUser is the parent class of user classes.
 */
public abstract class BaseUser implements WritableReadable<BaseUser> {
    private String username;
    private String password;

    public BaseUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}