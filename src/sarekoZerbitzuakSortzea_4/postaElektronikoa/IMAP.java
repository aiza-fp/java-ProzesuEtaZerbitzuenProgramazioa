package sarekoZerbitzuakSortzea_4.postaElektronikoa;

import java.util.Properties;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeBodyPart;

/**
 * IMAP (Internet Message Access Protocol) protokoloaren erabileraren adibidea.
 * 993 portuan eta ("mail.imap.starttls.enable", "true") adieraziz datuak modu
 * zifratuan bidali eta jasoko direla adierazten dugu, hau da, IMAPs erabiliko
 * dugu.
 */
public class IMAP {
	public static void main(String[] args) {
		String imapHost = "imap.gmail.com";
		String username = "ZURE GMAIL KORREOA";
		String password = "ZURE KORREOKO APP PASAHITZA";

		Properties properties = new Properties();
		properties.put("mail.imap.host", imapHost);
		properties.put("mail.imap.port", "993");
		properties.put("mail.imap.starttls.enable", "true");

		Session session = Session.getInstance(properties);
		try {
			Store store = session.getStore("imaps");
			store.connect(imapHost, username, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			int totalMessages = inbox.getMessageCount();
			System.out.println("Mezuak guztira: " + totalMessages);

			int numberOfMessagesToRetrieve = 5;
			int start = Math.max(1, totalMessages - numberOfMessagesToRetrieve + 1);

			Message[] messages = inbox.getMessages(start, totalMessages);
			System.out.println("Aurkitutako mezuak: " + messages.length);

			for (Message message : messages) {
				System.out.println("Mezua: " + message.getSubject());
				Object content = message.getContent();                
			}

			inbox.close(false);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
