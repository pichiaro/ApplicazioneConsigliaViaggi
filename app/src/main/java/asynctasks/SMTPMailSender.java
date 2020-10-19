package asynctasks;

import android.app.Dialog;

import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import authenticators.UserAuthenticator;

public class SMTPMailSender extends AbstractWaitingAsyncTask {

    private String email;
    private String subject;
    private String message;
    private UserAuthenticator userAuthenticator;
    private boolean isOk;

    public SMTPMailSender(Dialog dialog, String email, String subject, String message, UserAuthenticator userAuthenticator){
        super(dialog);
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.userAuthenticator = userAuthenticator;
    }

    public void setUserAuthenticator(UserAuthenticator userAuthenticator) {
        this.userAuthenticator = userAuthenticator;
    }

    public UserAuthenticator getUserAuthenticator() {
        return this.userAuthenticator;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    protected void setOk(boolean ok) {
        isOk = ok;
    }

    public boolean isOk() {
        return this.isOk;
    }

    @Override
    protected void onPreExecute() {
        this.isOk = false;
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object...objects) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(properties, this.userAuthenticator);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            String user = this.userAuthenticator.getUser();
            mimeMessage.setFrom(new InternetAddress(user));
            mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(this.email));
            mimeMessage.setSubject(this.subject);
            mimeMessage.setText(this.message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            this.isOk = false;
        }
        this.isOk = true;
        return null;
    }

}