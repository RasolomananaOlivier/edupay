package util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	public static void send(String body, String receiver) {
		try {
			Session session = getSession();
			session.setDebug(true);

			Message message = new MimeMessage(session);
			message.setSubject("Bourse d'étude");

			message.setContent(body, "text/html");

			Address sender = new InternetAddress("herimanitraolivierr@gmail.com", "EDUPAY");

			message.setFrom(sender);

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

			try (Transport tt = session.getTransport()) {

				tt.connect("herimanitraolivierr@gmail.com", "edlitrqurovbfhin");
				tt.sendMessage(message, message.getAllRecipients());

				System.out.println("Email sent");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static Properties getProperties() {
		Properties pros = new Properties();

		pros.put("mail.transport.protocol", "smtps");
		pros.put("mail.smtps.host", "smtp.gmail.com");
		pros.put("mail.smtps.port", 465);
		pros.put("mail.smtps.auth", "true");
		pros.put("mail.smtps.quitwait", "false");

		return pros;
	}

	private static Session getSession() {
		return Session.getDefaultInstance(getProperties());
	}
}
