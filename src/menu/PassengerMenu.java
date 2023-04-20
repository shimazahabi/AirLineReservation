package menu;

import data.User;
import datamanager.Account;
import datamanager.Passenger;
import utils.Console;

public class PassengerMenu {
    private final Passenger passenger;
    private final Account account;
    private User activeUser;

    public PassengerMenu(Passenger passenger, Account account) {
        this.passenger = passenger;
        this.account = account;
    }

    public void passengerMenuOptions(User user) {
        activeUser = user;

        int option;
        do {
            Console.clear();
            System.out.print("""
                     ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                   ⠀ ⠀⠀⠀⠀⠀⢸⣿⠛⠛⠛⠛⠛⠛⣿⣧⠀⠀⠀⠀⠀⠀
                                   ⠀⠀ ⠀⠀⠀⠀⡘⠛⠀⠀⠀⠀  ⠀⠀⠛⠃⠀⠀⠀⠀⠀⠀
                                   ⢰⣿⣿⡇⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿⣆
                                   ⢸⣿⣿⡗⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿⣧
                                   ⢸⣿⣿⡇⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿⡧
                                   ⢸⣿⣿⡇⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿⣏
                                   ⢸⣿⣿⡇⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿⣧
                                   ⢸⣿⣿⡗⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⣿⣿⡧
                                   ⠈⠛⠛⠋⠀⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠂⠘⠛⠛⠁
                                  
                  ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                  ``````````````````| PASSENGER MENU OPTIONS |``````````````````
                                             
                  { 1 } - Change Password
                  { 2 } - Search Flight Tickets
                  { 3 } - Booking Ticket
                  { 4 } - Ticket Cancellation
                  { 5 } - Booked Tickets
                  { 6 } - Add Charge
                  { 0 } - Sign Out
                    """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    changePasswordMenu();
                    break;
                case 2:
                    searchFlightTicketsMenu();
                    break;
                case 3:
                    bookingTicketMenu();
                    break;
                case 4:
                    ticketCancellation();
                    break;
                case 5:
                    bookedTickets();
                    break;
                case 6:
                    addChargeMenu();
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

    public void changePasswordMenu() {
        int option;
        do {
            Console.clear();
            System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````| CHANGE PASSWORD |``````````````````````
                
                { 1 } - Change Password
                { 0 } - Return
                
                """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    account.changingPassword(activeUser);
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

    public void searchFlightTicketsMenu() {
        int option;
        do {
            Console.clear();
            System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ``````````````````| SEARCH FLIGHT TICKETS |```````````````````
                
                { 1 } - Search Flight Tickets
                { 0 } - Return
                """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    passenger.searchFlightTickets();
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

    public void bookingTicketMenu() {
        int option;
        do {
            Console.clear();
            System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ``````````````````````| BOOKING TICKET |``````````````````````
                
                { 1 } - Book A Ticket
                { 0 } - Return
                """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    passenger.bookingTicket(activeUser);
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

    public void ticketCancellation() {
        int option;
        do {
            Console.clear();
            System.out.print("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    ```````````````````| Ticket Cancellation |````````````````````
                                    
                    { 1 } - Cancel A Ticket
                    { 0 } - Return
                    """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    passenger.cancellingTicket(activeUser);
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

    public void bookedTickets() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ``````````````````````| Booked Tickets |``````````````````````
                    
                """);
        passenger.printBookedTicket(activeUser);
    }

    public void addChargeMenu() {
        int option;
        do {
            Console.clear();
            System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ````````````````````````| ADD CHARGE |````````````````````````
                    
                { 1 } - Add Charge
                { 0 } - Return
                
                """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    passenger.addingCharge(activeUser);
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
}