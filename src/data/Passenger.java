package data;

/**
 * This class is for the passenger details including username, password and charge. (Child class of BaseUser)
 */
public class Passenger extends BaseUser {
    private int charge;

    public Passenger(String username, String password, int charge) {
        super(username, password);
        this.charge = charge;
    }

    public int getCharge() {
        return charge;
    }

    @Override
    public String generate() {
        return fixString(getUsername()) + fixString(getPassword()) + fixString(intToString(charge));
    }

    @Override
    public Passenger separateRecord(String[] str) {
        return new Passenger(str[0], str[1], stringToInt(str[2]));
    }
}