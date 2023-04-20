package menu;

import datamanager.*;
import utils.Console;
import java.util.Scanner;

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

    public void signUpPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````````| SIGN UP |``````````````````````````
                """);
        String username = dataBase.getAccount().signUp();

        System.out.println("\nSigning Up successfully completed !");
        System.out.printf("{ WELCOME USER => %s }%n", username);
        Console.pressKey();

        dataBase.getPassengerMenu().showMenu(dataBase.getUsers().findUser(username));
    }

    public void signInPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````````| SIGN In |``````````````````````````
                
                """);
        String username = dataBase.getAccount().signIn();

        System.out.printf("\n{ WELCOME USER => %s }%n", username);
        Console.pressKey();

        if(username.equals("Admin")){
            dataBase.getAdminMenu().showMenu();
        } else {
            dataBase.getPassengerMenu().showMenu(dataBase.getUsers().findUser(username));
        }
    }
}