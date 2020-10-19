package authenticators;

import javax.mail.PasswordAuthentication;

import javax.mail.Authenticator;

public class UserAuthenticator extends Authenticator {

    private String user;
    private String password;

    public UserAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.user, this.password);
    }

}
