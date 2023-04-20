package datamanager;

import data.*;
import java.util.ArrayList;

public class Tickets {
    private final ArrayList<Ticket> tickets = new ArrayList<>();
    private static int idCounter = 1000;

    public Tickets() { }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public String addTicket(Flight flight, User passenger) {
        idCounter++;
        String ticketId = String.format("%s-%d", flight.getFlightId(), idCounter);
        tickets.add(new Ticket(flight, passenger, ticketId));
        return ticketId;
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public void updateTicketsMessage(Flight flight) {
        tickets.forEach(ticket -> {
            if (flight.equals(ticket.getFlight())) {
                ticket.setUpdated(true);
                ticket.setMessage("* Flight with flight Id : " + flight.getFlightId()
                        + "From : " + flight.getOrigin()
                        + "To : " + flight.getDestination()
                        + "is updated !"
                );
            }
        });
    }

    public void removingTicketsMessage(Flight flight) {
        tickets.forEach(ticket -> {
            if (flight.equals(ticket.getFlight())) {
                ticket.setRemoved(true);
                ticket.setMessage("* Flight with flight Id : " + flight.getFlightId()
                        + "From : " + flight.getOrigin()
                        + "To : " + flight.getDestination()
                        + "is removed !"
                );
            }
        });
    }

    public Ticket findTicket(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticketId.equals(ticket.getTicketId())) {
                return ticket;
            }
        }
        return null;
    }
}