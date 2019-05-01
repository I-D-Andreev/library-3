/**
 * A class to send notification emails.
 *
 * @author Ivan Andreev
 */
// main usage is that it prepares the message text
public class EmailNotificationSender {
    // some pre-written strings that will be used often in the messages
    private static final String hello = "Hello." + System.lineSeparator();

    private static final String bestRegards = System.lineSeparator() + "Best regards,"
            + System.lineSeparator() + "The Tawe-Lib Team";

    private static final String unsubscribeInfo = System.lineSeparator() + " You can unsubscribe from these emails"
            + " in the Tawe-Lib application/Edit account/Notifications";

    /**
     * Sends a notification for a new event in Tawe-Lib. The user will only receive the notification if they
     * have allowed this type of notifications in their preferences.
     *
     * @param recipient The user that will receive the notification.
     * @param event     The new event.
     */
    public static void sendNewEventNotification(NormalUser recipient, Event event) {
        if (!recipient.getNotificationPreferences().getReceiveNewEventNotification()) {
            return;
        }

        String message = hello + "A new event has been added. More details below: " + System.lineSeparator()
                + "Title: " + event.getTitle() + System.lineSeparator()
                + "Description: " + event.getDescription() + System.lineSeparator()
                + "Max number of attendees: " + event.getMaxAttendees() + System.lineSeparator()
                + "Start time: " + event.getStartDate() + " " + event.getStartTime() + System.lineSeparator()
                + bestRegards + unsubscribeInfo;

        MailSender.sendEmail(recipient.getEmail(), "Tawe-Lib New Event", message);
    }

    /**
     * Send a notification for a new added resource in Tawe-Lib. The user will only receive the notification if they
     * have allowed this type of notifications in their preferences.
     *
     * @param recipient   The user that will receive the notification.
     * @param newResource The newly added resource.
     */
    public static void sendNewResourceAddedNotification(NormalUser recipient, Resource newResource) {
        if (!recipient.getNotificationPreferences().getReceiveNewAdditionsNotification()) {
            return;
        }

        String message = hello + "A new resource has been added. More details below: " + System.lineSeparator()
                + "Title: " + newResource.getTitle() + System.lineSeparator()
                + "Type: " + newResource.getType() + System.lineSeparator()
                + bestRegards + unsubscribeInfo;

        MailSender.sendEmail(recipient.getEmail(), "Tawe-Lib New Resource Added", message);
    }

    /**
     * Send a notification when a new resource is reserved for the user. The user will only receive
     * the notification if they have allowed this type of notifications in their preferences.
     *
     * @param recipient        The user that will receive the notification.
     * @param reservedResource The newly reserved resource.
     */
    public static void sendNewResourceReservedNotification(NormalUser recipient, Resource reservedResource) {
        if (!recipient.getNotificationPreferences().getReceiveNewReservedResourceNotification()) {
            return;
        }

        String message = hello
                + "A new resource has been reserved for you. More details below:" + System.lineSeparator()
                + "Title: " + reservedResource.getTitle() + System.lineSeparator()
                + "Type: " + reservedResource.getType() + System.lineSeparator()
                + bestRegards + unsubscribeInfo;

        MailSender.sendEmail(recipient.getEmail(), "Tawe-Lib New Resource Reserved For You", message);
    }


    /**
     * Send a notification that a copy should be returned. The user will only receive the notification if they
     * * have allowed this type of notifications in their preferences.
     *
     * @param recipient  The user that will receive the notification.
     * @param returnCopy The copy that should be returned.
     */
    public static void sendNewReturnAResourceNotification(NormalUser recipient, Copy returnCopy) {
        if (!recipient.getNotificationPreferences().getReceiveReturnResourceNotification()) {
            return;
        }

        String message = hello +
                "A resource you have borrowed should soon be returned. More information below:" + System.lineSeparator()
                + "ID: " + returnCopy.getUniqueCopyID() + System.lineSeparator()
                + "Title: " + returnCopy.getCopyOf().getTitle() + System.lineSeparator()
                + "Type: " + returnCopy.getCopyOf().getType() + System.lineSeparator() + System.lineSeparator()

                + "DUE DATE: " + returnCopy.getDueDate() + System.lineSeparator() + System.lineSeparator()

                + "A fine of " + returnCopy.getCopyOf().getLateReturnFinePerDay() + " pounds will apply for each"
                + " day that you are late, up to a maximum of " + returnCopy.getCopyOf().getMaxFineAmount()
                + " pounds total." + System.lineSeparator()
                + bestRegards;

        MailSender.sendEmail(recipient.getEmail(),
                "Tawe-Lib A Resource Should Be Returned By " + returnCopy.getDueDate(),
                message);
    }

}
