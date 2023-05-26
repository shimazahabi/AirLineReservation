package datamanager;

import data.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * This class for saving all the tickets.
 */
public class Tickets extends DataHolder<Ticket> {
    public static int idCounter = 1000;

    public Tickets(Ticket ticket, String filePath, int recordBytesNum, int featuresNum) {
        super(ticket, filePath, recordBytesNum, featuresNum);
    }

    public String ticketIdGenerator(String flightId) {
        idCounter++;
        return String.format("%s-%d", flightId, idCounter);
    }

    public ArrayList<Ticket> bookedTickets(String username) {
        ArrayList<Ticket> foundTickets = new ArrayList<>();
        try {
            openFile();
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                String[] str = new String[featuresNum];
                for (int j = 0; j < featuresNum; j++) {
                    str[j] = readFixString();
                }
                if (username.matches(str[1])) {
                    foundTickets.add((Ticket) t.separateRecord(str));
                }
            }
            closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return foundTickets;
    }
}