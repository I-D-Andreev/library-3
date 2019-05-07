/**
 * Class to represent a MimeMessage in a table.
 * @author Ivan Andreev
 */
public class MimeMessageTableRepresentation {
    /**
     * The email address of the receiver
     */
    private String email;

    /**
     * The subject of the message
     */
    private String subject;

    /**
     * Creates an MimeMessage table representation object.
     * @param email The email.
     * @param subject The subject.
     */
    public MimeMessageTableRepresentation(String email, String subject) {
        this.email = email;
        this.subject = subject;
    }

    /**
     * Gets the email.
     * @return The email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * @param email The email. Must be full email format.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the subject of the message.
     * @return The subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the message.
     * @param subject The subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
