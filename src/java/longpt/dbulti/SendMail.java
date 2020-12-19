/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.dbulti;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author phamt
 */
public class SendMail {

    private static final String HOST_NAME = "smtp.gmail.com"; //using smtp host provide by google
    private static final int TLS_PORT = 587; //Port for Transport Layer Security (TSL)
    private static final String APP_EMAIL = "redore0808@gmail.com"; //email use to send 
    private static final String APP_PASSWORD = "tl12on34"; //app password of email, use for authentication
    
    public static void sendAccountActivationCode(String code, String receiveEmail)
	    throws AddressException, MessagingException {
	//get properties object to config email
	Properties properties = new Properties();
	properties.put("mail.smtp.auth", "true");
	properties.put("mail.smtp.host", HOST_NAME);
	properties.put("mail.smtp.starttls.enable", "true");
	properties.put("mail.smtp.port", TLS_PORT);

	//get email session
	Session session = Session.getInstance(properties, new Authenticator() {
	    @Override
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
	    }
	});

	//create and compose mime message
	MimeMessage message = new MimeMessage(session);
	message.setFrom(new InternetAddress(APP_EMAIL));
	message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmail));
	message.setSubject("Verify Your Booking Hotel");
	message.setText("The activation code for your account is:" + code);

	//send message
	Transport.send(message);
    }
}
