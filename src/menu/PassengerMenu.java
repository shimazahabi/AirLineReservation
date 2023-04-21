package menu;

import data.Passenger;
import datamanager.Account;
import datamanager.PassengerActions;
import utils.AnsiColors;
import utils.Console;
import java.util.Scanner;

/**
 * This class is for the passenger manu options. (Child class of BaseMenu)
 */
public class PassengerMenu extends BaseMenu {
    private final PassengerActions passengerActions;
    private final Account account;
    private Passenger activePassenger;

    public PassengerMenu(PassengerActions passengerActions, Account account, Scanner input, int RANGE) {
        super(input, RANGE);
        this.passengerActions = passengerActions;
        this.account = account;
    }

    public void showMenu(Passenger passenger) {
        activePassenger = passenger;
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
        System.out.print(AnsiColors.ANSI_YELLOW + """
                   ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                  _____              _____   _____  ______  _   _   _____  ______  _____ \s
                 |  __ \\     /\\     / ____| / ____||  ____|| \\ | | / ____||  ____||  __ \\\s
                 | |__) |   /  \\   | (___  | (___  | |__   |  \\| || |  __ | |__   | |__) |
                 |  ___/   / /\\ \\   \\___ \\  \\___ \\ |  __|  | . ` || | |_ ||  __|  |  _  /\s
                 | |      / ____ \\  ____) | ____) || |____ | |\\  || |__| || |____ | | \\ \\\s
                 |_|     /_/    \\_\\|_____/ |_____/ |______||_| \\_| \\_____||______||_|  \\_\\
                                                                                                                                                \s
                ______________________________________________________________________________
                ║                        [ PASSENGER MENU OPTIONS ]                          ║
                ``````````````````````````````````````````````````````````````````````````````
                                           
                [ 1 ] Change Password
                [ 2 ] Search Flight Tickets
                [ 3 ] Booking Ticket
                [ 4 ] Ticket Cancellation
                [ 5 ] Booked Tickets
                [ 6 ] Add Charge
                [ 0 ] Sign Out
                  """ + AnsiColors.ANSI_RESET);
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
            case CHANGE_PASSWORD-> account.changePasswordPage(activePassenger);
            case SEARCH -> passengerActions.searchFlightTicketsPage();
            case BOOKING -> passengerActions.bookingTicketPage(activePassenger);
            case CANCELLATION -> passengerActions.ticketCancellationPage(activePassenger);
            case BOOKED_TICKETS -> passengerActions.bookedTicketsPage(activePassenger);
            case ADD_CHARGE -> passengerActions.addChargePage(activePassenger);
        }
        return 0;
    }
}