package menu;

import utils.Console;
import datamanager.Admin;

public class AdminMenu {
    private final Admin admin;

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public void adminMenuOptions() {
        int option;
        do {
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
                                                 
                     { 1 } - Add
                     { 2 } - Update
                     { 3 } - Remove
                     { 4 } - Flight Schedules
                     { 0 } - Sign Out
                    """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    addMenu();
                    break;
                case 2:
                    updateMenu();
                    break;
                case 3:
                    removeMenu();
                    break;
                case 4:
                    flightSchedules();
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

    private void addMenu(){
        int option;
        do {
            Console.clear();
            System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ```````````````````````| ADD FLIGHTS |````````````````````````
                
                { 1 } - Add A Flight
                { 0 } - Return
                """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    admin.addingFlight();
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

    private void updateMenu() {
        int option;
        do {
            Console.clear();
            System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````| UPDATE FLIGHTS |```````````````````````
                    
                { 1 } - Update A Flight
                { 0 } - Return
                """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    admin.updatingFlight();
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

    private void removeMenu() {
        int option;
        do {
            Console.clear();
            System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````| Remove FLIGHTS |```````````````````````
                    
                { 1 } - Remove A Flight
                { 0 } - Return
                """);

            option = Console.checkInt();
            switch (option) {
                case 1:
                    admin.removingFlight();
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

    private void flightSchedules() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````| FLIGHT SCHEDULES |`````````````````````
                
                
                """);

        admin.printFlightSchedules();
        Console.pressKey();
    }
}