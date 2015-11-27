package eu.appbucket.upserver;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class Mailer {

    public static void sentMail() throws Exception {
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("rotharapp", "jfdrh9a8f7ad98asdhu9o88"));
        email.setSSLOnConnect(true);
        email.setFrom("rotharapp@gmail.com");
        email.setSubject("Server down");
        email.setMsg("Server down :(");
        email.addTo("abednarski79@gmail.com");
        email.send();
    }
}
