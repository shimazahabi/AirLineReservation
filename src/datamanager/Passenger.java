package datamanager;

import utils.Console;

import java.util.Random;
import java.util.Scanner;

public class Passenger {
    private final Scanner input = new Scanner(System.in);
    private final Users users;
    private final Flights flights;
    private final Tickets tickets;

    public Passenger(Users users, Flights flights, Tickets tickets) {
        this.users = users;
        this.flights = flights;
        this.tickets = tickets;
    }

    public void addingCharge(String username) {
        int currentCharge = users.charge(username);
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
        users.updateCharge(username, currentCharge + addedCharge);
        System.out.println(">> Charge successfully added ! <<");
        System.out.printf("$ Current charge : %,d%n", users.charge(username));
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

        flights.showSearchedFlights(matchScores, flightIndex);
    }

    public void bookingTicket(String username) {
        String flightId = searchFlightId();

        if (!checkEmptySeats(flightId)) {
            System.out.println("* NO AVAILABLE SEATS !");
        } else if (!checkCharge(flightId, username)) {
            System.out.println("* YOUR CHARGE IS NOT ENOUGH");
        } else {
            updateDetailsAfterBooking(flightId, username);
            Console.pauseProgram();
            String ticketId = generateTicketId();
            tickets.addTicket(flights.findFlight(flightId), users.findUser(username), ticketId);
            flights.isBooked(flightId);
            System.out.println("Ticket successfully booked !");

            System.out.printf("""
                    +===============================================+
                    |   * Ticket ID :   %s                   |
                    |   ~ Passenger :   %s                       |
                    |                                               |
                    |   - Flight Id :   %s                       |
                    |   - From :    %s   To :    %s          |
                    |   - Date :    %s    Time :    %s   |
                    |                                               |
                    +===============================================+
                    """, ticketId, username, flightId, flights.origin(flightId),
                    flights.destination(flightId), flights.date(flightId), flights.time(flightId));
        }
        Console.pressKey();
    }

    public String searchFlightId() {
        while (true) {
            System.out.print("~ Flight Id : ");
            String flightId = input.nextLine();

            if (flights.findFlight(flightId) == null) {
                System.out.println("Chosen flight id doesn't exist ! Try Again !");
            } else {
                return flightId;
            }
        }
    }

    public void updateDetailsAfterBooking(String flightId, String username) {
        int emptySeats = flights.seats(flightId) - 1;
        flights.updateFlight(flightId, 7, Integer.toString(emptySeats));

        int charge = users.charge(username);
        int price = flights.price(flightId);
        users.updateCharge(username, charge - price);
    }

    public boolean checkEmptySeats(String flightId) {
        int emptySeats = flights.seats(flightId);
        return emptySeats > 0;
    }

    public boolean checkCharge(String flightId, String username) {
        int charge = users.charge(username);
        int price = flights.price(flightId);
        return charge >= price;
    }

    public String generateTicketId() {
        Random random = new Random();
        return String.valueOf(random.nextInt(999999999));
    }

    public void cancellingTicket(String username) {
        System.out.print("~  ID : ");
        String ticketId = searchTicketId();

        updateSeats(ticketId);
        returnCharge(username, ticketId);

        tickets.removeTicket(ticketId);

        Console.pauseProgram();
        System.out.println("Ticket successfully cancelled ! ");
        Console.pressKey();
    }

    public String searchTicketId() {
        while (true) {
            System.out.print("~ Ticket Id : ");
            String ticketId = input.nextLine();

            if (tickets.findTicketWithTicketId(ticketId) == null) {
                System.out.println("Ticket is NOT FOUND ! Try Again !");
            } else {
                return ticketId;
            }
        }
    }

    public void returnCharge(String username, String ticketId) {
        int price = tickets.findTicketWithTicketId(ticketId).getFlight().getPrice();
        int charge = users.findUser(username).getCharge();
        users.updateCharge(username, charge + price);
    }
    public void updateSeats(String ticketId) {
        int emptySeats = tickets.findTicketWithTicketId(ticketId).getFlight().getSeats() + 1;
        String flightId = tickets.findTicketWithTicketId(ticketId).getFlight().getFlightId();
        flights.updateFlight(flightId, 7, Integer.toString(emptySeats));
    }

    public void printBookedTicket(String username) {
        tickets.showMessages(users.findUser(username));

        System.out.println("\n----------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-10s | %-13s | %-10s | %-6s | %-10s | %-6s |%n",
                "Ticket Id", "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats");
        System.out.println("----------------------------------------------------------------------------------------------------");

        tickets.showBookedFlights(users.findUser(username));
        Console.pressKey();
    }
}