package data;

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