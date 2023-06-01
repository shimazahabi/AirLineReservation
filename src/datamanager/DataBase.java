package datamanager;

import data.*;
import menu.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is for newing all the classes.
 */
public class DataBase {
    Scanner input = new Scanner(System.in);
    DataHolder<Admin> admins = new DataHolder<>(new Admin("",""),"Admins.dat", "AdminsIndex.dat", 120, 2);
    DataHolder<Passenger> passengers = new DataHolder<>(new Passenger("", "", 0),"Passengers.dat", "PassengersIndex.dat",180, 3);
    Flights flights = new Flights(new Flight("", "", "", "", "", 0,0, false), "Flights.dat", "FlightsIndex.dat",480, 8);
    Tickets tickets = new Tickets(new Ticket("", "", ""), "Tickets.dat", "TicketsIndex.dat",180, 3);

    private final Account account = new Account(input, admins, passengers);
    private final AdminActions adminActions = new AdminActions(input, flights);
    private final AdminMenu adminMenu = new AdminMenu(adminActions, account, input, 5);
    private final PassengerActions passengerActions = new PassengerActions(input, passengers, flights, tickets);
    private final PassengerMenu passengerMenu = new PassengerMenu(passengerActions, account, input, 6);

    public DataHolder<Admin> getAdmins() {
        return admins;
    }

    public DataHolder<Passenger> getPassengers() {
        return passengers;
    }

    public Account getAccount() {
        return account;
    }

    public AdminMenu getAdminMenu() {
        return adminMenu;
    }

    public PassengerMenu getPassengerMenu() {
        return passengerMenu;
    }

    /**
     * This method predefines some flights and an admin.
     */
    public void preDefined() {
        File config = new File("config.txt");

        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            admins.addToFile(new Admin("Admin", "Admin"));

            flights.addToFile(new Flight("ST-19", "Shiraz", "Tehran", "1402-01-03", "23:20", 950000, 25, false));
            flights.addToFile(new Flight("YT-21", "Yazd", "Tabriz", "1402-01-05", "09:30", 900000, 45, false));
            flights.addToFile(new Flight("SK-23", "Shiraz", "Kish", "1402-01-06", "18:45", 985000, 59, false));
            flights.addToFile(new Flight("MA-74", "Mashhad", "Ahvaz", "1402-01-07", "11:40", 1200000, 62, false));
            flights.addToFile(new Flight("ST-35", "Shahrood", "Tehran", "1402-01-10", "15:20", 350000, 35, false));
            flights.addToFile(new Flight("ER-76", "Isfahan", "Rasht", "1402-01-13", "12:45", 1000000, 53, false));
            flights.addToFile(new Flight("TM-17", "Tehran", "Mashhad", "1402-01-15", "20:30", 870000, 13, false));
            flights.addToFile(new Flight("TI-38", "Tehran", "Istanbul", "1402-01-18", "13:45", 1500000, 47, false));
            flights.addToFile(new Flight("YT-22", "Yazd", "Tehran", "1402-01-23", "22:30", 980000, 27, false));
            flights.addToFile(new Flight("SA-30", "Shiraz", "Ahvaz", "1402-01-25", "12:50", 789000, 48, false));
        }
    }
}