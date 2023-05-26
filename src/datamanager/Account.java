package datamanager;

import data.Admin;
import data.Passenger;
import utils.*;
import java.util.Scanner;

/**
 * This class is for all the actions related to account.
 */
public class Account {
    private final Scanner input;
    private final DataHolder<Admin> admins;
    private final DataHolder<Passenger> passengers;

    public Account(Scanner input, DataHolder<Admin> admins, DataHolder<Passenger> passengers) {
        this.input = input;
        this.admins = admins;
        this.passengers = passengers;
    }

    /**
     * This method is for sign-up process.
     * @param user an admin or a passenger
     * @return accepted username
     */
    public String signUp(String user) {
        System.out.print("""
                { * Username Requirements :
                - Username should be at least five characters long.
                - Username should start with a letter.
                - Username should not include spaces. }
                
                """);

        String username = usernameValidation();

        Console.pauseProgram();

        System.out.print("""
                
                { * Password Requirements :
                - Password should be at least four characters long.
                - Password can include letters, numbers and dash.
                - Password should not include spaces. }
                
                """);
        String password = PasswordValidation();

        Console.pauseProgram();

        if (user.equals("passenger")) {
            passengers.addToFile(new Passenger(username, password, 0));
        } else if (user.equals("admin")) {
            admins.addToFile(new Admin(username, password));
        }
        return username;
    }

    /**
     * This method is for the username validation.
     * @return accepted username
     */
    public String usernameValidation() {
        String username;
        while (true) {
            do {
                System.out.print("- Enter Username : ");
                username = input.nextLine();
            } while (!usernameRequirements(username));

            if (passengers.findInFile(username) != null || admins.findInFile(username) != null) {
                System.out.println(AnsiColors.ANSI_RED + "* Username is already taken ! Try another username :)\n" + AnsiColors.ANSI_RESET);
            } else {
                System.out.println(AnsiColors.ANSI_GREEN + "~ Username accepted !" + AnsiColors.ANSI_RESET);
                return username;
            }
        }
    }

    /**
     * This method is for the password validation.
     * @return accepted password
     */
    public String PasswordValidation() {
        String password;
        String confirmPassword;
        while(true) {
            do {
                System.out.print("- Enter Password : ");
                password = input.nextLine();
            } while (!passwordRequirements(password));

            System.out.print("- Confirm Your Password : ");
            confirmPassword = input.nextLine();

            if(password.equals(confirmPassword)) {
                System.out.println(AnsiColors.ANSI_GREEN + "~ Password accepted !" + AnsiColors.ANSI_RESET);
                return password;
            } else {
                System.out.println(AnsiColors.ANSI_RED + "!! Passwords do NOT match...Try Again :)\n" + AnsiColors.ANSI_RESET);
            }
        }
    }

    /**
     * This method checks if the entered username matches the username requirements.
     * @param username chosen username
     * @return true if the username is accepted according to username requirements.
     */
    private boolean usernameRequirements(String username) {
        if (username.contains(" ")) {
            System.out.println(AnsiColors.ANSI_RED + "* Unacceptable username : Username should not include spaces !\n" + AnsiColors.ANSI_RESET);
            return false;
        } else if (!username.matches("^[a-zA-Z]\\w*")) {
            System.out.println(AnsiColors.ANSI_RED + "* Unacceptable username : Username should start with a letter !\n" + AnsiColors.ANSI_RESET);
            return false;
        } else if (!username.matches("^\\w{5,}$")) {
            System.out.println(AnsiColors.ANSI_RED + "* Unacceptable username : Username should be at least five characters long !\n" + AnsiColors.ANSI_RESET);
            return false;
        }
        return true;
    }

    /**
     * This method checks if the entered password matches the password requirements.
     * @param password chosen password
     * @return true if the password is accepted according to password requirements.
     */
    private boolean passwordRequirements(String password) {
        if (password.contains(" ")) {
            System.out.println(AnsiColors.ANSI_RED + "* Unacceptable password : Password should not include spaces !\n" + AnsiColors.ANSI_RESET);
            return false;
        } else if (!password.matches("^[A-Za-z0-9_]*$")) {
            System.out.println(AnsiColors.ANSI_RED + "* Unacceptable password : Password can only include letters, numbers and dash !\n" + AnsiColors.ANSI_RESET);
            return false;
        } else if (!password.matches("^\\w{4,}$")) {
            System.out.println(AnsiColors.ANSI_RED + "* Unacceptable password : Password should be at least four characters long !\n" + AnsiColors.ANSI_RESET);
            return false;
        }
        return true;
    }

