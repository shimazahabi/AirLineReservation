package menu;

import utils.*;
import java.util.Scanner;

/**
 * BaseMenu is the parent class of menu classes;
 */
public abstract class BaseMenu {
    Scanner input;

    public BaseMenu(Scanner input, int RANGE) {
        this.input = input;
        this.RANGE = RANGE;
    }

    public final int EXIT = 0;
    public final int ERROR = -1;
    public final int RANGE;

    public abstract void printMenu();

    public abstract int readInput();

    public void printErrorMessage() {
        System.out.println(AnsiColors.ANSI_RED + "* Attention => You can only enter numbers ! *" + AnsiColors.ANSI_RESET);
        Console.pauseProgram();
    }

    public void printOutOfRangeMessage() {
        System.out.println(AnsiColors.ANSI_RED + "* Attention => Chosen option is out of range ! *" + AnsiColors.ANSI_RESET);
        Console.pauseProgram();
    }

    public abstract int processCommand(int command);

    public int safeRead() {
        printMenu();

        int command;
        while (true) {
            command = readInput();
            if (command == ERROR) {
                printErrorMessage();
            } else if (command < 0 || command > RANGE) {
                printOutOfRangeMessage();
            } else {
                return command;
            }
        }
    }

    public void start() {
        int command;
        do {
            command = safeRead();
            processCommand(command);
        } while (command != EXIT);
    }
}