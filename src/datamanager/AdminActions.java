package datamanager;

import data.Admin;
import data.Flight;
import data.Ticket;
import utils.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is for all the actions related to admin.
 */
public class AdminActions {
    private final Scanner input;
    private final Flights flights;

    public AdminActions(Scanner input, Flights flights) {
        this.input = input;
        this.flights = flights;
    }

    /**
     * This method is for adding flights page.
     */
    public void addFlightPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_PURPLE + """
                ______________________________________________________________
                ║                       [ ADD FLIGHTS ]                      ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);
        addingFlight();
    }

    /**
     * This method is for adding flights.
     */
    public void addingFlight() {
        System.out.print("- Flight Id : ");
        String flightId = flightIdValidation();

        System.out.print("- Origin : ");
        String origin = cityValidation();

        String destination;
        do {
            System.out.print("- Destination : ");
            destination = cityValidation();
            if (origin.equals(destination)) {
                System.out.println(AnsiColors.ANSI_RED + "Origin and destination can not be the same city ! Try Again :" + AnsiColors.ANSI_RESET);
            } else {
                break;
            }
        } while (true);

        System.out.print("- Date : ");
        String date = dateValidation();

        System.out.print("- Time : ");
        String time = timeValidation();

        System.out.print("- Price : ");
        int price = priceValidation();

        System.out.print("- Seats : ");
        int seats = seatsValidation();

        Console.pauseProgram();
        flights.addToFile(new Flight(flightId, origin, destination, date, time, price, seats, false));
        System.out.println(AnsiColors.ANSI_GREEN + "\n>> Flight is successfully added ! <<\n" + AnsiColors.ANSI_RESET);
        Console.pressKey();
    }

    /**
     * This method is for updating flights page.
     */
    public void updateFlightPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_PURPLE + """
                ______________________________________________________________
                ║                     [ UPDATE FLIGHTS ]                     ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);
        updatingFlight();
    }

    /**
     * This method is for updating flights.
     */
    public void updatingFlight() {
        String flightId = searchFlightId();

        int option;
        do {
            printFields(flightId);
            System.out.print("- Choose A Field : ");
            int fieldNum = Console.checkInt();
            String update1;
            int update2;

            switch (fieldNum) {
                case 1 -> {
                    System.out.print("- Update Origin : ");
                    update1 = cityValidation();
                    flights.updateFile(flightId, 2, update1);
                }
                case 2 -> {
                    System.out.print("- Update Destination : ");
                    update1 = cityValidation();
                    flights.updateFile(flightId, 3, update1);
                }
                case 3 -> {
                    System.out.print("- Update Date : ");
                    update1 = dateValidation();
                    flights.updateFile(flightId, 4, update1);
                }
                case 4 -> {
                    System.out.print("- Update Time : ");
                    update1 = timeValidation();
                    flights.updateFile(flightId, 5, update1);
                }
                case 5 -> {
                    if (flights.findInFile(flightId).isBooked()) {
                        System.out.println(AnsiColors.ANSI_RED + "* You can't change the price, because this flight is booked by passengers !" + AnsiColors.ANSI_RESET);
                    } else {
                        System.out.print("- Update Price : ");
                        update2 = priceValidation();
                        flights.updateFile(flightId, 6, String.valueOf(update2));
                    }
                }
                case 6 -> {
                    System.out.print("- Update Seats : ");
                    update2 = seatsValidation();
                    flights.updateFile(flightId, 7, String.valueOf(update2));
                }
                case -1 -> {
                    System.out.println(AnsiColors.ANSI_RED + "* Attention => You can only enter numbers ! *" + AnsiColors.ANSI_RESET);
                    Console.pauseProgram();
                }
                default -> {
                    System.out.println(AnsiColors.ANSI_RED + "Chosen field is out of range !" + AnsiColors.ANSI_RESET);
                    Console.pauseProgram();
                }
            }

            while (true) {
                System.out.print(AnsiColors.ANSI_PURPLE + """
                                            
                        [ 1 ] Update Another Field
                        [ 2 ] Finish Updating This Flight
                        """ + AnsiColors.ANSI_RESET);
                option = Console.checkInt();
                if (option == 2 || option == 1) {
                    break;
                } else if (option == -1) {
                    System.out.println(AnsiColors.ANSI_RED + "* Attention => You can only enter numbers ! *" + AnsiColors.ANSI_RESET);
                    Console.pauseProgram();
                } else {
                    System.out.println(AnsiColors.ANSI_RED + "Chosen Option is out of range !" + AnsiColors.ANSI_RESET);
                    Console.pauseProgram();
                }
            }
        } while (option != 2);
        System.out.println(AnsiColors.ANSI_GREEN + ">> Flight is successfully updated ! <<\n" + AnsiColors.ANSI_RESET);
        Console.pressKey();
    }

    public void printFields(String flightId) {
        Flight flight = flights.findInFile(flightId);

        System.out.printf(AnsiColors.ANSI_PURPLE + """
                                        
                        { * } Flight Id : %s
                        ( 1 ) Origin : %s
                        ( 2 ) Destination : %s
                        ( 3 ) Date : %s
                        ( 4 ) Time : %s
                        ( 5 ) Price : %,d
                        ( 6 ) Seats : %d
                                        
                        """ + AnsiColors.ANSI_RESET,
                flight.getFlightId(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDate(),
                flight.getTime(),
                flight.getPrice(),
                flight.getSeats());
    }

    /**
     * This method is for removing flights page.
     */
    public void removeFlightPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_PURPLE + """
                ______________________________________________________________
                ║                      [ REMOVE FLIGHTS ]                    ║
                ``````````````````````````````````````````````````````````````
                    
                """ + AnsiColors.ANSI_RESET);
        removingFlight();
    }

    /**
     * This method is for removing flights.
     */
    public void removingFlight() {
        String flightId = searchFlightId();

        Console.pauseProgram();
        System.out.print(AnsiColors.ANSI_PURPLE + """
                Are you sure that you wanna remove the flight?
                - Enter 'y' for yes.
                - Enter 'n' for no.
                """ + AnsiColors.ANSI_RESET);

        do {
            String answer = input.nextLine();
            Console.pauseProgram();

            if (answer.equals("y")) {
                flights.removeFromFile(flightId);
                System.out.println(AnsiColors.ANSI_GREEN + ">> Flight is successfully removed ! <<\n" + AnsiColors.ANSI_RESET);
                break;
            } else if (answer.equals("n")) {
                System.out.println(AnsiColors.ANSI_GREEN + ">> Flight isn't removed ! <<\n" + AnsiColors.ANSI_RESET);
                break;
            } else {
                System.out.println(AnsiColors.ANSI_RED + "Invalid Input ! Try Again : " + AnsiColors.ANSI_RESET);
            }
        } while (true);
        Console.pressKey();
    }

    /**
     * This method is for searching flight id.
     * @return the found flight
     */
    public String searchFlightId() {
        while (true) {
            System.out.print("- Flight Id : ");
            String flightId = input.nextLine();

            Flight flight = flights.findInFile(flightId);
            if (flight == null) {
                System.out.println(AnsiColors.ANSI_RED + "Chosen flight doesn't exist ! Try Again !" + AnsiColors.ANSI_RESET);
            } else {
                return flightId;
            }
        }
    }

    /**
     * This method is for the flight id validation.
     * @return the accepted flight id
     */
    public String flightIdValidation() {
        while (true) {
            String flightId = input.nextLine();
            if (!flightId.matches("^[a-zA-z]{2}-[0-9]{2}$")) {
                System.out.println(AnsiColors.ANSI_RED + """
                        ** Flight Id is not acceptable !
                        (Correct Format => [a-zA-Z]*2 - [0-9]*2)
                        (Example => SA-18)
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            } else if (flights.findInFile(flightId) != null) {
                System.out.println(AnsiColors.ANSI_RED + """
                        ** Flight Id already exists !
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            } else {
                return flightId;
            }
        }
    }

    /**
     * This method is for the city name validation.
     * @return the accepted city name
     */
    public String cityValidation() {
        while (true) {
            String city = input.nextLine();
            if (city.matches("[a-zA-Z]+$")){
                return city.substring(0, 1).toUpperCase() + city.substring(1);
            } else {
                System.out.println(AnsiColors.ANSI_RED + """
                        ** City name should not include numbers !
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            }
        }
    }

    /**
     * This method is for the date validation.
     * @return the accepted date
     */
    public String dateValidation() {
        while (true) {
            SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = input.nextLine();
            try
            {
                Date date = dateInput.parse(strDate);
                strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                return strDate;
            }
            catch (ParseException e)
            {
                System.out.println(AnsiColors.ANSI_RED + """
                        ** Invalid date format !
                        (Correct Format => yyyy-MM-dd)
                        (Example => 1402-01-10)
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            }
        }
    }

    /**
     * This method is for the time validation.
     * @return the accepted time
     */
    public String timeValidation() {
        while (true) {
            String time = input.nextLine();
            if (!time.matches("^[0-9]{2}:[0-9]{2}$")) {
                System.out.println(AnsiColors.ANSI_RED + """
                        ** Time format is not acceptable !
                        (Correct Format => hh:mm)
                        (Example => 12:30)
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            } else {
                return time;
            }
        }
    }

    /**
     * This method is for the price validation.
     * @return the accepted price
     */
    public int priceValidation() {
        while (true) {
            int price = Console.checkInt();

            if (price < 0) {
                System.out.print(AnsiColors.ANSI_RED + """
                        Invalid Price Input !
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            } else {
                return price;
            }
        }
    }

    /**
     * This method is for the seat validation.
     * @return the accepted seat
     */
    public int seatsValidation() {
        while (true) {
            int seats = Console.checkInt();

            if (seats < 0) {
                System.out.print(AnsiColors.ANSI_RED + """
                        Invalid Seat Input !
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            } else if (seats > 1000) {
                System.out.print(AnsiColors.ANSI_RED + """
                        Airplane doesn't have this capacity !
                        Try Again :\s""" + AnsiColors.ANSI_RESET);
            } else {
                return seats;
            }
        }
    }

    /**
     * This method is for the flight schedules page.
     */
    public void flightSchedulesPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_PURPLE + """
                ______________________________________________________________
                ║                     [ FLIGHT SCHEDULES ]                   ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);
        printFlightSchedules();
        Console.pressKey();
    }

    /**
     * This method is for printing all the flights.
     */
    public void printFlightSchedules() {
        System.out.printf(AnsiColors.ANSI_CYAN + """
                +=====================================================================================+
                ║ %-10s ║ %-10s ║ %-13s ║ %-10s ║ %-6s ║ %-10s ║ %-6s ║
                +=====================================================================================+
                """, "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats");

        flights.printFlights();
        System.out.print(AnsiColors.ANSI_RESET);
    }
}