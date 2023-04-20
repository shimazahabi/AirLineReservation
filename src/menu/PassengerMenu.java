package menu;

import data.User;
import datamanager.Account;
import datamanager.Passenger;
import utils.Console;
import java.util.Scanner;

public class PassengerMenu extends BaseMenu {
    private final Passenger passenger;
    private final Account account;
    private User activeUser;

    public PassengerMenu(Passenger passenger, Account account, Scanner input, int RANGE) {
        super(input, RANGE);
        this.passenger = passenger;
        this.account = account;
    }

    public void showMenu(User user) {
        activeUser = user;
        start();
    }

    public final int CHANGE_PASSWORD = 1;
    public final int SEARCH = 2;
    public final int BOOKING = 3;
    public final int CANCELLATION = 4;
    public final int BOOKED_TICKETS = 5;
    public final int ADD_CHARGE = 6;

    @Override
    public void printMenu() {
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
    }

    @Override
    public int readInput() {
        int command = Console.checkInt();

        return switch (command) {
            case 1 -> CHANGE_PASSWORD;
            case 2 -> SEARCH;
            case 3 -> BOOKING;
            case 4 -> CANCELLATION;
            case 5 -> BOOKED_TICKETS;
            case 6 -> ADD_CHARGE;
            case 0 -> EXIT;
            case -1 -> ERROR;
            default -> command;
        };
    }

    @Override
    public int processCommand(int command) {
        switch (command) {
            case CHANGE_PASSWORD-> account.changePasswordPage(activeUser);
            case SEARCH -> passenger.searchFlightTicketsPage();
            case BOOKING -> passenger.bookingTicketPage(activeUser);
            case CANCELLATION -> passenger.ticketCancellationPage(activeUser);
            case BOOKED_TICKETS -> passenger.bookedTicketsPage(activeUser);
            case ADD_CHARGE -> passenger.addChargePage(activeUser);
        }
        return 0;
    }
}