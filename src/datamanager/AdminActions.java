package datamanager;

import data.Flight;
import utils.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminActions {
    private final Scanner input = new Scanner(System.in);
    private final Flights flights;
    private final Tickets tickets;

    public AdminActions(Flights flights, Tickets tickets) {
        this.flights = flights;
        this.tickets = tickets;
    }

    public void addFlightPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ```````````````````````| ADD FLIGHTS |````````````````````````
                
                """);
        addingFlight();
    }

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
                System.out.println("Origin and destination can not be the same city ! Try Again :");
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

        Console.pressKey();
        flights.addFlight(flightId, origin, destination, date, time, price, seats);
        System.out.println("\n>> Flight is successfully added ! <<\n");
        Console.pressKey();
    }

    public void updateFlightPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````| UPDATE FLIGHTS |```````````````````````
                
                """);
        updatingFlight();
    }

    public void updatingFlight() {
        Flight flight = searchFlightId();

        int option;
        do {
            printFields(flight);
            System.out.print("- Choose A Field : ");
            int fieldNum = Console.checkInt();
            String update1;
            int update2;

            if (fieldNum >= 1 && fieldNum <= 7) {
                tickets.updateTicketsMessage(flight);
            }

            switch (fieldNum) {
                case 1 -> {
                    System.out.print("- Update Flight Id : ");
                    update1 = flightIdValidation();
                    flight.setFlightId(update1);
                }
                case 2 -> {
                    System.out.print("- Update Origin : ");
                    update1 = cityValidation();
                    flight.setOrigin(update1);
                }
                case 3 -> {
                    System.out.print("- Update Destination : ");
                    update1 = cityValidation();
                    flight.setDestination(update1);
                }
                case 4 -> {
                    System.out.print("- Update Date : ");
                    update1 = dateValidation();
                    flight.setDate(update1);
                }
                case 5 -> {
                    System.out.print("- Update Time : ");
                    update1 = timeValidation();
                    flight.setTime(update1);
                }
                case 6 -> {
                    if (flight.isBooked()) {
                        System.out.println("* You can't change the price, because this flight is booked by passengers !");
                    } else {
                        System.out.print("- Update Price : ");
                        update2 = priceValidation();
                        flight.setPrice(update2);
                    }
                }
                case 7 -> {
                    System.out.print("- Update Seats : ");
                    update2 = seatsValidation();
                    flight.setSeats(update2);
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
        System.out.println(">> Flight is successfully updated ! <<\n");
        Console.pressKey();
    }

    public void printFields(Flight flight) {
        System.out.printf("""
                                        
                        ( 1 ) Flight Id : %s
                        ( 2 ) Origin : %s
                        ( 3 ) Destination : %s
                        ( 4 ) Date : %s
                        ( 5 ) Time : %s
                        ( 6 ) Price : %,d
                        ( 7 ) Seats : %d
                                        
                        """,
                flight.getFlightId(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDate(),
                flight.getTime(),
                flight.getPrice(),
                flight.getSeats());
    }

    public void removeFlightPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````| Remove FLIGHTS |```````````````````````
                    
                """);
        removingFlight();
    }

    public void removingFlight() {
        Flight flight = searchFlightId();

        Console.pauseProgram();
        System.out.print("""
                Are you sure that you wanna remove the flight?
                - Enter 'y' for yes.
                - Enter 'n' for no.
                """);

        do {
            String answer = input.nextLine();
            Console.pauseProgram();

            if (answer.equals("y")) {
                tickets.removingTicketsMessage(flight);
                flights.removeFlight(flight);
                System.out.println(">> Flight is successfully removed ! <<\n");
                break;
            } else if (answer.equals("n")) {
                System.out.println(">> Flight isn't removed ! <<\n");
                break;
            } else {
                System.out.println("Invalid Input ! Try Again : ");
            }
        } while (true);
        Console.pressKey();
    }

    public Flight searchFlightId() {
        while (true) {
            System.out.print("- Flight Id : ");
            String flightId = input.nextLine();

            Flight flight = flights.findFlight(flightId);
            if (flight == null) {
                System.out.println("Chosen flight doesn't exist ! Try Again !");
            } else {
                return flight;
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
                System.out.println("""
                        ** Invalid date format !
                        (Correct Format => yyyy-MM-dd)
                        (Example => 1402-01-10)
                        Try Again :\s""");
            }
        }
    }

    public String timeValidation() {
        while (true) {
            String time = input.nextLine();
            if (!time.matches("^[0-9]{2}:[0-9]{2}$")) {
                System.out.println("""
                        ** Time format is not acceptable !
                        (Correct Format => hh:mm)
                        (Example => 12:30)
                        Try Again :\s""");
            } else {
                return time;
            }
        }
    }

    public int priceValidation() {
        while (true) {
            int price = Console.checkInt();

            if (price < 0) {
                System.out.print("""
                        Invalid Price Input !
                        Try Again :\s""");
            } else {
                return price;
            }
        }
    }

    public int seatsValidation() {
        while (true) {
            int seats = Console.checkInt();

            if (seats < 0) {
                System.out.print("""
                        Invalid Seat Input !
                        Try Again :\s""");
            } else if (seats > 1000) {
                System.out.print("""
                        Airplane doesn't have this capacity !
                        Try Again :\s""");
            } else {
                return seats;
            }
        }
    }

    public void flightSchedulesPage() {
        Console.clear();
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                `````````````````````| FLIGHT SCHEDULES |`````````````````````
                
                """);
        printFlightSchedules();
        Console.pressKey();
    }

    public void printFlightSchedules() {
        System.out.printf("""
                +=====================================================================================+
                ║ %-10s ║ %-10s ║ %-13s ║ %-10s ║ %-6s ║ %-10s ║ %-6s ║
                +=====================================================================================+
                """, "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats");

        flights.getFlights().forEach(System.out::print);
    }
}