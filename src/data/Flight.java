package data;

import datamanager.WritableReadable;

/**
 * This class is for the flight details. (Including flightId, origin, destination, date, time, price, seats, booked)
 */
public class Flight implements WritableReadable {
    private String flightId;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private int price;
    private int seats;
    private boolean booked;

    public Flight(String flightId, String origin, String destination, String date, String time, int price, int seats, boolean booked) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.seats = seats;
        this.booked = booked;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() { return destination; }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isBooked() { return booked; }

    public void setBooked(boolean booked) { this.booked = booked; }

    @Override
    public String toString() {
        return String.format("""
                            | %-10s | %-10s | %-13s | %-10s | %-6s | %,-10d | %-6d |
                            +-------------------------------------------------------------------------------------+
                            """, flightId, origin, destination, date, time, price, seats);
    }

    @Override
    public String generate() {
        return fixString(flightId) + fixString(origin) + fixString(destination) + fixString(date)
               + fixString(time) + fixString(intToString(price)) + fixString(intToString(seats))
               + fixString(booleanToString(booked));
    }

    @Override
    public Flight separateRecord(String[] str) {
        return new Flight(str[0], str[1], str[2], str[3], str[4], stringToInt(str[5]), stringToInt(str[6]), stringToBoolean(str[7]));
    }
}