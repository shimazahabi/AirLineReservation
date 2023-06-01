package data;

/**
 * This class is for the admin details including username and password. (Child class of BaseUser)
 */
public class Admin extends BaseUser {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String generate() {
        return fixString(getUsername()) + fixString(getPassword());
    }

    @Override
    public String keyWord() {
        return getUsername();
    }

    @Override
    public Admin separateRecord(String[] str) {
        return new Admin(str[0], str[1]);
    }
}