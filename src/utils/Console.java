package utils;

import java.util.Scanner;

public class Console {
    static Scanner input = new Scanner(System.in);

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
        System.out.println("Please press a key to continue...");
        try{System.in.read();}
        catch(Exception e){	e.printStackTrace();}
    }

    public static void pauseProgram() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}