package datamanager;

import data.*;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PassengerActions {
    private final Scanner input;
    private DataHolder<Passenger> passengers;
    private final Flights flights;
    private final Tickets tickets;

    public PassengerActions(Scanner input, DataHolder<Passenger> passengers, Flights flights, Tickets tickets) {
        this.input = input;
        this.passengers = passengers;
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

        List<Integer> matchScores = new ArrayList<>();
        List<Flight> foundFlights;

        try {
            foundFlights = flights.calculateMatchScores(matchScores, flightId, origin, destination, date, time, startPriceRange, endPriceRange);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (searchedFlightExist(matchScores)) {
            sortArrays(matchScores, foundFlights);
            printSearchedFlights(matchScores, foundFlights);
        } else {
            System.out.println(AnsiColors.ANSI_RED + "No flights found !" + AnsiColors.ANSI_RESET);
        }
        Console.pressKey();
    }

    public void sortArrays(List<Integer> matchScores, List<Flight> foundFlights) {
        int size = matchScores.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (matchScores.get(i) > matchScores.get(j)) {
                    int temp = matchScores.get(i);
                    matchScores.set(i, matchScores.get(j));
                    matchScores.set(j, temp);

                    Flight tempFlight;
                    tempFlight = foundFlights.get(i);
                    foundFlights.set(i, foundFlights.get(j));
                    foundFlights.set(j, tempFlight);
                }
            }
        }
    }

    /**
     * This method checks if the searched flight exists or not.
     * @param matchScores the array that saves the matching score of the flights.
     * @return true if any flight matches the searched flight.
     */
    public boolean searchedFlightExist(List<Integer> matchScores) {
        for (int matchScore : matchScores) {
            if (matchScore > 0) {
                return true;
            }
        }
        return false;
    }

    public void printSearchedFlights(List<Integer> matchScores, List<Flight> foundFlights) {
        System.out.printf(AnsiColors.ANSI_CYAN + """
                +======================================================================================================+
                ║ %-14s ║ %-10s ║ %-10s ║ %-13s ║ %-10s ║ %-6s ║ %-10s ║ %-6s ║
                +======================================================================================================+
                """, "Matching Score", "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats");

        int size = matchScores.size();
        for (int i = size - 1; i >= 0; i--) {
            Flight flight = foundFlights.get(i);
            if (matchScores.get(i) > 0) {
                System.out.printf("""
                                | %-14d | %-10s | %-10s | %-13s | %-10s | %-6s | %,-10d | %-6d |
                                +------------------------------------------------------------------------------------------------------+
                                """,
                        matchScores.get(i), flight.getFlightId(), flight.getOrigin(),
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

        flights.printFlights();
        System.out.print(AnsiColors.ANSI_RESET);

        bookingTicket(passenger);
    }

    /**
     * This method is for booking ticket.
     * @param passenger active passenger
     */
    public void bookingTicket(Passenger passenger) {
        String flightId = searchFlightId();
        Flight flight = flights.findInFile(flightId);

        if (!checkEmptySeats(flight)) {
            System.out.println(AnsiColors.ANSI_RED + "* NO AVAILABLE SEATS !" + AnsiColors.ANSI_RESET);
        } else if (!checkCharge(flight, passenger)) {
            System.out.println(AnsiColors.ANSI_RED + "* YOUR CHARGE IS NOT ENOUGH" + AnsiColors.ANSI_RESET);
        } else {
            updateDetailsAfterBooking(flight, passenger);
            Console.pauseProgram();
            String ticketId = tickets.ticketIdGenerator(flightId);
            tickets.addToFile(new Ticket(ticketId, passenger.getUsername(), flightId));

            System.out.println(AnsiColors.ANSI_GREEN + "Ticket successfully booked !" + AnsiColors.ANSI_RESET);

            printTicket(flight, passenger, ticketId);
        }
        Console.pressKey();
    }

    /**
     * This method is for searching the flight id.
     * @return found flight
     */
    public String searchFlightId() {
        while (true) {
            System.out.print("~ Flight Id : ");
            String flightId = input.nextLine();

            Flight flight = flights.findInFile(flightId);
            if (flight == null) {
                System.out.println(AnsiColors.ANSI_RED + "Chosen flight id doesn't exist ! Try Again !" + AnsiColors.ANSI_RESET);
            } else {
                return flightId;
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
        int charge = passengers.findInFile(passenger.getUsername()).getCharge();
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
        flights.updateFile(flight.getFlightId(), 7, String.valueOf(emptySeats));
        flights.updateFile(flight.getFlightId(), 8, String.valueOf(true));

        int charge = passengers.findInFile(passenger.getUsername()).getCharge();
        int price = flight.getPrice();
        passengers.updateFile(passenger.getUsername(), 3, String.valueOf(charge - price));
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
        String ticketId = searchTicketId();

        updateSeats(ticketId);
        returnCharge(passenger, ticketId);

        tickets.removeFromFile(ticketId);

        Console.pauseProgram();
        System.out.println(AnsiColors.ANSI_GREEN + "Ticket successfully cancelled ! " + AnsiColors.ANSI_RESET);
        Console.pressKey();
    }

    /**
     * This method is for searching the ticket id.
     * @return the found ticket
     */
    public String searchTicketId() {
        while (true) {
            System.out.print("~ Ticket Id : ");
            String ticketId = input.nextLine();

            Ticket ticket = tickets.findInFile(ticketId);
            if (ticket == null) {
                System.out.println(AnsiColors.ANSI_RED + "Ticket is NOT FOUND ! Try Again !" + AnsiColors.ANSI_RESET);
            } else {
                return ticketId;
            }
        }
    }

    public void returnCharge(Passenger passenger, String ticketId) {
        Flight flight = flights.findInFile(tickets.findInFile(ticketId).getFlightId());
        int price = flight.getPrice();
        int charge = passenger.getCharge();
        passengers.updateFile(passenger.getUsername(), 3, String.valueOf(charge + price));
    }

    public void updateSeats(String ticketId) {
        String flightId = tickets.findInFile(ticketId).getFlightId();
        Flight flight = flights.findInFile(flightId);
        int emptySeats = flight.getSeats() + 1;
        flights.updateFile(flightId, 7, String.valueOf(emptySeats));
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

        List<Ticket> foundTickets;
        foundTickets = tickets.bookedTickets(passenger.getUsername());

        if (foundTickets.size() > 0) {
            printBookedTicket(foundTickets);
        } else {
            System.out.println(AnsiColors.ANSI_RED + "NO booked tickets found !" + AnsiColors.ANSI_RESET);
        }
        Console.pressKey();
    }

    public void printBookedTicket(List<Ticket> foundTickets) {
        System.out.printf(AnsiColors.ANSI_CYAN + """
                +--------------------------------------------------------------------------------------------------+
                | %-10s | %-10s | %-10s | %-13s | %-10s | %-6s | %-10s | %-6s |
                +--------------------------------------------------------------------------------------------------+
                """, "Ticket Id", "Flight Id", "Origin", "Destination", "Date", "Time", "Price", "Seats" );

        foundTickets.forEach(ticket -> {
            System.out.print(ticket.toString(flights.findInFile(ticket.getFlightId())));
        });
        System.out.print(AnsiColors.ANSI_RESET);
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
        int currentCharge = passengers.findInFile(passenger.getUsername()).getCharge();
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
        passengers.updateFile(passenger.getUsername(), 3, String.valueOf(currentCharge + addedCharge));
        System.out.println(AnsiColors.ANSI_GREEN + ">> Charge successfully added ! <<" + AnsiColors.ANSI_RESET);
        System.out.printf("$ Current charge : %,d%n", passengers.findInFile(passenger.getUsername()).getCharge());
        Console.pressKey();
    }
}