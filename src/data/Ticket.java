package data;

/**
 * This class is for the ticket details. (Including flight, passenger, ticketId, message, removed, updated)
 */
public class Ticket {
    private final Flight flight;
    private final Passenger passenger;
    private final String ticketId;
    private String message;
    private boolean removed, updated;

    public Ticket(Flight flight, Passenger passenger, String ticketId) {
        this.flight = flight;
        this.passenger = passenger;
        this.ticketId = ticketId;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() { return passenger; }

    public String getTicketId() {
        return ticketId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return String.format("""
                | %-10s | %-10s | %-10s | %-13s | %-10s | %-6s | %,-10d | %-6d |
                +--------------------------------------------------------------------------------------------------+
                """, ticketId, flight.getFlightId(), flight.getOrigin(), flight.getDestination(),
                flight.getDate(), flight.getTime(), flight.getPrice(), flight.getSeats());
    }
}