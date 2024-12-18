package sarekoZerbitzuakSortzea_4.postaElektronikoa;

import java.util.Properties;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeBodyPart;

/**
 * POP3 (Post Office Protocol 3. bertsioa) protokoloaren erabileraren adibidea.
 * 995 portuan eta ("mail.pop3.starttls.enable", "true") adieraziz datuak modu
 * zifratuan bidali eta jasoko direla adierazten dugu, hau da, POP3s erabiliko dugu.
 * 
 */
public class POP3 {
    public static void main(String[] args) {
        String pop3Host = "pop.gmail.com";
        String username = "zure_email@gmail.com";
        String password = "zure_pasahitza";

        Properties properties = new Properties();
        properties.put("mail.pop3.host", pop3Host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        Session session = Session.getInstance(properties);
        try {
            Store store = session.getStore("pop3s");
            store.connect(pop3Host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();
            System.out.println("Mezuak guztira: " + totalMessages);
            
            int numberOfMessagesToRetrieve = 5;
            int start = Math.max(1, totalMessages - numberOfMessagesToRetrieve + 1);
            
            // Get only the last 5 messages
            Message[] messages = inbox.getMessages(start, totalMessages);
            System.out.println("Aurkitutako mezuak: " + messages.length);

            // Now we can iterate through them normally since we only have the messages we want
            for (Message message : messages) {
                System.out.println("Mezua: " + message.getSubject());
                Object content = message.getContent();
                
                if (content instanceof String) {
                    System.out.println("Edukia: " + content);
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    for (int j = 0; j < multipart.getCount(); j++) {
                        MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(j);
                        if (part.isMimeType("text/plain")) {
                            System.out.println("Edukia: " + part.getContent());
                        }
                    }
                }
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}