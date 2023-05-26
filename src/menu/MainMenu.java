package menu;

import datamanager.*;
import utils.AnsiColors;
import utils.Console;
import java.util.Scanner;

/**
 * This class is for the main manu options. (Child class of BaseMenu)
 */
public class MainMenu extends BaseMenu {
    private final DataBase dataBase = new DataBase();

    public MainMenu(Scanner input, int RANGE) {
        super(input, RANGE);
    }

    public void showMenu() {
        dataBase.preDefined();
        start();
    }

    public final int SIGN_IN = 1;
    public final int SIGN_UP = 2;

    @Override
    public void printMenu() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_CYAN + """
                                      ___
                                      \\\\ \\
                                       \\\\ `\\
                    ___                 \\\\  \\
                   |    \\                \\\\  `\\
                   |_____\\                \\    \\
                   |______\\                \\    `\\
                   |       \\                \\     \\
                   |      __\\__---------------------------------._.
                 __|---~~~__o_o_o_o_o_o_o_o_o_o_o_o_o_o_o_o_o_o_[][\\__
                |___                         /~      )                \\__
                    ~~~---..._______________/      ,/_________________/
                                           /      /
                                          /     ,/
                                         /     /
                                        /    ,/
                                       /    /        {  WELCOME
                                      //  ,/              T0
                                     //  /             AIRLINE
                                    // ,/            RESERVATION
                                   //_/                 SYSTEM   }
                                                     
                 ______________________________________________________________
                 ║                    [ MAIN MENU OPTIONS ]                   ║
                 ``````````````````````````````````````````````````````````````
                                          
                 [ 1 ] Sign In
                 [ 2 ] Sign Up
                 [ 0 ] Exit
                 """ + AnsiColors.ANSI_RESET);
    }

    @Override
    public int readInput() {
        int command = Console.checkInt();

        return switch (command) {
            case 1 -> SIGN_IN;
            case 2 -> SIGN_UP;
            case 0 -> EXIT;
            case -1 -> ERROR;
            default -> command;
        };
    }

    @Override
    public int processCommand(int command) {
        switch (command) {
            case SIGN_IN -> signInPage();
            case SIGN_UP -> signUpPage();
        }
        return 0;
    }

    /**
     * This method is for the sign-up page and going to the passenger menu.
     */
    public void signUpPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_CYAN + """
                ______________________________________________________________
                ║                        [ SIGN UP ]                         ║
                ``````````````````````````````````````````````````````````````
                """ + AnsiColors.ANSI_RESET);
        String username = dataBase.getAccount().signUp("passenger");

        System.out.println("\nSigning Up successfully completed !");
        System.out.printf(AnsiColors.ANSI_GREEN + "{ WELCOME USER => %s }%n", username + AnsiColors.ANSI_RESET);
        Console.pressKey();

        dataBase.getPassengerMenu().showMenu(dataBase.getPassengers().findInFile(username));
    }

    /**
     * This method is for the sing-in page and going to the admin-passenger menu.
     */
    public void signInPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_CYAN + """
                ______________________________________________________________
                ║                        [ SIGN IN ]                         ║
                ``````````````````````````````````````````````````````````````
                
                """+ AnsiColors.ANSI_RESET);
        String username = dataBase.getAccount().signIn();

        System.out.printf(AnsiColors.ANSI_GREEN + "\n{ WELCOME USER => %s }%n", username + AnsiColors.ANSI_RESET);
        Console.pressKey();

        if (dataBase.getAdmins().findInFile(username) != null) {
            dataBase.getAdminMenu().showMenu();
        } else if (dataBase.getPassengers().findInFile(username) != null) {
            dataBase.getPassengerMenu().showMenu(dataBase.getPassengers().findInFile(username));
        }
    }
}