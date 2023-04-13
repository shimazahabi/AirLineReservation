package menu;

import datamanager.*;
import utils.Console;
import data.User;

public class MainMenu {
    private final Flights flights = new Flights();
    private final Users users = new Users();
    private final Tickets tickets = new Tickets();
    private final Account account = new Account(users);
    private final Admin admin = new Admin(flights, tickets);
    private final AdminMenu adminMenu = new AdminMenu(admin);
    private final Passenger passenger = new Passenger(users, flights, tickets);
    private final PassengerMenu passengerMenu = new PassengerMenu(passenger, account);

    public void mainMenuOptions() {
        preDefined();

        int option;
        do {
            Console.clear();
            System.out.print("""
                             ⢳⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀  ⠀⠀⢿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀  ⠀⠀⠈⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                   ⢲⡄⠀⠀⠀⠀  ⠀⠀⠀⢙⣿⣿⡶⠆⠀⠀⠀⠀⠀⠀⠀
                    ⣘⣿⣦⣤⣤⣤⣶⣶⣶⣶⣿⣿⣿⣶⣶⣶⣶⣶⣦⣄⡀  +----------------------------------------+
                    ⢩⣿⠟⠛⠻⠿⠿⠿⠿⠿⣿⣿⣿⠿⠿⠿⠿⠿⠛⠋⠁  |  WELCOME TO AIRLINE RESERVATION SYSTEM |\s
                   ⠼⠋⠀⠀⠀⠀⠀ ⠀ ⠀⣸⣿⣿⠷⠆⠀⠀⠀⠀⠀⠀⠀     +----------------------------------------+
                    ⠀⠀⠀⠀⠀⠀⠀ ⠀ ⢀⣾⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀⠀  ⠀⣾⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀  ⠀⣼⠏⠀
                                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    ````````````````````| MAIN MENU OPTIONS |`````````````````````
                                             
                    { 1 } - Sign In
                    { 2 } - Sign Up
                    { 0 } - Exit
                    """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    signInPage();
                    break;
                case 2:
                    signUpPage();
                    break;
                case 0:
                    break;
                case -1:
                    System.out.println("* Attention => You can only enter numbers ! *");
                    Console.pauseProgram();
                    break;
                default:
                    System.out.println("* Attention => Chosen option is out of range ! *");
                    Console.pauseProgram();
                    break;
            }
        } while (option != 0);
    }

    public void signUpPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````````| SIGN UP |``````````````````````````
                """);
        String username = account.signUp();

        System.out.println("\nSigning Up successfully completed !");
        System.out.printf("{ WELCOME USER => %s }%n", username);
        Console.pressKey();

        passengerMenu.passengerMenuOptions(username);
    }

    public void signInPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````````| SIGN In |``````````````````````````
                
                """);

        String username = account.signIn();

        System.out.printf("\n{ WELCOME USER => %s }%n", username);
        Console.pressKey();

        if(username.equals("Admin")){
            adminMenu.adminMenuOptions();
        } else {
            passengerMenu.passengerMenuOptions(username);
        }
    }

    public void preDefined() {
        users.addUser("Admin", "Admin");

        flights.addFlight("ST-19", "Shiraz", "Tehran", "1402-01-03", "23:20", 950000, 25);
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
