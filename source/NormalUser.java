import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is used to make an account for a normal user
 *
 * @author Christina Meggs, Ivan Andreev
 */
public class NormalUser extends User implements Serializable {
    /**
     * The amount of money the user owes to the library.
     */
    private double balance;

    /**
     * List of borrowed copies.
     */
    private ArrayList<Copy> borrowedCopies;

    /**
     * List of new additions for the particular user.
     */
    private ArrayList<Resource> newAdditions;

    /**
     * The user's money transaction history.
     */
    private History transactionHistory;

    /**
     * The number of resources a user can borrow.
     */
    private final int resourceCap;

    /**
     * A user's notification preferences.
     */
    private NotificationPreferences notificationPreferences;


    /**
     * Creates a normal user account with the inputted information.
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
    public NormalUser(String username, String password, String securitySalting,
                      String email, String secretQuestion, String secretAnswer,
                      String firstName, String lastName, String phoneNumber, String profileImagePath, Address address) {
        super(username, password, securitySalting, email, secretQuestion, secretAnswer, firstName,
                lastName, phoneNumber, profileImagePath, address);

        this.balance = 0;
        this.transactionHistory = new History();
        this.borrowedCopies = new ArrayList<>();
        this.newAdditions = new ArrayList<>();
        this.resourceCap = 5;
        this.notificationPreferences = new NotificationPreferences();
    }

    /**
     * Gets the user's account balance.
     *
     * @return The user's account balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gives the user a fine.
     * Also adjusts their account balance.
     *
     * @param amount The amount of the fine.
     */
    public void giveFine(double amount) {
        this.balance += amount;
    }

    /**
     * Pays off fine(s).
     * Also adjusts the account balance accordingly.
     *
     * @param amount The amount the user is paying off.
     */
    public void payFines(double amount) {

        this.balance -= amount;
        this.balance = Math.round(this.balance * 100.0) / 100.0;

        transactionHistory.addEntry(new HistoryEntryMoneyTransaction(new Date(), amount));
    }

    /**
     * Gets the users resource cap.
     *
     * @return The resource cap of the user.
     */
    public int getResourceCap() {
        return resourceCap;
    }

    /**
     * Checks if the user has admin access.
     *
     * @return false Normal users don't have admin access.
     */
    public boolean hasAdminAccess() {
        return false;
    }

    /**
     * Gets the transaction history of the user.
     *
     * @return Transaction history of the user.
     */
    public History getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Gets an arrayList of all the borrowed copies.
     *
     * @return An arrayList of all the borrowed copies.
     */
    public ArrayList<Copy> getBorrowedCopies() {
        return borrowedCopies;
    }

    /**
     * Says if a user can borrow copies.
     *
     * @return true if a user can borrow a copy, otherwise - false.
     */
    public boolean canBorrowCopy() {
        return !(balance > 0 || hasOverdueCopies());
    }

    /**
     * Checks number of resources the user has taken out in regards to the resource cap.
     *
     * @return an int that represents the amount of "points" the user has accumulated.
     */
    public int resourceCapCheck() {
        ArrayList<Copy> borrowedCopies = getBorrowedCopies();

        int counter = 0;
        for (int i = 0; i < borrowedCopies.size(); i++) {
            counter += borrowedCopies.get(i).getCopyOf().getCapContribution();
        }

        return counter;
    }

    /**
     * Checks the arrayList of all borrowed copies for overdue copies.
     *
     * @return True if there are overdue copies,false otherwise.
     */
    private boolean hasOverdueCopies() {
        boolean hasOverdueCopies = false;
        Date today = new Date();
        for (Copy copy : borrowedCopies) {
            if (copy.getDueDate() != null && copy.getDueDate().compareTo(today) == -1) {
                hasOverdueCopies = true;
            }
        }
        return hasOverdueCopies;
    }

    /**
     * Get the new additions of the user.
     * @return A list of newly added resources.
     */
    public ArrayList<Resource> getNewAdditions() {
        return newAdditions;
    }

    /**
     * Get a user's notification preferences.
     * @return The user's notification preferences.
     */
    public NotificationPreferences getNotificationPreferences() {
        return notificationPreferences;
    }
}