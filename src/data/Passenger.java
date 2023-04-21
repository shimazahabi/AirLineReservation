package data;

/**
 * This class is for the passenger's username, password and charge. (Child class of BaseUser)
 */
public class Passenger extends BaseUser {
    private int charge;

    public Passenger(String username, String password) {
        super(username, password);
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }
}