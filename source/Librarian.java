import java.util.Date;

/**
 * This is used to make an account for a librarian
 *
 * @author Christina Meggs
 */
public class Librarian extends User {
    /**
     * A librarian's staff number.
     */
    private String staffNumber;

    /**
     * The librarian's employment date.
     */
    private Date employmentDate;


    /**
     * Creates a librarian account with the inputted information.
     *
     * @param username         The user's account username.
     * @param password         The users (encrypted) password.
     * @param securitySalting  The salt used for encrypting the password.
     * @param email            The user's email.
     * @param secretQuestion   The user's secret question for password recovery.
     * @param secretAnswer     The user's (encoded) secret answer for password recovery.
     * @param firstName        The user's first name.
     * @param lastName         The user's last name.
     * @param phoneNumber      The user's phone number.
     * @param profileImagePath The user's profile image.
     * @param address          The user's home address.
     */
    public Librarian(String username, String password, String securitySalting,
                     String email, String secretQuestion, String secretAnswer,
                     String firstName, String lastName, String phoneNumber, String profileImagePath, Address address) {
        super(username, password, securitySalting, email, secretQuestion, secretAnswer, firstName, lastName,
                phoneNumber, profileImagePath, address);
        this.setStaffNumber();
        this.employmentDate = new Date();
    }

    /**
     * Gets staff number of the librarian.
     *
     * @return The librarians staff number.
     */
    public String getStaffNumber() {
        return this.staffNumber;
    }

    /**
     * Sets the staff number of the librarian.
     */
    private void setStaffNumber() {
        this.staffNumber = this.getId() + "-" + this.getUsername().charAt(0) + this.getFirstName().charAt(0);
    }

    /**
     * Checks whether librarian has admin access.
     *
     * @return True as librarians always have admin access.
     */
    public boolean hasAdminAccess() {
        return true;
    }

    /**
     * Gets the employment date of the librarian.
     *
     * @return Librarian's employment date.
     */
    public Date getEmploymentDate() {
        return this.employmentDate;
    }

}