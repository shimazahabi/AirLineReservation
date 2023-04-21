package datamanager;

import data.*;
import java.util.ArrayList;

/**
 * This class for saving all the tickets.
 */
public class Tickets {
    private final ArrayList<Ticket> tickets = new ArrayList<>();
    private static int idCounter = 1000;

    public Tickets() { }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public String addTicket(Flight flight, Passenger passenger) {
        idCounter++;
        String ticketId = String.format("%s-%d", flight.getFlightId(), idCounter);
        tickets.add(new Ticket(flight, passenger, ticketId));
        return ticketId;
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    /**
     * This method is for setting the message when updating a flight.
     */
    public void updateTicketsMessage(Flight flight) {
        tickets.forEach(ticket -> {
            if (flight.equals(ticket.getFlight())) {
                ticket.setUpdated(true);
                ticket.setMessage("* Flight with flight Id : " + flight.getFlightId()
                        + " From : " + flight.getOrigin()
                        + " To : " + flight.getDestination()
                        + " is updated !"
                );
            }
        });
    }

    /**
     * This method is for setting the message when removing a flight.
     */
    public void removingTicketsMessage(Flight flight) {
        tickets.forEach(ticket -> {
            if (flight.equals(ticket.getFlight())) {
                ticket.setRemoved(true);
                ticket.setMessage("* Flight with flight Id : " + flight.getFlightId()
                        + " From : " + flight.getOrigin()
                        + " To : " + flight.getDestination()
                        + " is removed !"
                );
            }
        });
    }

    /**
     * This method is for finding ticket using the ticket id.
     * @return the found ticket
     */
    public Ticket findTicket(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticketId.equals(ticket.getTicketId())) {
                return ticket;
            }
        }
        return null;
    }
}