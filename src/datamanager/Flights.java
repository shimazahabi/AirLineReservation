package datamanager;

import data.Flight;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for saving all the flights.
 */
public class Flights extends DataHolder<Flight> {

    public Flights(Flight flight, String filePath, String fileIndexPath, int recordBytesNum, int featuresNum) {
        super(flight, filePath, fileIndexPath, recordBytesNum, featuresNum);
    }

    /**
     * This method calculates the matching score of the available flights with the searched flight.
     * @return an arraylist of the found flights
     */
    public ArrayList<Flight> calculateMatchScores(List<Integer> matchScores, String flightId, String origin, String destination, String date, String time, int start, int end) throws IOException {
        ArrayList<Flight> foundFlights = new ArrayList<>();
        openFile();
        int matchScore;
        for (int i = 0; i < (file.length() / recordBytesNum); i++) {
            String[] str = new String[8];
            for (int j = 0; j < featuresNum; j++) {
                str[j] = readFixString(file);
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
            matchScores.add(matchScore);
            foundFlights.add(t.separateRecord(str));
        }
        closeFile();
        return foundFlights;
    }

    /**
     * This method prints all the  flights.
     */
    public void printFlights() {
        String[] str = new String[featuresNum];
        try {
            openFile();
            for (int i = 0; i < (file.length() / recordBytesNum); i++) {
                for (int j = 0; j < featuresNum; j++) {
                    str[j] = readFixString(file);
                }
                System.out.print(t.separateRecord(str));
            }
            closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}