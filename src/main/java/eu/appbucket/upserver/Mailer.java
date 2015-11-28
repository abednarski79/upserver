package eu.appbucket.upserver;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import java.util.Properties;

public class Mailer {

    private String receiverAddress;
    private String senderAddress;
    private String senderPassword;
    private String probeUrl;

    public Mailer(Properties properties) throws Exception {
        loadSetting(properties);
    }

    private void loadSetting(Properties properties)  {
        senderAddress = properties.getProperty("email.sender.address");
        if(senderAddress == null) {
            throw new RuntimeException("No sender address in the properties file.");
        }
        senderPassword = properties.getProperty("email.sender.password");
        if(senderPassword == null) {
            throw new RuntimeException("No sender password in the properties file.");
        }
        receiverAddress = properties.getProperty("email.receiver.address");
        if(receiverAddress == null) {
            throw new RuntimeException("No receiver address in the properties file.");
        }
        probeUrl = properties.getProperty("probe.url");
    }

    public void sentMail() throws Exception {
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        String senderUser = senderAddress.split("@")[0];
        email.setAuthenticator(new DefaultAuthenticator(senderUser, senderPassword));
        email.setSSLOnConnect(true);
        email.setFrom(senderAddress);
        email.setSubject("Server down !");
        email.setMsg("Server is down :( for proble url: " + probeUrl);
        email.addTo(receiverAddress);
        email.send();
    }
}
