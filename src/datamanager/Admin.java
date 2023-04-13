package datamanager;

import utils.Console;

import java.util.Scanner;

public class Admin {
    private final Scanner input = new Scanner(System.in);
    private final Flights flights;
    private final Tickets tickets;

    public Admin(Flights flights, Tickets tickets) {
        this.flights = flights;
        this.tickets = tickets;
    }

    public void addingFlight() {
        System.out.print("- Flight Id : ");
        String flightId = flightIdValidation();

        System.out.print("- Origin : ");
        String origin = cityValidation();

        System.out.print("- Destination : ");
        String destination = cityValidation();

        System.out.print("- Date : ");
        String date = input.nextLine();

        System.out.print("- Time : ");
        String time = input.nextLine();

        int price;
        while (true) {
            System.out.print("- Price : ");
            price = Console.checkInt();

            if (price < 0) {
                System.out.println("Invalid Price Input !");
            } else {
                break;
            }
        }

        int seats;
        while (true) {
            System.out.print("- Seats : ");
            seats = Console.checkInt();

            if (seats < 0) {
                System.out.println("Invalid Seat Input !");
            } else {
                break;
            }
        }

        flights.addFlight(flightId, origin, destination, date, time, price, seats);
        System.out.println("\n>> Flight is successfully added ! <<\n");
        Console.pressKey();
    }

    public void updatingFlight() {
        String flightId = searchFlightId();

        int option;
        do {
            printFields(flightId);
            System.out.print("- Choose A Field : ");
            int fieldNum = Console.checkInt();
            String update;

            if (fieldNum >= 1 && fieldNum <= 7) {
                tickets.updateTicketsMessage(flightId);
            }

            switch (fieldNum) {
                case 1 -> {
                    System.out.print("- Update Flight Id : ");
                    update = flightIdValidation();
                    flights.updateFlight(flightId, 1, update);
                }
                case 2 -> {
                    System.out.print("- Update Origin : ");
                    update = cityValidation();
                    flights.updateFlight(flightId, 2, update);
                }
                case 3 -> {
                    System.out.print("- Update Destination : ");
                    update = cityValidation();
                    flights.updateFlight(flightId, 3, update);
                }
                case 4 -> {
                    System.out.print("- Update Date : ");
                    update = input.nextLine();
                    flights.updateFlight(flightId, 4, update);
                }
                case 5 -> {
                    System.out.print("- Update Time : ");
                    update = input.nextLine();
                    flights.updateFlight(flightId, 5, update);
                }
                case 6 -> {
                    if (flights.booked(flightId)) {
                        System.out.println("* You can't change the price, because this flight is booked by passengers !");
                    } else {
                        System.out.print("- Update Price : ");
                        update = input.nextLine();
                        flights.updateFlight(flightId, 6, update);
                    }
                }
                case 7 -> {
                    System.out.print("- Update Seats : ");
                    update = input.nextLine();
                    flights.updateFlight(flightId, 7, update);
                }
                case -1 -> {
                    System.out.println("* Attention => You can only enter numbers ! *");
                    Console.pauseProgram();
                }
                default -> {
                    System.out.println("Chosen field is out of range !");
                    Console.pauseProgram();
                }
            }

            while (true) {
                System.out.print("""
                                            
                        { 1 } - Update Another Field
                        { 2 } - Finish Updating This Flight
                        """);
                option = Console.checkInt();
                if (option == 2 || option == 1) {
                    break;
                } else if (option == -1) {
                    System.out.println("* Attention => You can only enter numbers ! *");
                    Console.pauseProgram();
                } else {
                    System.out.println("Chosen Option is out of range !");
                    Console.pauseProgram();
                }
            }
        } while (option != 2);
        System.out.println(">> Flight is successfully updated ! <<");
        Console.pressKey();
    }

    public void printFields(String flightId) {
        System.out.printf("""
                                        
                        { 1 } Flight Id : %s
                        { 2 } Origin : %s
                        { 3 } Destination : %s
                        { 4 } Date : %s
                        { 5 } Time : %s
                        { 6 } Price : %,d
                        { 7 } Seats : %d
                                        
                        """,
                flights.flightId(flightId),
                flights.origin(flightId),
                flights.destination(flightId),
                flights.date(flightId),
                flights.time(flightId),
                flights.price(flightId),
                flights.seats(flightId));
    }

    public void removingFlight() {
        String flightId = searchFlightId();

        Console.pauseProgram();
        System.out.println("""
                Are you sure that you wanna remove the flight?
                - Enter y for yes.
                - Enter n for no.
                """);
        String answer = input.nextLine();
        Console.pauseProgram();

        if (answer.equals("y")) {
            tickets.removingTicketsMessage(flightId);
            flights.removeFlight(flightId);
            System.out.println(">> Flight is Successfully removed ! <<");
        } else if (answer.equals("n")) {
            System.out.println(">> Flight isn't removed ! <<");
        }
        Console.pressKey();
    }

    public String searchFlightId() {
        while (true) {
            System.out.print("- Flight Id : ");
            String flightId = input.nextLine();

            if (flights.findFlight(flightId) == null) {
                System.out.println("Chosen flight doesn't exist ! Try Again !");
            } else {
                return flightId;
            }
        }
    }

    public String flightIdValidation() {
        while (true) {
            String flightId = input.nextLine();
            if (!flightId.matches("^[a-zA-z]{2}-[0-9]{2}$")) {
                System.out.println("""
                        ** Flight Id is not acceptable !
                        (Correct Format => [a-zA-Z]*2 - [0-9]*2)
                        (Example => SA-18)
                        Try Again :\s""");
            } else if (flights.findFlight(flightId) != null) {
                System.out.println("""
                        ** Flight Id already exists !
                        Try Again :\s""");
            } else {
                return flightId;
            }
        }
    }

    public String cityValidation() {
        while (true) {
            String city = input.nextLine();
            if (city.matches("[a-zA-Z]+$")){
                return city.substring(0, 1).toUpperCase() + city.substring(1);
            } else {
                System.out.println("""
                        ** City name should not include numbers !
                        Try Again :\s""");
            }
        }
    }

    public void printFlightSchedules() {
        System.out.printf("""
                +=====================================================================================+
                ║ %-10s ║ %-10s ║ %-13s ║ %-10s ║ %-6s ║ %-10s ║ %-6s ║
                +=====================================================================================+
                """, "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats");

        flights.showFlights();
    }
}