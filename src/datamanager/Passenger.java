package datamanager;

import data.*;
import utils.Console;

import java.util.ArrayList;
import java.util.Scanner;

public class Passenger {
    private final Scanner input = new Scanner(System.in);
    private final Flights flights;
    private final Tickets tickets;

    public Passenger(Flights flights, Tickets tickets) {
        this.flights = flights;
        this.tickets = tickets;
    }

    public void addingCharge(User user) {
        int currentCharge = user.getCharge();
        System.out.printf("$ Current Charge : %,d%n", currentCharge);

        int addedCharge;
        while (true) {
            System.out.print("- How much money do you wanna add to your charge? : ");
            addedCharge = Console.checkInt();
            if (addedCharge == -1) {
                System.out.println("* Attention => You can only enter numbers ! *");
            } else {
                break;
            }
        }

        Console.pauseProgram();
        user.setCharge(currentCharge + addedCharge);
        System.out.println(">> Charge successfully added ! <<");
        System.out.printf("$ Current charge : %,d%n", user.getCharge());
        Console.pressKey();
    }

    public void searchFlightTickets() {
        String flightId = "";
        String origin = "";
        String destination = "";
        String date = "";
        String time = "";
        int startPriceRange = 0;
        int endPriceRange = 0;

        int option;
        do {
            System.out.printf("""
                    { 1 } - Flight Id : %s
                    { 2 } - Origin : %s
                    { 3 } - Destination : %s
                    { 4 } - Date : %s
                    { 5 } - Time : %s
                    { 6 } - Price Range From : %,d To : %,d%n%n
                    """, flightId, origin, destination, date, time, startPriceRange, endPriceRange);

            System.out.print("- Choose A Field As A Filter : ");
            int fieldNum = Console.checkInt();

            switch (fieldNum) {
                case 1 -> {
                    System.out.print("- Flight Id : ");
                    flightId = input.nextLine();
                }
                case 2 -> {
                    System.out.print("- Origin : ");
                    origin = input.nextLine();
                }
                case 3 -> {
                    System.out.print("- Destination : ");
                    destination = input.nextLine();
                }
                case 4 -> {
                    System.out.print("- Date : ");
                    date = input.nextLine();
                }
                case 5 -> {
                    System.out.print("- Time : ");
                    time = input.nextLine();
                }
                case 6 -> {
                    System.out.print("- Price Range From : ");
                    startPriceRange = Console.checkInt();
                    System.out.print("To :");
                    endPriceRange = Console.checkInt();
                }
                default -> System.out.println("!! Chosen field is out of range !!");
            }

            while (true) {
                System.out.print("""
                                            
                        { 1 } Add Another Field As Filter
                        { 2 } Search
                        """);

                option = Console.checkInt();
                if (option == 2) {
                    System.out.println(" >> Searching . . .");
                    Console.pauseProgram();
                    break;
                } else if (option == 1) {
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

        int size = flights.getFlights().size();
        int[] matchScores = new int[size];
        int[] flightIndex = new int[size];

        flights.calculateMatchScores(matchScores, flightIndex, flightId, origin, destination, date, time, startPriceRange, endPriceRange);
        sortArrays(matchScores, flightIndex);

        printSearchedFlights(matchScores, flightIndex);
        Console.pressKey();
    }

    public void sortArrays(int[] matchScores, int[] flightIndex) {
        int size = matchScores.length;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (matchScores[i] > matchScores[j]) {
                    int temp = matchScores[i];
                    matchScores[i] = matchScores[j];
                    matchScores[j] = temp;

                    temp = flightIndex[i];
                    flightIndex[i] = flightIndex[j];
                    flightIndex[j] = temp;
                }
            }
        }
    }

    public void printSearchedFlights(int[] matchScores, int[] flightIndex) {
        System.out.printf("""
                +======================================================================================================+
                ║ %-14s ║ %-10s ║ %-10s ║ %-13s ║ %-10s ║ %-6s ║ %-10s ║ %-6s ║
                +======================================================================================================+
                """, "Matching Score", "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats");

        int size = flights.getFlights().size();
        for (int i = size - 1; i >= 0; i--) {
            Flight flight = flights.getFlights().get(flightIndex[i]);
            if (matchScores[i] > 0) {
                System.out.printf("""
                                | %-14d | %-10s | %-10s | %-13s | %-10s | %-6s | %,-10d | %-6d |
                                +------------------------------------------------------------------------------------------------------+
                                """,
                        matchScores[i], flight.getFlightId(), flight.getOrigin(),
                        flight.getDestination(), flight.getDate(), flight.getTime(),
                        flight.getPrice(), flight.getSeats());
            }
        }
    }

    public void bookingTicket(User user) {
        Flight flight = searchFlightId();

        if (!checkEmptySeats(flight)) {
            System.out.println("* NO AVAILABLE SEATS !");
        } else if (!checkCharge(flight, user)) {
            System.out.println("* YOUR CHARGE IS NOT ENOUGH");
        } else {
            updateDetailsAfterBooking(flight, user);
            Console.pauseProgram();
            String ticketId = tickets.addTicket(flight, user);
            flight.setBooked(true);
            System.out.println("Ticket successfully booked !");

            printTicket(flight, user, ticketId);
        }
        Console.pressKey();
    }

    public Flight searchFlightId() {
        while (true) {
            System.out.print("~ Flight Id : ");
            String flightId = input.nextLine();

            Flight flight = flights.findFlight(flightId);
            if (flight == null) {
                System.out.println("Chosen flight id doesn't exist ! Try Again !");
            } else {
                return flight;
            }
        }
    }

    public boolean checkEmptySeats(Flight flight) {
        int emptySeats = flight.getSeats();
        return emptySeats > 0;
    }

    public boolean checkCharge(Flight flight, User user) {
        int charge = user.getCharge();
        int price = flight.getPrice();
        return charge >= price;
    }

    public void updateDetailsAfterBooking(Flight flight, User user) {
        int emptySeats = flight.getSeats() - 1;
        flight.setSeats(emptySeats);

        int charge = user.getCharge();
        int price = flight.getPrice();
        user.setCharge(charge - price);
    }

    public void printTicket(Flight flight, User user, String ticketId) {
        System.out.printf("""
                    +===============================================+
                    |   * Ticket ID :   %s                  |
                    |   ~ Passenger :   %s                       |
                    |                                               |
                    |   - Flight Id :   %s                       |
                    |   - From :    %s   To :    %s          |
                    |   - Date :    %s    Time :    %s   |
                    |                                               |
                    +===============================================+
                    """,
                ticketId, user.getUsername(), flight.getFlightId(),
                flight.getOrigin(), flight.getDestination(), flight.getDate(),
                flight.getTime());
    }

    public void cancellingTicket(User user) {
        System.out.print("~ Ticket ID : ");
        Ticket ticket = searchTicketId();

        updateSeats(ticket);
        returnCharge(user, ticket);

        tickets.removeTicket(ticket);

        Console.pauseProgram();
        System.out.println("Ticket successfully cancelled ! ");
        Console.pressKey();
    }

    public Ticket searchTicketId() {
        while (true) {
            System.out.print("~ Ticket Id : ");
            String ticketId = input.nextLine();

            Ticket ticket = tickets.findTicket(ticketId);
            if (ticket == null) {
                System.out.println("Ticket is NOT FOUND ! Try Again !");
            } else {
                return ticket;
            }
        }
    }

    public void returnCharge(User user, Ticket ticket) {
        int price = ticket.getFlight().getPrice();
        int charge = user.getCharge();
        user.setCharge(charge + price);
    }

    public void updateSeats(Ticket ticket) {
        Flight flight = ticket.getFlight();
        int emptySeats = flight.getSeats() + 1;
        flight.setSeats(emptySeats);
    }

    public void printBookedTicket(User user) {
        showMessages(user);
        System.out.printf("""
                +--------------------------------------------------------------------------------------------------+
                | %-10s | %-10s | %-10s | %-13s | %-10s | %-6s | %-10s | %-6s |
                +--------------------------------------------------------------------------------------------------+
                """, "Ticket Id", "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats" );

        tickets.getTickets().forEach(ticket -> {
            if (ticket.getPassenger().equals(user)) {
                System.out.print(ticket);
            }
        });
        Console.pressKey();
    }

    public void showMessages(User user) {
        ArrayList<Ticket> removed = new ArrayList<>();

        for (Ticket ticket : tickets.getTickets()) {
            if (ticket.getPassenger().equals(user)) {
                if (ticket.isRemoved()) {
                    System.out.println(ticket.getMessage());
                    removed.add(ticket);
                } else if (ticket.isUpdated()) {
                    System.out.println(ticket.getMessage());
                    ticket.setUpdated(false);
                }
            }
        }

        removed.forEach(ticket -> {
            if (ticket.isRemoved()) {
                tickets.removeTicket(ticket);
            }
        });
    }
}