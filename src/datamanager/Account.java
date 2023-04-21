package datamanager;

import data.Admin;
import data.Passenger;
import utils.AnsiColors;
import utils.Console;

import java.util.Scanner;

public class Account {
    private final Scanner input;
    private final Users users;

    public Account(Scanner input, Users users) {
        this.input = input;
        this.users = users;
    }

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
            users.addPassenger(username, password);
        } else if (user.equals("admin")) {
            users.addAdmin(username, password);
        }
        return username;
    }

    public String usernameValidation() {
        String username;
        while (true) {
            do {
                System.out.print("- Enter Username : ");
                username = input.nextLine();
            } while (!usernameRequirements(username));

            if (users.findPassenger(username) != null || users.findAdmin(username) != null) {
                System.err.println("* Username is already taken ! Try another username :)\n");
            } else {
                System.out.println(AnsiColors.ANSI_GREEN + "~ Username accepted !" + AnsiColors.ANSI_RESET);
                return username;
            }
        }
    }

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
                System.err.println("!! Passwords do NOT match...Try Again :)\n");
            }
        }
    }

    private boolean usernameRequirements(String username) {
        if (username.contains(" ")) {
            System.err.println("* Unacceptable username : Username should not include spaces !\n");
            return false;
        } else if (!username.matches("^[a-zA-Z]\\w*")) {
            System.err.println("* Unacceptable username : Username should start with a letter !\n");
            return false;
        } else if (!username.matches("^\\w{5,}$")) {
            System.err.println("* Unacceptable username : Username should be at least five characters long !\n");
            return false;
        }
        return true;
    }

    private boolean passwordRequirements(String password) {
        if (password.contains(" ")) {
            System.err.println("* Unacceptable password : Password should not include spaces !\n");
            return false;
        } else if (!password.matches("^[A-Za-z0-9_]*$")) {
            System.err.println("* Unacceptable password : Password can only include letters, numbers and dash !\n");
            return false;
        } else if (!password.matches("^\\w{4,}$")) {
            System.err.println("* Unacceptable password : Password should be at least four characters long !\n");
            return false;
        }
        return true;
    }

    public String signIn() {
        return matchUser();
    }

    public String matchUser() {
        String username;
        while (true) {
            System.out.print("{ Username } : ");
            username = input.nextLine();

            Passenger passenger = users.findPassenger(username);
            Admin admin = users.findAdmin(username);
            if (passenger != null) {
                matchPassword(username, "passenger");
                return username;
            } else if (admin != null) {
                matchPassword(username, "admin");
                return username;
            } else {
                System.err.println("* USERNAME NOT FOUND ! Try Again !\n");
            }
        }
    }

    private void matchPassword(String username, String user) {
        String password;
        while (true) {
            System.out.print("{ Password } : ");
            password = input.nextLine();

            if (user.equals("passenger")) {
                if (password.equals(users.findPassenger(username).getPassword())) {
                    return;
                }
            } else if (user.equals("admin")) {
                if (password.equals(users.findAdmin(username).getPassword())) {
                    return;
                }
            } else {
                System.err.println("* INCORRECT PASSWORD ! Try Again !\n");
            }
        }
    }

    public void changePasswordPage(Passenger passenger) {
        Console.clear();
        System.out.print(AnsiColors.ANSI_YELLOW + """
                ______________________________________________________________
                ║                      [ CHANGE PASSWORD ]                   ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);
        changingPassword(passenger);
    }

    public void changingPassword(Passenger passenger) {
        System.out.println("~ USER => " + passenger.getUsername());

        String password;
        while (true) {
            System.out.println("- First, Enter Your Current Password : ");
            password = input.nextLine();

            if(password.equals(passenger.getPassword())){
                break;
            } else {
                System.err.println("* INCORRECT PASSWORD ! Try Again !\n");
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
                System.err.println("!! Passwords do NOT match...Try Again :)\n");
            }
        }

        Console.pauseProgram();
        passenger.setPassword(password);
        System.out.println(AnsiColors.ANSI_GREEN + "Password successfully changed !" + AnsiColors.ANSI_RESET);
        Console.pressKey();
    }

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