package datamanager;

import data.User;

import java.util.ArrayList;

public class Users {
    private final ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public Users() {
    }

    public void addUser(String username, String password) {
        users.add(new User(username, password));
    }
    public String password(String username) {
        return findUser(username).getPassword();
    }

    public int charge(String username) {
        return findUser(username).getCharge();
    }

    public void changePassword(String username, String newPassword) {
        findUser(username).setPassword(newPassword);
    }

    public void updateCharge(String username, int newCharge) {
        findUser(username).setCharge(newCharge);
    }

    public User findUser(String username) {
        for (User user : users) {
            if (username.equals(user.getUsername())){
                return user;
            }
        }
        return null;
    }
}