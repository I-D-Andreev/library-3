import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * A class to deal with sending emails.
 *
 * @author Ivan Andreev
 */
public class MailSender {
    /**
     * Sends an email.
     * @param recipient The email address of the recipient. (xxxxx@xxxx.xxx)
     * @param subject The subject of the email.
     * @param message The message of the email.
     */
    public static void sendEmail(String recipient, String subject, String message) {
        String SENDER_NAME = "tawelib2019@gmail.com";
        String PASSWORD = "tawelib955058@";


        // set up properties to use gmail's smtp
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        // session for the mime message
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_NAME, PASSWORD);
                    }
                });


        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
            mimeMessage.setFrom(new InternetAddress(SENDER_NAME));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message,"utf-8", "html");

            // send message
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Error sending message.");
            e.printStackTrace();
        }
    }
}
