package data;

import datamanager.WritableReadable;

/**
 * This class is for the ticket details. (Including flight, passenger, ticketId, message, removed, updated)
 */
public class Ticket implements WritableReadable {
    private String username;
    private String flightId;
    private String ticketId;

    public Ticket(String ticketId, String username, String flightId) {
        this.ticketId = ticketId;
        this.username = username;
        this.flightId = flightId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String toString(Flight flight) {
        return String.format("""
               | %-10s | %-10s | %-10s | %-13s | %-10s | %-6s | %,-10d | %-6d |
               +--------------------------------------------------------------------------------------------------+
               """, ticketId, flight.getFlightId(), flight.getOrigin(), flight.getDestination(),
                flight.getDate(), flight.getTime(), flight.getPrice(), flight.getSeats());
    }

    @Override
    public String generate() {
        return fixString(ticketId) + fixString(username) + fixString(flightId);
    }

    @Override
    public Ticket separateRecord(String[] str) {
        return new Ticket(str[0], str[1], str[2]);
    }
}