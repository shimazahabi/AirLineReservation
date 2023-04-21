package utils;

import java.util.Scanner;

/**
 * This class holds methods that are used in all classes. (Mostly related to console)
 */
public class Console {
    static Scanner input = new Scanner(System.in);

    /**
     * This method checks whether the input is integer or not.
     * @return integer or -1 if input is not an integer.
     */
    public static int checkInt() {
        try
        {
            return Integer.parseInt(input.nextLine());
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    /**
     * This method clears the console. (However it doesn't work in intellij)
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
    }

    /**
     * This method waits for pressing a key to continue.
     */
    public static void pressKey() {
        System.out.println(AnsiColors.ANSI_PURPLE + "Please press a key to continue..." + AnsiColors.ANSI_RESET);
        try{System.in.read();}
        catch(Exception e){	e.printStackTrace();}
    }

    public static final int SLEEP_TIME = 1000;

    /**
     * This method pauses the program for 1000 milliseconds.
     */
    public static void pauseProgram() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}