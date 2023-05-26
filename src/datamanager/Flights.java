package datamanager;

import data.Flight;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * This class is for saving all the flights.
 */
public class Flights extends DataHolder<Flight> {

    public Flights(Flight flight, String filePath, int recordBytesNum, int featuresNum) {
        super(flight, filePath, recordBytesNum, featuresNum);
    }

    public ArrayList<Flight> calculateMatchScores(int[] matchScores, String flightId, String origin, String destination, String date, String time, int start, int end) throws IOException {
        ArrayList<Flight> foundFlights = new ArrayList<>();
        openFile();
        int matchScore;
        for (int i = 0; i < (file.length() / recordBytesNum); i++) {
            String[] str = new String[8];
            for (int j = 0; j < featuresNum; j++) {
                str[j] = readFixString();
            }
            matchScore = 0;
            if (flightId.matches("(?i)" + str[0] + "(?-i)")) {
                matchScore++;
            }
            if (origin.matches("(?i)" + str[1] + "(?-i)")) {
                matchScore++;
            }
            if (destination.matches("(?i)" + str[2] + "(?-i)")) {
                matchScore++;
            }
            if (date.equals(str[3])) {
                matchScore++;
            }
            if (time.equals(str[4])) {
                matchScore++;
            }
            if (start < t.stringToInt(str[5]) && t.stringToInt(str[5]) < end) {
                matchScore++;
            }
            matchScores[i] = matchScore;
            foundFlights.add((Flight) t.separateRecord(str));
        }
        closeFile();
        return foundFlights;
    }

    public void printFlights() {
        String[] str = new String[featuresNum];
        try {
            openFile();
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                for (int j = 0; j < featuresNum; j++) {
                    str[j] = readFixString();
                }
                System.out.print(t.separateRecord(str));
            }
            closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}