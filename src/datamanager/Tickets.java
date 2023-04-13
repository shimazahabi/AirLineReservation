package datamanager;

import data.Flight;
import data.Ticket;
import data.User;

import java.util.ArrayList;

public class Tickets {
    private final ArrayList<Ticket> tickets = new ArrayList<>();

    public Tickets() {
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Flight flight, User passenger, String ticketId) {
        tickets.add(new Ticket(flight, passenger, ticketId));
    }

    public void updateTicketsMessage(String flightId) {
        for (Ticket ticket : tickets) {
            Flight flight = ticket.getFlight();
            if (flightId.equals(flight.getFlightId())) {
                ticket.setUpdated(true);
                ticket.setMessage("* Flight with flight Id : " + flight.getFlightId()
                        + "From : " + flight.getOrigin()
                        + "To : " + flight.getDestination()
                        + "is updated !"
                        );
            }
        }
    }

    public void removingTicketsMessage(String flightId) {
        for (Ticket ticket : tickets) {
            Flight flight = ticket.getFlight();
            if (flightId.equals(flight.getFlightId())) {
                ticket.setRemoved(true);
                ticket.setMessage("* Flight with flight Id : " + flight.getFlightId()
                        + "From : " + flight.getOrigin()
                        + "To : " + flight.getDestination()
                        + "is removed !"
                );
            }
        }
    }

    public void removeTicket(String ticketId) {
        tickets.remove(findTicketWithTicketId(ticketId));
    }

    public void showMessages(User user) {
        for (Ticket ticket : tickets) {
            if (ticket.getPassenger().equals(user)) {
                if (ticket.isRemoved()) {
                    System.out.println(ticket.getMessage());
                    tickets.remove(ticket);
                } else if (ticket.isUpdated()) {
                    System.out.println(ticket.getMessage());
                    ticket.setUpdated(false);
                }
            }
        }
    }

    public void showBookedFlights(User user) {
        for (Ticket ticket : tickets) {
            if (ticket.getPassenger().equals(user)) {
                Flight flight = ticket.getFlight();
                System.out.printf("| %-10s | %-10s | %-10s | %-13s | %-10s | %-6s | %,-10d | %-6d |%n",
                        ticket.getTicketId(), flight.getFlightId(), flight.getOrigin(), flight.getDestination(),
                        flight.getDate(), flight.getTime(), flight.getPrice(), flight.getSeats());
                System.out.println("----------------------------------------------------------------------------------------------------");
            }
        }
    }

    public Ticket findTicketWithFlightId(String flightId) {
        for (Ticket ticket : tickets) {
            Flight flight = ticket.getFlight();
            if (flightId.equals(flight.getFlightId())) {
                return ticket;
            }
        }
        return null;
    }

    public Ticket findTicketWithTicketId(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticketId.equals(ticket.getTicketId())) {
                return ticket;
            }
        }
        return null;
    }
}
