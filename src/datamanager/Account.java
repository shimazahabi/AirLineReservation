package datamanager;

import utils.Console;

import data.User;

import java.util.Scanner;

public class Account {
    private final Scanner input = new Scanner(System.in);
    private final Users users;

    public Account(Users users) {
        this.users = users;
    }

    public String signUp() {
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

        users.addUser(username, password);
        return username;
    }

    public String usernameValidation() {
        String username;
        while (true) {
            do {
                System.out.print("- Enter Username : ");
                username = input.nextLine();
            } while (!usernameRequirements(username));

            if (users.findUser(username) != null) {
                System.out.println("* Username is already taken ! Try another username :)\n");
            } else {
                System.out.println("~ Username accepted !");
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
                System.out.println("~ Password accepted !");
                return password;
            } else {
                System.out.println("!! Passwords do NOT match...Try Again :)\n");
            }
        }
    }

    private boolean usernameRequirements(String username) {
        if (username.contains(" ")) {
            System.out.println("* Unacceptable username : Username should not include spaces !\n");
            return false;
        } else if (!username.matches("^[a-zA-Z]\\w*")) {
            System.out.println("* Unacceptable username : Username should start with a letter !\n");
            return false;
        } else if (!username.matches("^\\w{5,}$")) {
            System.out.println("* Unacceptable username : Username should be at least five characters long !\n");
            return false;
        }
        return true;
    }

    private boolean passwordRequirements(String password) {
        if (password.contains(" ")) {
            System.out.println("* Unacceptable password : Password should not include spaces !\n");
            return false;
        } else if (!password.matches("^[A-Za-z0-9_]*$")) {
            System.out.println("* Unacceptable password : Password can only include letters, numbers and dash !\n");
            return false;
        } else if (!password.matches("^\\w{4,}$")) {
            System.out.println("* Unacceptable password : Password should be at least four characters long !\n");
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

            User user = users.findUser(username);
            if (user != null) {
                matchPassword(username);
                return username;
            } else {
                System.out.println("* USERNAME NOT FOUND ! Try Again !\n");
            }
        }
    }

    private void matchPassword(String username) {
        String password;
        while (true) {
            System.out.print("{ Password } : ");
            password = input.nextLine();

            if(password.equals(users.password(username))){
                return;
            } else {
                System.out.println("* INCORRECT PASSWORD ! Try Again !\n");
            }
        }
    }

    public void changeingPassword(String username) {
        System.out.println("~ USER => " + username);

        String password;
        while (true) {
            System.out.println("- First, Enter Your Current Password : ");
            password = input.nextLine();

            if(password.equals(users.password(username))){
                break;
            } else {
                System.out.println("* INCORRECT PASSWORD ! Try Again !\n");
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
                System.out.println("!! Passwords do NOT match...Try Again :)\n");
            }
        }

        Console.pauseProgram();
        users.changePassword(username, password);
        System.out.println("Password successfully changed !");
        Console.pressKey();
    }
}