package datamanager;

import data.*;
import utils.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PassengerActions {
    private final Scanner input;
    private final Flights flights;
    private final Tickets tickets;

    public PassengerActions(Scanner input, Flights flights, Tickets tickets) {
        this.input = input;
        this.flights = flights;
        this.tickets = tickets;
    }

    /**
     * This method is for searching flight tickets page.
     */
    public void searchFlightTicketsPage() {
        Console.clear();
        System.out.print(AnsiColors.ANSI_YELLOW + """
                ______________________________________________________________
                ║                  [ SEARCH FLIGHT TICKETS ]                 ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);
        searchFlightTickets();
    }

    /**
     * This method is for searching flight tickets.
     */
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
            System.out.printf(AnsiColors.ANSI_YELLOW + """
                    ( 1 ) Flight Id : %s
                    ( 2 ) Origin : %s
                    ( 3 ) Destination : %s
                    ( 4 ) Date : %s
                    ( 5 ) Time : %s
                    ( 6 ) Price Range From : %,d To : %,d%n%n
                    """ + AnsiColors.ANSI_RESET, flightId, origin, destination, date, time, startPriceRange, endPriceRange);

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
                default -> System.out.println(AnsiColors.ANSI_RED + "!! Chosen field is out of range !!" + AnsiColors.ANSI_RESET);
            }

            while (true) {
                System.out.print(AnsiColors.ANSI_YELLOW + """
                                            
                        [ 1 ] Add Another Field As Filter
                        [ 2 ] Search
                        """ + AnsiColors.ANSI_RESET);

                option = Console.checkInt();
                if (option == 2) {
                    System.out.println(" >> Searching . . .");
                    Console.pauseProgram();
                    break;
                } else if (option == 1) {
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

        int size = flights.getFlights().size();
        int[] matchScores = new int[size];
        int[] flightIndex = new int[size];

        flights.calculateMatchScores(matchScores, flightIndex, flightId, origin, destination, date, time, startPriceRange, endPriceRange);

        if (searchedFlightExist(matchScores)) {
            sortArrays(matchScores, flightIndex);
            printSearchedFlights(matchScores, flightIndex);
        } else {
            System.out.println(AnsiColors.ANSI_RED + "No flights found !" + AnsiColors.ANSI_RESET);
        }
        Console.pressKey();
    }

    /**
     * This method sorts two arrays simultaneously.
     * @param matchScores the array that saves the matching score of the flights.
     * @param flightIndex the array that saves the flight indexes.
     */
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

    /**
     * This method checks if the searched flight exists or not.
     * @param matchScores the array that saves the matching score of the flights.
     * @return true if any flight matches the searched flight.
     */
    public boolean searchedFlightExist(int[] matchScores) {
        for (int matchScore : matchScores) {
            if (matchScore > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method prints the searched flights.
     * @param matchScores the array that saves the matching score of the flights.
     * @param flightIndex the array that saves the flight indexes.
     */
    public void printSearchedFlights(int[] matchScores, int[] flightIndex) {
        System.out.printf(AnsiColors.ANSI_CYAN + """
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
        System.out.print(AnsiColors.ANSI_RESET);
    }

    /**
     * This method is for booking ticket page.
     * @param passenger active passenger
     */
    public void bookingTicketPage(Passenger passenger) {
        Console.clear();
        System.out.print(AnsiColors.ANSI_YELLOW + """
                ______________________________________________________________
                ║                       [ BOOK TICKET ]                      ║
                ``````````````````````````````````````````````````````````````
                
                """ + AnsiColors.ANSI_RESET);

        System.out.printf(AnsiColors.ANSI_CYAN + """
                +=====================================================================================+
                ║ %-10s ║ %-10s ║ %-13s ║ %-10s ║ %-6s ║ %-10s ║ %-6s ║
                +=====================================================================================+
                """, "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats");

        flights.getFlights().forEach(System.out::print);
        System.out.print(AnsiColors.ANSI_RESET);

        bookingTicket(passenger);
    }

    /**
     * This method is for booking ticket.
     * @param passenger active passenger
     */
    public void bookingTicket(Passenger passenger) {
        Flight flight = searchFlightId();

        if (!checkEmptySeats(flight)) {
            System.out.println(AnsiColors.ANSI_RED + "* NO AVAILABLE SEATS !" + AnsiColors.ANSI_RESET);
        } else if (!checkCharge(flight, passenger)) {
            System.out.println(AnsiColors.ANSI_RED + "* YOUR CHARGE IS NOT ENOUGH" + AnsiColors.ANSI_RESET);
        } else {
            updateDetailsAfterBooking(flight, passenger);
            Console.pauseProgram();
            String ticketId = tickets.addTicket(flight, passenger);
            flight.setBooked(true);
            System.out.println(AnsiColors.ANSI_GREEN + "Ticket successfully booked !" + AnsiColors.ANSI_RESET);

            printTicket(flight, passenger, ticketId);
        }
        Console.pressKey();
    }

    /**
     * This method is for searching the flight id.
     * @return found flight
     */
    public Flight searchFlightId() {
        while (true) {
            System.out.print("~ Flight Id : ");
            String flightId = input.nextLine();

            Flight flight = flights.findFlight(flightId);
            if (flight == null) {
                System.out.println(AnsiColors.ANSI_RED + "Chosen flight id doesn't exist ! Try Again !" + AnsiColors.ANSI_RESET);
            } else {
                return flight;
            }
        }
    }

    /**
     * This method checks if the chosen flight has any empty seat or not.
     * @return true if the flight has empty seats.
     */
    public boolean checkEmptySeats(Flight flight) {
        int emptySeats = flight.getSeats();
        return emptySeats > 0;
    }

    /**
     * This method checks the charge of the user.
     * @param flight chosen flight
     * @param passenger active passenger
     * @return true if the charge is enough for buying the ticket.
     */
    public boolean checkCharge(Flight flight, Passenger passenger) {
        int charge = passenger.getCharge();
        int price = flight.getPrice();
        return charge >= price;
    }

    /**
     * This method updates the details after booking. (seats and charge of the user)
     * @param flight chosen flight
     * @param passenger active passenger
     */
    public void updateDetailsAfterBooking(Flight flight, Passenger passenger) {
        int emptySeats = flight.getSeats() - 1;
        flight.setSeats(emptySeats);

        int charge = passenger.getCharge();
        int price = flight.getPrice();
        passenger.setCharge(charge - price);
    }

    /**
     * This method prints the ticket.
     * @param flight chosen flight
     * @param passenger active passenger
     * @param ticketId unique ticket id
     */
    public void printTicket(Flight flight, Passenger passenger, String ticketId) {
        System.out.printf(AnsiColors.ANSI_BLUE + """
                    +===============================================+
                    |   * Ticket ID :   %s                  |
                    |   ~ Passenger :   %s                       |
                    |                                               |
                    |   - Flight Id :   %s                       |
                    |   - From :    %s   To :    %s          |
                    |   - Date :    %s    Time :    %s   |
                    |                                               |
                    +===============================================+
                    """ + AnsiColors.ANSI_RESET,
                ticketId, passenger.getUsername(), flight.getFlightId(),
                flight.getOrigin(), flight.getDestination(), flight.getDate(),
                flight.getTime());
    }

    /**
     * This method is for ticket cancellation page.
     * @param passenger active passenger
     */
    public void ticketCancellationPage(Passenger passenger) {
        Console.clear();
        System.out.print(AnsiColors.ANSI_YELLOW + """
                ______________________________________________________________
                ║                  [ TICKET CANCELLATION ]                   ║
                ``````````````````````````````````````````````````````````````

                """ + AnsiColors.ANSI_RESET);
        cancellingTicket(passenger);
    }

    /**
     * This method is for cancelling ticket.
     * @param passenger active passenger
     */
    public void cancellingTicket(Passenger passenger) {
        Ticket ticket = searchTicketId();

        updateSeats(ticket);
        returnCharge(passenger, ticket);

        tickets.removeTicket(ticket);

        Console.pauseProgram();
        System.out.println(AnsiColors.ANSI_GREEN + "Ticket successfully cancelled ! " + AnsiColors.ANSI_RESET);
        Console.pressKey();
    }

    /**
     * This method is for searching the ticket id.
     * @return the found ticket
     */
    public Ticket searchTicketId() {
        while (true) {
            System.out.print("~ Ticket Id : ");
            String ticketId = input.nextLine();

            Ticket ticket = tickets.findTicket(ticketId);
            if (ticket == null) {
                System.out.println(AnsiColors.ANSI_RED + "Ticket is NOT FOUND ! Try Again !" + AnsiColors.ANSI_RESET);
            } else {
                return ticket;
            }
        }
    }

    /**
     * This method return charge of the user after cancelling the ticket.
     * @param passenger active passenger
     * @param ticket the cancelled ticket
     */
    public void returnCharge(Passenger passenger, Ticket ticket) {
        int price = ticket.getFlight().getPrice();
        int charge = passenger.getCharge();
        passenger.setCharge(charge + price);
    }

    /**
     * This method updates seats after cancelling.
     * @param ticket cancelled ticket
     */
    public void updateSeats(Ticket ticket) {
        Flight flight = ticket.getFlight();
        int emptySeats = flight.getSeats() + 1;
        flight.setSeats(emptySeats);
    }

    /**
     * This method is for booked tickets page.
     * @param passenger active passenger
     */
    public void bookedTicketsPage(Passenger passenger) {
        Console.clear();
        System.out.print(AnsiColors.ANSI_YELLOW + """
               ______________________________________________________________
               ║                      [ BOOKED TICKETS ]                    ║
               ``````````````````````````````````````````````````````````````
                    
                """ + AnsiColors.ANSI_RESET);
        if (bookedTicketExist(passenger)) {
            printBookedTicket(passenger);
        } else {
            System.out.println(AnsiColors.ANSI_RED + "NO booked tickets found !" + AnsiColors.ANSI_RESET);
        }
        Console.pressKey();
    }

    /**
     * This method checks if any booked ticket exist.
     * @param passenger active passenger
     * @return true if any booked ticket exists.
     */
    public boolean bookedTicketExist(Passenger passenger) {
        for (Ticket ticket : tickets.getTickets()) {
            if (ticket.getPassenger().equals(passenger)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method prints the booked tickets.
     * @param passenger active passenger
     */
    public void printBookedTicket(Passenger passenger) {
        showMessages(passenger);
        System.out.printf(AnsiColors.ANSI_CYAN + """
                +--------------------------------------------------------------------------------------------------+
                | %-10s | %-10s | %-10s | %-13s | %-10s | %-6s | %-10s | %-6s |
                +--------------------------------------------------------------------------------------------------+
                """, "Ticket Id", "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats" );

        tickets.getTickets().forEach(ticket -> {
            if (ticket.getPassenger().equals(passenger)) {
                System.out.print(ticket);
            }
        });
        System.out.print(AnsiColors.ANSI_RESET);
    }

    /**
     * This method prints the messages of the flights if they are removed or updated.
     * @param passenger active passenger
     */
    public void showMessages(Passenger passenger) {
        ArrayList<Ticket> removed = new ArrayList<>();

        for (Ticket ticket : tickets.getTickets()) {
            if (ticket.getPassenger().equals(passenger)) {
                if (ticket.isRemoved()) {
                    System.out.println(AnsiColors.ANSI_RED + ticket.getMessage() + AnsiColors.ANSI_RESET);
                    removed.add(ticket);
                } else if (ticket.isUpdated()) {
                    System.out.println(AnsiColors.ANSI_RED + ticket.getMessage() + AnsiColors.ANSI_RESET);
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

    /**
     * This method is for the add charge page.
     * @param passenger active passenger
     */
    public void addChargePage(Passenger passenger) {
        Console.clear();
        System.out.print(AnsiColors.ANSI_YELLOW + """
                ______________________________________________________________
                ║                        [ ADD CHARGE ]                      ║
                ``````````````````````````````````````````````````````````````
               
                """ + AnsiColors.ANSI_RESET);
        addingCharge(passenger);
    }

    /**
     * This method is for adding charge.
     * @param passenger active passenger
     */
    public void addingCharge(Passenger passenger) {
        int currentCharge = passenger.getCharge();
        System.out.printf("$ Current Charge : %,d%n", currentCharge);

        int addedCharge;
        while (true) {
            System.out.print("- How much money do you wanna add to your charge? : ");
            addedCharge = Console.checkInt();
            if (addedCharge == -1) {
                System.out.println(AnsiColors.ANSI_RED + "* Attention => You can only enter numbers ! *" + AnsiColors.ANSI_RESET);
            } else {
                break;
            }
        }

        Console.pauseProgram();
        passenger.setCharge(currentCharge + addedCharge);
        System.out.println(AnsiColors.ANSI_GREEN + ">> Charge successfully added ! <<" + AnsiColors.ANSI_RESET);
        System.out.printf("$ Current charge : %,d%n", passenger.getCharge());
        Console.pressKey();
    }
}