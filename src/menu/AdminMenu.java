package menu;

import utils.Console;
import datamanager.Admin;
import java.util.Scanner;

public class AdminMenu extends BaseMenu {
    private final Admin admin;

    public AdminMenu(Admin admin, Scanner input, int RANGE) {
        super(input, RANGE);
        this.admin = admin;
    }

    public void showMenu() {
        start();
    }

    public final int ADD = 1;
    public final int UPDATE = 2;
    public final int REMOVE = 3;
    public final int FLIGHT_SCHEDULES = 4;

    @Override
    public void printMenu() {
        Console.clear();
        System.out.print("""
                                         ⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀
                                        ⠀⠀⠀⠀⢀⣴⡶⠟⠛⠛⠛⠛⠻⢶⣦⡀⠀⠀⠀⠀
                                       ⠀ ⠀⢀⣴⡟⠁ ⣠⣶⣿⣿⣶⣄ ⠈⠻⣦⡀⠀⠀
                                       ⠀  ⣾⡏⠀⠀⠀⣿⣿⣿⣿⣿⣿⠀⠀  ⢹⣷⠀⠀
                                         ⢰⣿⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⠀⠀⠀ ⠀⣿⡆⠀
                                       ⠀ ⢸⣿⠀⠀⠀⠀⠈⣿⣿⣿⣿⠁⠀⠀⠀⠀ ⣿⡇⠀
                                        ⠀ ⣿⡆⢀⣀⣤⣾⣿⣿⣿⣿⣷⣤⣀⡀⢰⣿⠀⠀
                                      ⠀ ⠀ ⠘⢿⣮⠿⣿⣿⣿⣿⣿⣿⣿⣿⠿⣵⡿⠀
                                      ⠀⠀ ⠀ ⠀⠙⢷⣾⣭⣛⣛⣛⣛⣭⣵⡾⠋⠀⠀⠀⠀
                                      ⠀⠀⠀ ⠀ ⠀⠀⠀⠉⠙⠛⠛⠋⠉⠀⠀⠀⠀⠀⠀
                                    
                     ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                     ````````````````````| ADMIN MENU OPTIONS |````````````````````
                                                 
                     { 1 } - Add Flight
                     { 2 } - Update Flight
                     { 3 } - Remove Flight
                     { 4 } - Flight Schedules
                     { 0 } - Sign Out
                    """);
    }

    @Override
    public int readInput() {
        int command = Console.checkInt();

        return switch (command) {
            case 1 -> ADD;
            case 2 -> UPDATE;
            case 3 -> REMOVE;
            case 4 -> FLIGHT_SCHEDULES;
            case 0 -> EXIT;
            case -1 -> ERROR;
            default -> command;
        };
    }

    @Override
    public int processCommand(int command) {
        switch (command) {
            case ADD -> admin.addPage();
            case UPDATE -> admin.updatePage();
            case REMOVE -> admin.removePage();
            case FLIGHT_SCHEDULES -> admin.flightSchedulesPage();
        }
        return 0;
    }
}