package vn.udn.dut.cinema.common.mail.utils;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

/**
 * Username Password Authenticator
 *
 * @author HoaLD
 * @since 3.1.2
 */
public class UserPassAuthenticator extends Authenticator {

    private final String user;
    private final String pass;

    /**
     * structure
     *
     * @param user username
     * @param pass password
     */
    public UserPassAuthenticator(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.user, this.pass);
    }

}
