package sarekoZerbitzuakSortzea_4.SMTP;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
/**
 * Liburutegi hauek deskargatu eta proiektuari gehitu:
 * 
 * https://repo1.maven.org/maven2/org/eclipse/angus/angus-mail/2.0.3/angus-mail-2.0.3.jar
 * https://repo1.maven.org/maven2/jakarta/mail/jakarta.mail-api/2.1.3/jakarta.mail-api-2.1.3.jar
 * https://repo1.maven.org/maven2/jakarta/activation/jakarta.activation-api/2.1.3/jakarta.activation-api-2.1.3.jar
 * 
 * Google kontu batean APP PASSWORD bat sortu:
 * 
 * https://myaccount.google.com/apppasswords
 */
public class SMTP {
    public static void main(String[] args) {
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;
        
        String username = "ZURE_GMAIL_KORREOA"; // ALDATU
        String password = "ZURE PASAHITZ BEREZIA (APP PASSWORD)"; //ALDATU

        String toEmail = "NORAKO_KORREO_HELBIDEA"; //ALDATU
        
        String subject = "SMTP Test";
        String messageText = "Kaixo! Hau SMTP bidez bidalitako mezua da.";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", String.valueOf(smtpPort));

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("Email-a bidalita arrakastaz.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}