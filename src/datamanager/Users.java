package datamanager;

import data.Admin;
import data.Passenger;
import java.util.ArrayList;

public class Users {
    private final ArrayList<Passenger> passengers = new ArrayList<>();
    private final ArrayList<Admin> admins = new ArrayList<>();

    public Users() { }

    public void addPassenger(String username, String password) { passengers.add(new Passenger(username, password)); }

    public void addAdmin(String username, String password) { admins.add(new Admin(username, password)); }

    public Passenger findPassenger(String username) {
        for (Passenger passenger : passengers) {
            if (username.equals(passenger.getUsername())){
                return passenger;
            }
        }
        return null;
    }

    public Admin findAdmin(String username) {
        for (Admin admin : admins) {
            if (username.equals(admin.getUsername())) {
                return admin;
            }
        }
        return null;
    }
}