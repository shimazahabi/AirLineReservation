package datamanager;

import menu.AdminMenu;
import menu.PassengerMenu;
import java.util.Scanner;

public class DataBase {
    Scanner input = new Scanner(System.in);
    private final Flights flights = new Flights();
    private final Users users = new Users();
    private final Tickets tickets = new Tickets();
    private final Account account = new Account(users);
    private final AdminActions adminActions = new AdminActions(flights, tickets);
    private final AdminMenu adminMenu = new AdminMenu(adminActions, account, input, 5);
    private final PassengerActions passengerActions = new PassengerActions(flights, tickets);
    private final PassengerMenu passengerMenu = new PassengerMenu(passengerActions, account, input, 6);

    public Users getUsers() {
        return users;
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

    public void preDefined() {
        users.addAdmin("Admin", "Admin");

        flights.addFlight("ST-19", "Shiraz", "Tehran", "1402-01-03", "23:20", 950000, 25);
        flights.addFlight("YT-21", "Yazd", "Tabriz", "1402-01-05", "09:30", 900000, 45);
        flights.addFlight("SK-23", "Shiraz", "Kish", "1402-01-06", "18:45", 985000, 59);
        flights.addFlight("MA-74", "Mashhad", "Ahvaz", "1402-01-07", "11:40", 1200000, 62);
        flights.addFlight("ST-35", "Shahrood", "Tehran", "1402-01-10", "15:20", 350000, 35);
        flights.addFlight("ER-76", "Isfahan", "Rasht", "1402-01-13", "12:45", 1000000, 53);
        flights.addFlight("TM-17", "Tehran", "Mashhad", "1402-01-15", "20:30", 870000, 13);
        flights.addFlight("TI-38", "Tehran", "Istanbul", "1402-01-18", "13:45", 1500000, 47);
        flights.addFlight("YT-22", "Yazd", "Tehran", "1402-01-23", "22:30", 980000, 27);
        flights.addFlight("SA-30", "Shiraz", "Ahvaz", "1402-01-25", "12:50", 789000, 48);
    }
}