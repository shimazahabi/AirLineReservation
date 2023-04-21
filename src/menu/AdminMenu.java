package menu;

import datamanager.Account;
import utils.AnsiColors;
import utils.Console;
import datamanager.AdminActions;
import java.util.Scanner;

/**
 * This class is for the admin manu options. (Child class of BaseMenu)
 */
public class AdminMenu extends BaseMenu {
    private final AdminActions adminActions;
    private final Account account;

    public AdminMenu(AdminActions adminActions, Account account, Scanner input, int RANGE) {
        super(input, RANGE);
        this.adminActions = adminActions;
        this.account = account;
    }

    public void showMenu() {
        start();
    }

    public final int ADD_FLIGHT = 1;
    public final int UPDATE_FLIGHT = 2;
    public final int REMOVE_FLIGHT = 3;
    public final int FLIGHT_SCHEDULES = 4;
    public final int ADD_ADMIN = 5;

    @Override
    public void printMenu() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_PURPLE + """
                                     
                         _        ______     ____    ____   _____   ____  _____ \s
                        / \\      |_   _ `.  |_   \\  /   _| |_   _| |_   \\|_   _|\s
                       / _ \\       | | `. \\   |   \\/   |     | |     |   \\ | |  \s
                      / ___ \\      | |  | |   | |\\  /| |     | |     | |\\ \\| |  \s
                    _/ /   \\ \\_   _| |_.' /  _| |_\\/_| |_   _| |_   _| |_\\   |_ \s
                   |____| |____| |______.'  |_____||_____| |_____| |_____|\\____|\s
                                                                             \s
                   ______________________________________________________________
                   ║                    [ ADMIN MENU OPTIONS ]                  ║
                   ``````````````````````````````````````````````````````````````
                                               
                   [ 1 ] Add Flight
                   [ 2 ] Update Flight
                   [ 3 ] Remove Flight
                   [ 4 ] Flight Schedules
                   [ 5 ] Add Admin
                   [ 0 ] Sign Out
                  """ + AnsiColors.ANSI_RESET);
    }

    @Override
    public int readInput() {
        int command = Console.checkInt();

        return switch (command) {
            case 1 -> ADD_FLIGHT;
            case 2 -> UPDATE_FLIGHT;
            case 3 -> REMOVE_FLIGHT;
            case 4 -> FLIGHT_SCHEDULES;
            case 5 -> ADD_ADMIN;
            case 0 -> EXIT;
            case -1 -> ERROR;
            default -> command;
        };
    }

    @Override
    public int processCommand(int command) {
        switch (command) {
            case ADD_FLIGHT -> adminActions.addFlightPage();
            case UPDATE_FLIGHT -> adminActions.updateFlightPage();
            case REMOVE_FLIGHT -> adminActions.removeFlightPage();
            case FLIGHT_SCHEDULES -> adminActions.flightSchedulesPage();
            case ADD_ADMIN -> account.addAdminPage();
        }
        return 0;
    }
}