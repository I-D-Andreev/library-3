import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Tries to send all the emails that were not sent correctly.
 * @author Ivan Andreev
 */
public class SendUnsentEmails extends TimerTask {

    @Override
    public void run() {
        System.out.println("Try to send unsent messages");
        System.out.println("Number of emails - " + MailSender.NOT_SENT_MESSAGES.size());

        // if no messages, just do nothing
        if(MailSender.NOT_SENT_MESSAGES.size() == 0){
            return;
        }

        synchronized (MailSender.NOT_SENT_MESSAGES) {
            ArrayList<MimeMessage> toRemove = new ArrayList<>();
            for (MimeMessage m : MailSender.NOT_SENT_MESSAGES) {
                boolean sentSuccessfully = MailSender.sendEmail(m);

                // if a message was not sent successfully, stop
                if (sentSuccessfully) {
                    toRemove.add(m);
                } else {
                    return;
                }
            }

            // remove the messages that we have sent
            MailSender.NOT_SENT_MESSAGES.removeAll(toRemove);

            System.out.println("Number of remaining emails - " + MailSender.NOT_SENT_MESSAGES.size());
        }
    }
}
