import menu.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu(input, 2);
        mainMenu.showMenu();
    }
}