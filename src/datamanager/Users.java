package datamanager;

import data.User;
import java.util.ArrayList;

public class Users {
    private final ArrayList<User> users = new ArrayList<>();

    public Users() { }

    public void addUser(String username, String password) {
        users.add(new User(username, password));
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