    /**
     * This method is for sign-in process.
     * @return the found username
     */
    public String signIn() {
        return matchUser();
    }

    /**
     * This method is for matching the user.
     * @return the found username
     */
    public String matchUser() {
        String username;
        while (true) {
            System.out.print("{ Username } : ");
            username = input.nextLine();

            Passenger passenger = passengers.findInFile(username);
            Admin admin = admins.findInFile(username);
            if (passenger != null) {
                matchPassword(username, "passenger");
                return username;
            } else if (admin != null) {
                matchPassword(username, "admin");
                return username;
            } else {
                System.out.println(AnsiColors.ANSI_RED + "* USERNAME NOT FOUND ! Try Again !\n" + AnsiColors.ANSI_RESET);
            }
        }
    }

    /**
     * This method is for the matching password.
     * @param username the found username
     * @param user an admin or a passenger
     */
    private void matchPassword(String username, String user) {
        String password;
        while (true) {
            System.out.print("{ Password } : ");
            password = input.nextLine();

            if (user.equals("passenger")) {
                if (password.equals(passengers.findInFile(username).getPassword())) {
                    return;
                } else {
                    System.out.println(AnsiColors.ANSI_RED + "* INCORRECT PASSWORD ! Try Again !\n" + AnsiColors.ANSI_RESET);
                }
            } else if (user.equals("admin")) {
                if (password.equals(admins.findInFile(username).getPassword())) {
                    return;
                } else {
                    System.out.println(AnsiColors.ANSI_RED + "* INCORRECT PASSWORD ! Try Again !\n" + AnsiColors.ANSI_RESET);
                }
            }
        }
    }

    /**
     * This method is for changing password page.
     * @param passenger the user that wants to change their password.
     */
    public void changePasswordPage(Passenger passenger) {
        Console.clear();
        System.out.print(AnsiColors.ANSI_YELLOW + """
                ______________________________________________________________
                ║                      [ CHANGE PASSWORD ]                   ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);
        changingPassword(passenger);
    }

    /**
     * This method is for changing the password.
     * @param passenger the user that wants to change their password.
     */
    public void changingPassword(Passenger passenger) {
        System.out.println("~ USER => " + passenger.getUsername());

        String password;
        while (true) {
            System.out.println("- First, Enter Your Current Password : ");
            password = input.nextLine();

            if(password.equals(passenger.getPassword())){
                break;
            } else {
                System.out.println(AnsiColors.ANSI_RED + "* INCORRECT PASSWORD ! Try Again !\n" + AnsiColors.ANSI_RESET);
            }
        }

        String confirmNewPassword;
        while(true) {
            do {
                System.out.print("\n- Enter New Password : ");
                password = input.nextLine();
            } while (!passwordRequirements(password));

            System.out.print("- Confirm Your Password : ");
            confirmNewPassword = input.nextLine();

            if(password.equals(confirmNewPassword)) {
                System.out.println("~ Password accepted !");
                break;
            } else {
                System.out.println(AnsiColors.ANSI_RED + "!! Passwords do NOT match...Try Again :)\n" + AnsiColors.ANSI_RESET);
            }
        }

        Console.pauseProgram();
        passengers.updateFile(passenger.getUsername(), 2, password);
        passenger.setPassword(password);
        System.out.println(AnsiColors.ANSI_GREEN + "Password successfully changed !" + AnsiColors.ANSI_RESET);
        Console.pressKey();
    }

    /**
     * This method is for adding admin.
     */
    public void addAdminPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_PURPLE + """
                ______________________________________________________________
                ║                        [ ADD ADMIN ]                       ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);
        String username = signUp("admin");

        System.out.println("\nAdding Admin successfully completed !");
        System.out.printf(AnsiColors.ANSI_GREEN + "{ Added Admin => %s }%n", username + AnsiColors.ANSI_RESET);

        Console.pressKey();
    }
}