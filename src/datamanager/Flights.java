package datamanager;

import data.Flight;
import java.util.ArrayList;

/**
 * This class is for saving all the flights.
 */
public class Flights {
    private final ArrayList<Flight> flights = new ArrayList<>();

    public Flights() { }

    public ArrayList<Flight> getFlights() { return flights; }

    public void addFlight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        flights.add(new Flight(flightId, origin, destination, date, time, price, seats));
    }

    public void removeFlight(Flight flight) { flights.remove(flight); }

    /**
     * This method is for calculating the matching score of the searched flight.
     * @param matchScores this array saves the matching score of all the flights.
     * @param flightIndex this array saves the index of the flights.
     */
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

    /**
     * This method is for finding the flight by flight id.
     * @return the found flight
     */
    public Flight findFlight(String flightId) {
        for (Flight flight : flights) {
            if (flightId.equals(flight.getFlightId())){
                return flight;
            }
        }
        return null;
    }
}