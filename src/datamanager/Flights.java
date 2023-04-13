package datamanager;

import data.Flight;

import java.util.ArrayList;

public class Flights {
    private final ArrayList<Flight> flights = new ArrayList<>();

    public Flights() {
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public ArrayList<Flight> getFlight() {
        return flights;
    }

    public void addFlight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        flights.add(new Flight(flightId, origin, destination, date, time, price, seats));
    }

    public void updateFlight(String flightId, int fieldNum, String update) {
        Flight flight = findFlight(flightId);
        switch (fieldNum) {
            case 1 -> flight.setFlightId(update);
            case 2 -> flight.setOrigin(update);
            case 3 -> flight.setDestination(update);
            case 4 -> flight.setDate(update);
            case 5 -> flight.setTime(update);
            case 6 -> flight.setPrice(Integer.parseInt(update));
            case 7 -> flight.setSeats(Integer.parseInt(update));
            default -> {
            }
        }
    }
    public void isBooked(String flightId) {
        findFlight(flightId).setBooked(true);
    }
    public void removeFlight(String flightId) {
        flights.remove(findFlight(flightId));
    }

    public String flightId(String flightId) {
        return findFlight(flightId).getFlightId();
    }
    public String origin(String flightId) {
        return findFlight(flightId).getOrigin();
    }
    public String destination(String flightId) {
        return findFlight(flightId).getDestination();
    }
    public String date(String flightId) {
        return findFlight(flightId).getDate();
    }

    public String time(String flightId) {
        return findFlight(flightId).getTime();
    }
    public int price(String flightId) {
        return findFlight(flightId).getPrice();
    }
    public int seats(String flightId) {
        return findFlight(flightId).getSeats();
    }
    public boolean booked(String flightId) {
        return findFlight(flightId).isBooked();
    }

    public void calculateMatchScores(int[] matchScores, int[] flightIndex, String flightId, String origin, String destination, String date, String time, int start, int end) {
        int size = flights.size();
        int matchScore;
        for (int i = 0; i < size; i++) {
            Flight flight = flights.get(i);
            matchScore = 0;
            if (flightId.matches("(?i)" + flight.getFlightId() + "(?-i)")) {
                matchScore++;
            }
            if (origin.matches("(?i)" + flight.getOrigin() + "(?-i)")) {
                matchScore++;
            }
            if (destination.matches("(?i)" + flight.getDestination() + "(?-i)")) {
                matchScore++;
            }
            if (date.equals(flight.getDate())) {
                matchScore++;
            }
            if (time.equals(flight.getTime())) {
                matchScore++;
            }
            if (start < flight.getPrice() && flight.getPrice() < end) {
                matchScore++;
            }
            matchScores[i] = matchScore;
            flightIndex[i] = i;
        }
    }

    public Flight findFlight(String flightId) {
        for (Flight flight : flights) {
            if (flightId.equals(flight.getFlightId())){
                return flight;
            }
        }
        return null;
    }

    public void showFlights() {
        for (Flight flight : flights) {
            System.out.printf("""
                            | %-10s | %-10s | %-13s | %-10s | %-6s | %,-10d | %-6d |
                            +-------------------------------------------------------------------------------------+
                            """,
                    flight.getFlightId(), flight.getOrigin(), flight.getDestination(),
                    flight.getDate(), flight.getTime(), flight.getPrice(),
                    flight.getSeats());
        }
    }

    public void showSearchedFlights(int[] matchScores, int[] flightIndex) {
        int size = flights.size();
        for (int i = size - 1; i >= 0; i--) {
            Flight flight = flights.get(flightIndex[i]);
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
}