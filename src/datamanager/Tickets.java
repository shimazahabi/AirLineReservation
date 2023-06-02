package datamanager;

import data.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class for saving all the tickets.
 */
public class Tickets extends DataHolder<Ticket> {
    public static int idCounter = 1000;

    public Tickets(Ticket ticket, String filePath, String fileIndexPath, int recordBytesNum, int featuresNum) {
        super(ticket, filePath, fileIndexPath, recordBytesNum, featuresNum);
    }

    /**
     * This method generates a unique ticket id.
     * @param flightId of the booked flight
     * @return generated ticket id
     */
    public String ticketIdGenerator(String flightId) {
        idCounter += index.size() + 1;
        return String.format("%s-%d", flightId, idCounter);
    }

    /**
     * This method finds the booked tickets of a passenger.
     * @param username of the passenger
     * @return an arraylist of the found thickets
     */
    public ArrayList<Ticket> bookedTickets(String username) {
        ArrayList<Ticket> foundTickets = new ArrayList<>();
        try {
            openFile();
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                String[] str = new String[featuresNum];
                for (int j = 0; j < featuresNum; j++) {
                    str[j] = readFixString(file);
                }
                if (username.matches(str[1])) {
                    foundTickets.add(t.separateRecord(str));
                }
            }
            closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return foundTickets;
    }
}